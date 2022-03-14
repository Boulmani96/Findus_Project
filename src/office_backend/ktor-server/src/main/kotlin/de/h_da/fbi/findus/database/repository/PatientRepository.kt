package de.h_da.fbi.findus.database.repository

import com.mongodb.BasicDBObject
import com.mongodb.MongoClientException
import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.gridfs.GridFSBucket
import com.mongodb.client.gridfs.GridFSBuckets
import com.mongodb.client.gridfs.model.GridFSDownloadOptions
import com.mongodb.client.gridfs.model.GridFSUploadOptions
import com.mongodb.client.model.UpdateOptions
import de.h_da.fbi.findus.database.models.Patient
import de.h_da.fbi.findus.database.models.PatientModel
import mu.KotlinLogging
import org.bson.Document
import org.litote.kmongo.*
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

/**
 * Patient repository: this class handles the communication with a MongoDB instance. All CRUD-operations
 * for the class Patient are performed here
 *
 * @constructor instantiates a PatientRepository instance that manages the db communication
 *
 * @param client a client side representation of a mongo cluster
 */
class PatientRepository(client: MongoClient) : RepositoryInterface<Patient> {
    override lateinit var col: MongoCollection<Patient>
    private var gridFSBucket: GridFSBucket
    private val documentKey: String = "patientId"
    private val chunkSize: Int = 1048576
    private val gridFsFilterKey: String = "metadata.$documentKey"
    override val databaseName: String = "patientsDB"
    override val collectionName: String = "patients"
    private val bucketName: String = "images"
    private val originalFileKey: String = "originalFileName"
    private val fileType: String = ".jpg"
    override var logger = KotlinLogging.logger {}
    private val dbValidateRegex = Regex("[^A-Za-z]")
    private val options = UpdateOptions()

    init {
        val database = client.getDatabase(validateDatabaseName(databaseName))
        col = database.getCollection<Patient>(collectionName)
        gridFSBucket = GridFSBuckets.create(database, bucketName)
        options.upsert(false)
    }

    /**
     * inserts a patient into the db
     * @param patient the patient to insert into the database
     * @return String the id of the patient if the patient was successfully inserted
     * @throws MongoException when an error occurred during insertion
     */
    override fun addOne(patient: Patient): String {
        try {
            col.insertOne(patient)
            saveFiles(patient)
            return patient._id
        } catch (exception: MongoException) {
            logger.error(exception) { "error occurred trying to add a patient Error: $exception" }
            throw exception
        }
    }

    /**
     * inserts 0 to * patients into the db
     * @param patients the patients to insert into the database
     * @return String the ids of all inserted patients if the patients were successfully inserted
     * @throws MongoException when an error occurred during insertion
     */
    override fun addMany(patients: List<Patient>): List<String> {
        try {
            col.insertMany(patients)
            patients.forEach { currentPatient -> saveFiles(currentPatient) }
            return patients.asIterable().map { it._id }

        } catch (exception: MongoException) {
            logger.error(exception) { "error occurred trying to add a patient Error: $exception" }
            throw exception
        }
    }

    /**
     * takes the id of a patient and returns all stored information
     * of this patient by searching for the id of the patient
     * @param id the id of a patient to search for
     * @return Patient if found or null if not found
     * @throws MongoException when an error occurred during get operation
     */
    override fun getById(id: String): Patient? {
        try {
            val foundPatient = col.findOne(PatientModel::_id eq id) ?: return null
            downloadFile(foundPatient)
            return foundPatient
        } catch (exception: MongoException) {
            logger.error(exception) { "error occurred trying to get a patient Error: $exception" }
            throw exception
        }
    }

    /**
     * returns all Patients with a given name
     * @param patientName the name to search for
     * @return List<Patient> the patients with the given name
     * @throws MongoException when an error occurred during get operation
     */
    fun getByName(patientName: String): List<Patient> {
        return try {
            val foundPatients = col.find(Patient::name eq patientName)
            val results = foundPatients.asIterable().map { it }
            results.forEach { patient -> run { downloadFile(patient) } }
            results
        } catch (exception: MongoClientException) {
            logger.error(exception) { "error occurred finding all patients with given name Error: $exception" }
            throw exception
        }
    }

    /**
     * returns all saved records
     * @return List<Patient> the list of type Patient
     * @throws MongoException when an error occurred during get operation
     */
    override fun findAll(): List<Patient> {
        try {
            val foundPatients = col.find()
            val results = foundPatients.asIterable().map { it }
            results.forEach { patient -> run { downloadFile(patient) } }
            return results
        } catch (exception: MongoException) {
            logger.error(exception) { "error occurred finding all patients Error: $exception" }
            throw exception
        }
    }

    /**
     * Updates a patient
     *
     * @param patient the patient that should get updated
     * @return true if update was successful, else false
     */
    override fun updateOne(patient: Patient): Boolean {
        saveFiles(patient)
        return try {
            col.updateOneById(
                patient._id,
                patient,
                options,
                updateOnlyNotNullProperties = true
            ).wasAcknowledged()
        } catch (exception: MongoException) {
            logger.error(exception) { "error occurred trying to update a patient Error: $exception" }
            false
        }
    }

    /**
     * Updates multiple patients
     *
     * @param patients the patients that should get updated
     * @return true if update was successful, else false
     */
    override fun updateMany(patients: List<Patient>): Boolean {
        patients.forEach { value ->
            run {
                (col.findOne(PatientModel::_id eq value._id))?.let { saveFiles(value) }
            }
        }
        var updateWasSuccessful = true
        try {
            patients.forEach { updateRec ->
                col.updateOneById(
                    updateRec._id,
                    updateRec,
                    options,
                    updateOnlyNotNullProperties = true
                ).wasAcknowledged()
            }
        } catch (exception: MongoException) {
            logger.error(exception) { "error occurred trying to update multiple patients Error: $exception" }
            updateWasSuccessful = false
        }
        return updateWasSuccessful
    }


    /**
     * deletes a patient
     * @param id the id of a patient that should get deleted
     * @return Boolean true if delete operation was successful or false if not
     */
    override fun deleteOne(id: String): Boolean {
        val query = BasicDBObject(gridFsFilterKey, id)
        return try {
            val iterator = gridFSBucket.find(query)
            while (iterator.cursor().hasNext()) {
                gridFSBucket.delete(iterator.cursor().next().id)
            }
            col.deleteOneById(id).wasAcknowledged()

        } catch (exception: MongoException) {
            logger.error(exception) { "error occurred trying to delete a patient Error: $exception" }
            false
        }
    }

    /**
     * deletes all records handed over as parameters
     * @param ids the List with the ids of patients that should get deleted
     * @return Boolean true if deletes were successful or false if not
     */
    fun deleteMany(ids: List<String>): Boolean {
        var deleteSucceeded = true
        ids.forEach { patientToDelete ->
            if (!deleteOne(patientToDelete)) {
                deleteSucceeded = false
            }
        }
        return deleteSucceeded
    }

    /**
     * deletes all currently saved patients from the db
     */
    override fun deleteAll(): Boolean {
        return try {
            col.drop()
            gridFSBucket.drop()
            true
        } catch (exception: MongoException) {
            logger.error(exception) { "error occurred trying to delete all patients Error: $exception" }
            false
        }
    }

    /**
     *helper function to store all pictures of a record to the db
     *
     * @param currentPatient the patient that should store its pictures
     */
    private fun saveFiles(currentPatient: Patient) {
        currentPatient.pictures.forEach { file ->
            run {
                saveFileToDatabase(file, currentPatient)
            }
        }
    }

    /**
     * saves a picture(file) of a record to the db
     * @param file the picture that should be saved
     * @param patientToSave the patient associated with the file
     */
    private fun saveFileToDatabase(file: File, patientToSave: Patient) {
        val query = BasicDBObject(gridFsFilterKey, patientToSave._id)
        val iterator = gridFSBucket.find(query)
        for (foundElement in iterator) {
            if (foundElement.metadata?.getString(originalFileKey) == file.name) {
                gridFSBucket.delete(foundElement.id)
            }
        }

        // unique name for each file->avoids having a filename with special chars. Original filename
        // is stored in metadata and gets restored when downloading the file
        var fileNameUnique = newId<File>().toString()
        fileNameUnique += fileType


        FileInputStream(file).use { streamToUploadFrom ->
            val options = GridFSUploadOptions()
                .chunkSizeBytes(chunkSize)
                .metadata(
                    Document(documentKey, patientToSave._id).append(
                        originalFileKey,
                        file.name
                    )
                )
            gridFSBucket.uploadFromStream(
                fileNameUnique,
                streamToUploadFrom,
                options
            )
        }
    }

    /**
     * loads a file/picture of a stored patient from the db
     * @param currentPatient the patient whose pictures should be loaded
     */
    private fun downloadFile(currentPatient: Patient) {
        val fileList = mutableListOf<File>()
        val downloadOptions = GridFSDownloadOptions().revision(0)
        val query = BasicDBObject(gridFsFilterKey, currentPatient._id)
        val iterator = gridFSBucket.find(query)
        for (file in iterator) {
            try {
                val restoredFilename = file.metadata?.getString(originalFileKey) ?: continue
                FileOutputStream(file.filename).use { streamToDownloadTo ->
                    gridFSBucket.downloadToStream(
                        file.filename,
                        streamToDownloadTo,
                        downloadOptions
                    )
                    streamToDownloadTo.flush()
                    val fileToView = File(file.filename)
                    val outputFile = File(restoredFilename)
                    fileToView.copyTo(outputFile, true)
                    fileList.add(outputFile)
                    fileToView.deleteOnExit()
                    outputFile.deleteOnExit()
                }
            } catch (exception: FileNotFoundException) {
                logger.error(exception) { "The file could not be found, Error: $exception" }
            } catch (exception: SecurityException) {
                logger.error(exception) { "no write access to file granted, Error: $exception" }
            }
        }
        currentPatient.pictures = fileList
    }

    /**
     * Validates the db-name
     *
     * @param dbName the name that should be checked
     * @return the validated name
     */
    private fun validateDatabaseName(dbName: String): String {

        return dbValidateRegex.replace(dbName, "")
    }

    fun countEntries(): Long {
        return col.countDocuments()
    }

}
