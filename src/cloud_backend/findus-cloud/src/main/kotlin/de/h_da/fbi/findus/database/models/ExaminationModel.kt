package de.h_da.fbi.findus.database.models

/**
 * Examination model a basic interface that can be implemented for classes that should be stored in the db
 *
 * @constructor Creates a valid ExaminationModel
 */
interface ExaminationModel {
    //default naming to mark the ObjectId of a mongoDB document
    val _id: String
}
