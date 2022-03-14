package de.h_da.fbi.findus.database.repository

import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import de.h_da.fbi.findus.database.models.PatientModel
import mu.KLogger
import org.bson.types.ObjectId
import org.litote.kmongo.findOneById
import org.litote.kmongo.id.toId

/**
 * Repository this is a basic interface that can be implemented in order to save data classes
 * to the db. Therefore, the given functions should be overwritten.
 *
 * @param T the dataclass that should be used to perform CRUD-operations onto
 * @constructor Creates a valid repository for the given class T
 */
interface RepositoryInterface<T> {

    var col: MongoCollection<T>
    val databaseName: String
    val collectionName: String
    var logger: KLogger

    /**
     * Adds a patient to the db
     *
     * @param patient the patient that should get added
     * @return the id of the inserted element if insertion operation was successful
     */
    fun addOne(patient: T): String

    /**
     * Adds multiple patients to the db
     *
     * @param patients the patients that should get added
     * @return List<String> the inserted ids
     */
    fun addMany(patients: List<T>): List<String>

    /**
     * searches for a patient with a given id in the db
     *
     * @param id the id to search for
     * @return the found element or null, when not found
     */
    fun getById(id: String): T?

    /**
     * Finds all stored patients
     * @return a list of all stored patients
     */
    fun findAll(): List<T>

    /**
     * Update a patient model
     *
     * @param patient the patient that should get updated
     * @return true if update was successful, else false
     */
    fun updateOne(patient: T): Boolean

    /**
     * Updates multiple patient models
     *
     * @param patients the patients that should get updated
     * @return true if update was successful, else false
     */
    fun updateMany(patients: List<T>): Boolean

    /**
     * Deletes the db representation of an object with the given id
     *
     * @param id the id to search for
     * @return true if delete operation was successful, else false
     */
    fun deleteOne(id: String): Boolean

    /**
     * Deletes all currently stored patients in the collection
     * @return Boolean true if delete operation was successful or false if not
     */
    fun deleteAll(): Boolean

    /**
     * Checks whether a db representation of an object with the given id exists
     *
     * @param id the id to search for
     * @return true if id was found, else false
     */
    fun checkExists(id: String): Boolean {
        try {
            col.findOneById(id) ?: return false
            return true
        } catch (exception: MongoException) {
            logger.error(exception) { "error occurred trying to add a patient Error: $exception" }
            return false
        }
    }

    /**
     * Checks if the given id is in a valid format
     *
     * @param id the id to check
     * @return true if the id is valid, else false
     */
    fun isValidId(id: String): Boolean {

        return try {
            ObjectId(id).toId<PatientModel>()
            true
        } catch (exception: IllegalArgumentException) {
            logger.error(exception) { "error, id does not have a valid format : $exception" }
            false
        }
    }
}
