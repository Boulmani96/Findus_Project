package de.h_da.fbi.findus.database.models

/**
 * This class represents a markable position in a bodyMap
 *
 * @property idOfField the unique id of the position
 * @property painIntensity the painIntensity at this position
 * @property tensionIntensity the tension Intensity at this position
 * @constructor Creates a valid FieldValue
 */
data class FieldValue(
    val idOfField: String,
    val painIntensity: Int,
    val tensionIntensity: Int
)