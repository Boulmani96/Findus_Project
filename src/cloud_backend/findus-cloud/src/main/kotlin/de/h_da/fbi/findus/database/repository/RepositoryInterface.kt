package de.h_da.fbi.findus.database.repository

import com.mongodb.client.MongoCollection
import mu.KLogger

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
     * Adds a check-up to the db
     *
     * @param examination the MedicalExamination that should get added
     * @return the id of the inserted MedicalExamination if insertion operation was successful
     */
    fun addOne(examination: T): String

    /**
     * Finds all stored check-ups
     * @return a list of all stored MedicalExaminations
     */
    fun findAll(): List<T>

    /**
     * Deletes all currently stored medicalExaminations in the collection
     * @return Boolean true if delete operation was successful or false if not
     */
    fun deleteAll(): Boolean

    /**
     * searches for a check-up with a given id in the db
     *
     * @param id the id to search for
     * @return the found check-up or null, when not found
     */
    fun getById(id: String): T?
}
