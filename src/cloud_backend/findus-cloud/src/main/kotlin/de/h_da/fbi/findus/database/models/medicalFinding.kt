package de.h_da.fbi.findus.database.models

/**
 * Medical finding enumerates different possible markers that have been set to mark special positions
 * e.g. at an image of an animal.
 *
 * @constructor Creates a valid instance
 */
enum class MedicalFinding {
    PENDING, ROSE, BLUE, GREEN, YELLOW, ORANGE, RED
}
