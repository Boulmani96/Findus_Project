package de.h_da.fbi.findus.database.models

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId
import java.io.File

/**
 * the sex of the patient
 *
 * @constructor Creates a valid enum pbject
 */
enum class Sex {
    MALE,
    FEMALE
}

/**
 * Patient
 *
 * @property _id the id that is used to store the patient object in the database
 * @property name the name of the patient
 * @property weight the weight of the patient
 * @property pictures the pictures of the patient
 * @property comment the comment to the patient
 * @property sex the gender of the patient
 * @property birthDate the birthDate of the patient
 * @property species the species of the patient
 * @property ownerName the owner's name
 * @property ownerAddress the owner's address
 * @property ownerPhoneNumber the owner's phone number
 * @property diagnostic a diagnose about the patient
 * @property diagnosticSymptoms symptoms that support the diagnose
 * @property diagnosticNotes notes to the diagnose
 * @property chartDataWeight a ChartData value of the weight
 * @property chartDataMuscle a ChartData value about the muscle
 * @property anamnese holds a list of anamnese objects about the patient
 * @property bodyMaps holds a list of bodymap objects about the patient
 * @constructor Creates a patient instance
 */
data class Patient(
    @BsonId override val _id: String = newId<Patient>().toString(),
    var name: String,
    var weight: Float,
    @Transient
    var pictures: List<File> = listOf(),
    var comment: String,
    var sex: Sex,
    var birthDate: String,
    var species: String,

    var ownerName: String,
    var ownerAddress: String,
    var ownerPhoneNumber: String,

    var diagnostic: String = "None",
    var diagnosticSymptoms: List<String> = listOf(),
    var diagnosticNotes: List<String> = listOf(),

    var chartDataWeight: List<ChartData> = listOf(),
    var chartDataMuscle: List<ChartData> = listOf(),

    var anamnese: List<Anamnese> = listOf(),
    var bodyMaps: List<BodyMap> = listOf(),
) : PatientModel
