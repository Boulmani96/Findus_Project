package de.h_da.fbi.findus.database.models

/**
 * this class holds all values required to create a bodyMap
 *
 * @property id the id of the bodyMap
 * @property fieldValues all values required to create the bodyMap
 * @constructor Creates a valid bodyMap
 */
data class BodyMap(
    val id: String,
    val fieldValues: List<FieldValue>
)
