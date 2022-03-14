package de.h_da.fbi.findus.database.repository

import com.mongodb.MongoClientException
import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import com.mongodb.client.gridfs.GridFSBucket
import com.mongodb.client.gridfs.GridFSBuckets
import com.mongodb.client.gridfs.model.GridFSUploadOptions
import de.h_da.fbi.findus.database.models.ExaminationModel
import de.h_da.fbi.findus.database.models.MedicalExamination
import mu.KotlinLogging
import org.bson.Document
import org.bson.types.ObjectId
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection
import org.litote.kmongo.id.toId
import org.litote.kmongo.newId
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

/**
 * MedicalExamination repository: this class handles the communication with a MongoDB instance. All CRUD-operations
 * are performed here
 *
 * @constructor instantiates a MedicalExaminationRepository instance that manages the db communication
 *
 * @param client a client side representation of a mongo cluster
 */
class MedicalExaminationRepository(client: MongoClient) : RepositoryInterface<MedicalExamination> {
    override lateinit var col: MongoCollection<MedicalExamination>
    override val databaseName: String = "medicalExaminationDB"
    override val collectionName: String = "examinations"
    override var logger = KotlinLogging.logger {}
    private val dbValidateRegex = Regex("[^A-Za-z]")
    private var gridFSBucket: GridFSBucket
    private val bucketName: String = "images"
    private val fileType: String = ".jpg"
    private val documentKey: String = "medicalExaminationId"
    private val originalFileKey: String = "originalFileName"
    private val chunkSize: Int = 1048576

    init {
        val database = client.getDatabase(validateDatabaseName(databaseName))
        col = database.getCollection<MedicalExamination>(collectionName)
        gridFSBucket = GridFSBuckets.create(database, bucketName)
    }

    /**
     * inserts a medical examination into the db
     * @param examination the medical examination to insert into the database
     * @return String the id of the medical examination if the medical examination was successfully inserted
     * @throws MongoException when an error occurred during insertion
     */
    override fun addOne(examination: MedicalExamination): String {
        try {
            col.insertOne(examination)
            return examination._id
        } catch (exception: MongoException) {
            logger.error(exception) { "error occurred trying to add a medical examination Error: $exception" }
            throw exception
        }
    }

    /**
     * takes the id of a medical examination and returns all stored information
     * of this medical examination by searching for the id of the medical examination
     * @param id the id of a medical examination to search for
     * @return the MedicalExamination with the given id if found or null if not found
     * @throws MongoException when an error occurred during get operation
     */
    override fun getById(id: String): MedicalExamination? {
        try {
            return col.findOne(MedicalExamination::_id eq id)
        } catch (exception: MongoException) {
            logger.error(exception) { "error occurred trying to get a medical examination Error: $exception" }
            throw exception
        }
    }

    /**
     * returns all saved medical examinations
     * @return List<MedicalExamination> the list of type MedicalExamination
     * @throws MongoException when an error occurred during get operation
     */
    override fun findAll(): List<MedicalExamination> {
        try {
            return col.find().asIterable().map { it }
        } catch (exception: MongoException) {
            logger.error(exception) { "error occurred finding all medical examinations Error: $exception" }
            throw exception
        }
    }

    /**
     * deletes all currently saved medical examinations from the db
     * @return Boolean true if delete operation was successful or false if not
     */
    override fun deleteAll(): Boolean {
        return try {
            col.drop()
            true
        } catch (exception: MongoClientException) {
            logger.error(exception) { "error occurred trying to delete all medical examinations Error: $exception" }
            false
        }
    }

    /**
     * Uploads all saved images from the examination element to the db
     *
     * @param examination the element that holds the images that should get uploaded
     * @return true if upload was successful, else false
     */
    fun uploadImages(examination: MedicalExamination): Boolean {
        examination.pictures.forEach { file ->
            run {
                // unique patientIdentifier for each file-> avoids having a filename with special chars. Original filename
                // is stored in metadata and gets restored when downloading the file
                var fileNameUnique = newId<File>().toString()
                fileNameUnique += fileType

                try {
                    FileInputStream(file).use { streamToUploadFrom ->
                        val options = GridFSUploadOptions()
                            .chunkSizeBytes(chunkSize)
                            .metadata(
                                Document(documentKey, examination._id).append(
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
                } catch (exception: FileNotFoundException) {
                    logger.error(exception) { "error occurred trying save a file Error: $exception" }
                    return false
                }
            }
        }
        return true
    }

    /**
     * Validates the db-patientIdentifier
     *
     * @param dbName the patientIdentifier that should be checked
     * @return the validated patientIdentifier
     */
    private fun validateDatabaseName(dbName: String): String {

        return dbValidateRegex.replace(dbName, "")
    }

    /**
     * Checks if the given id is in a valid format
     *
     * @param id the id to check
     * @return true if the id is valid, else false
     */
    fun isValidId(id: String): Boolean {

        return try {
            ObjectId(id).toId<ExaminationModel>()
            true
        } catch (exception: IllegalArgumentException) {
            logger.error(exception) { "error, id does not have a valid format : $exception" }
            false
        }
    }
}
