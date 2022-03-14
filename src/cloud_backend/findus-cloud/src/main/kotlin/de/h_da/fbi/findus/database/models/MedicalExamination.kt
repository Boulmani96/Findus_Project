package de.h_da.fbi.findus.database.models

import kotlinx.serialization.Contextual
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId
import java.io.File
import java.time.LocalDate

/**
 * MedicalExamination this data class is used to store all information about a physical examination of an animal to the db
 *
 * @property patientIdentifier the patientIdentifier of the animal
 * @property birthDate the birthdate of the animal
 * @property age the age of the animal
 * @property animalBreed the breed of the animal
 * @property examinationDate the medical examination date for the animal
 * @property existingDisease the existing disease(es) of the animal
 * @property bodyMarker markers that mark special positions of the animal
 * @property muscleCircumference the circumference of different muscles of the animal
 * @property movement the movements the animal has performed
 * @property bodyPosture the body posture of the animal
 * @property breathMovement the breath movement of the animal
 * @property symptoms the symptoms e.g. pain the animal currently has
 * @property pictures the pictures of a specific check-up
 * @constructor Create a MedicalExaminationAPI instance that can be saved to the db
 */
data class MedicalExamination(
    // this is the anonym identifier of a patient (a patient can have multiple examinations)
    var patientIdentifier: String,
    @Contextual
    var birthDate: LocalDate,
    var age: Int,
    var animalBreed: String,
    @Contextual
    var examinationDate: LocalDate?,
    var existingDisease: List<String>,
    var bodyMarker: List<MedicalFinding?>,
    var muscleCircumference: List<Float?>,
    var movement: List<String?>,
    var bodyPosture: String?,
    var breathMovement: String?,
    var symptoms: List<String?>,
    @Transient
    var pictures: List<File> = listOf(),
    // this is the unique id of the examination
    @BsonId override val _id: String = newId<MedicalExamination>().toString(),
) : ExaminationModel
