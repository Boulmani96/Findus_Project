package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Request
import de.h_da.fbi.findus.database.models.MedicalFinding
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

const val timeOffset = "UTC+1"

/**
 * MedicalExaminationAPI this data class is used to store all information about a physical examination of an animal,
 * it is used to receive this information about an animal from an api
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
 * @constructor Create a MedicalExaminationAPI instance
 */
@Request("A request to create a medical examination.")
@Serializable
data class MedicalExaminationAPI(
    @Required
    var patientIdentifier: String,
    @Required
    var birthDate: LocalDateTime,
    @Required
    var age: Int,
    @Required
    var animalBreed: String,
    var examinationDate: LocalDateTime,
    var existingDisease: List<String>,
    var bodyMarker: List<MedicalFinding?>,
    var muscleCircumference: List<Float?>,
    var movement: List<String?>,
    var bodyPosture: String?,
    var breathMovement: String?,
    var symptoms: List<String?>,
) {
    companion object {
        val EXAMPLE = MedicalExaminationAPI(
            patientIdentifier = "someDog",
            birthDate = Clock.System.now().toLocalDateTime(TimeZone.of(timeOffset)),
            age = 10,
            animalBreed = "Beagle",
            examinationDate = Clock.System.now().toLocalDateTime(TimeZone.of(timeOffset)),
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(
                MedicalFinding.PENDING,
                null,
                null,
                MedicalFinding.RED
            ),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptome")
        )
    }
}
