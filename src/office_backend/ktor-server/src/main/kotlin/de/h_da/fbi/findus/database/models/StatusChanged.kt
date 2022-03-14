package de.h_da.fbi.findus.database.models

/**
 * Indicates whether the status of a patient's organ or muscle changed
 *
 */
enum class StatusChanged {
    UNKNOWN,
    ANECHOGEN,
    HYPOECHOGEN,
    ISOECHOGEN,
    HYPERECHOGEN,
}
