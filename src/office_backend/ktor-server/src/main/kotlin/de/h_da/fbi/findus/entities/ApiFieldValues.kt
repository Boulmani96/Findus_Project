package de.h_da.fbi.findus.entities
import kotlinx.serialization.Serializable

/**
 * This class represents a markable position in a bodyMap
 *
 * @property id the id of the position
 * @property painIntensity the painIntensity at this position
 * @property tensionIntensity the tension Intensity at this position
 * @constructor Creates a valid ApiFieldValues object
 */
@Serializable
data class ApiFieldValues(
    val id: String,
    val painIntensity: Int,
    val tensionIntensity: Int
)