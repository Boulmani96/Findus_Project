package de.h_da.fbi.findus.database.models

/**
 * data class that represents the status of an organ of the patient
 *
 */
data class Organ(
    var name: String,
    var status: Status,
    var statusChanged: StatusChanged,
    var comment: String,
    var type: Type
)

/**
 * data class that represents the status of a muscle of the patient
 *
 */
data class Muscle(
    var name: String,
    var status: Status,
    var comment: String,
    var type: Type,
    val checkedStateInjury: Boolean,
    val checkedStateSwelling: Boolean,
    val checkedStateHematoma: Boolean
)

/**
 * data class that represents a filled anamnesis book
 *
 */
data class Anamnese(
    var id: String,
    var date: String,
    var organs: List<Organ> = listOf(),
    var muscles: List<Muscle> = listOf(),
)
