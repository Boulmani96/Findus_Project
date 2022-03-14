package de.h_da.fbi.findus

import de.h_da.fbi.findus.database.di.cloudDatabaseModule
import de.h_da.fbi.findus.database.models.MedicalExamination
import de.h_da.fbi.findus.database.models.MedicalFinding
import de.h_da.fbi.findus.database.services.CloudService
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.test.KoinTest
import java.io.File
import java.time.LocalDate
import kotlin.test.assertContains
import kotlin.test.assertFailsWith

class CloudServiceTest : KoinTest {

    private val invalidMedicalExaminationMessage: String =
        "the medical examination argument does not have a valid format"
    private val path = "./src/test/kotlin/de/h_da/fbi/findus/"

    @Before
    fun setup() {
        startKoin { modules(cloudDatabaseModule) }
        CloudService().deleteAllCheckUps()
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun testInsertInvalidAge() {
        val service = CloudService()
        val birthdate = LocalDate.of(2010, 1, 8)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "testInsert",
            birthDate = birthdate,
            age = -3,
            animalBreed = "Beagle",
            examinationDate = LocalDate.now(),
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.GREEN),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms")
        )
        val exception =
            assertFailsWith<IllegalArgumentException> {
                service.createMedicalExamination(
                    cloudDataEntry
                )
            }
        exception.message?.let {
            assertContains(
                invalidMedicalExaminationMessage,
                it
            )
        }
        val results = service.getAllExaminations()
        assertEquals(0, results.size)
    }

    @Test
    fun testInsertInvalidBirthdateIsBeforeOldestAllowedDateEdgeCase() {
        val service = CloudService()
        val current = LocalDate.now()
        val birthdate = LocalDate.of(1974, 12, 31)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "testInsert",
            birthDate = birthdate,
            age = 10,
            animalBreed = "Beagle",
            examinationDate = current,
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.ROSE),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms")
        )
        val exception =
            assertFailsWith<IllegalArgumentException> {
                service.createMedicalExamination(
                    cloudDataEntry
                )
            }
        exception.message?.let {
            assertContains(
                invalidMedicalExaminationMessage,
                it
            )
        }
        val results = service.getAllExaminations()
        assertEquals(0, results.size)
    }

    @Test
    fun testInsertInValidBirthdateIsBeforeOldestAllowedDate() {
        val service = CloudService()
        val current = LocalDate.now()
        val birthdate = LocalDate.of(1900, 12, 1)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "testInsert",
            birthDate = birthdate,
            age = 10,
            animalBreed = "Beagle",
            examinationDate = current,
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.BLUE),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms")
        )
        val exception =
            assertFailsWith<IllegalArgumentException> {
                service.createMedicalExamination(
                    cloudDataEntry
                )
            }
        exception.message?.let {
            assertContains(
                invalidMedicalExaminationMessage,
                it
            )
        }
        val results = service.getAllExaminations()
        assertEquals(0, results.size)
    }

    @Test
    fun testInsertInvalidBirthdateIsAfterAllowedDateEdgeCase() {
        val service = CloudService()
        val current = LocalDate.now()
        val birthdate = current.plusDays(1)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "testInsert",
            birthDate = birthdate,
            age = 10,
            animalBreed = "Beagle",
            examinationDate = current,
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.RED),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms")
        )
        val exception =
            assertFailsWith<IllegalArgumentException> {
                service.createMedicalExamination(
                    cloudDataEntry
                )
            }
        exception.message?.let {
            assertContains(
                invalidMedicalExaminationMessage,
                it
            )
        }
        val results = service.getAllExaminations()
        assertEquals(0, results.size)
    }

    @Test
    fun testInsertInvalidBirthdateIsAfterAllowedDate() {
        val service = CloudService()
        val birthdate = LocalDate.of(2200, 12, 1)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "testInsert",
            birthDate = birthdate,
            age = 10,
            animalBreed = "Beagle",
            examinationDate = LocalDate.now(),
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.YELLOW),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms")
        )
        val exception =
            assertFailsWith<IllegalArgumentException> {
                service.createMedicalExamination(
                    cloudDataEntry
                )
            }
        exception.message?.let {
            assertContains(
                invalidMedicalExaminationMessage,
                it
            )
        }
        val results = service.getAllExaminations()
        assertEquals(0, results.size)
    }

    @Test
    fun testInsertInvalidMedicalExaminationDateIsBeforeBirthdateEdgeCase() {
        val service = CloudService()
        val current = LocalDate.now()
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "testInsert",
            birthDate = current,
            age = 10,
            animalBreed = "Beagle",
            examinationDate = current.minusDays(1),
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.YELLOW),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms")
        )
        val exception =
            assertFailsWith<IllegalArgumentException> {
                service.createMedicalExamination(
                    cloudDataEntry
                )
            }
        exception.message?.let {
            assertContains(
                invalidMedicalExaminationMessage,
                it
            )
        }
        val results = service.getAllExaminations()
        assertEquals(0, results.size)
    }

    @Test
    fun testInsertInvalidMedicalExaminationDateIsBeforeBirthdate() {
        val service = CloudService()
        val birthdate = LocalDate.of(2010, 12, 1)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "testInsert",
            birthDate = birthdate,
            age = 10,
            animalBreed = "Beagle",
            examinationDate = LocalDate.of(2000, 12, 1),
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.YELLOW),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms")
        )
        val exception =
            assertFailsWith<IllegalArgumentException> {
                service.createMedicalExamination(
                    cloudDataEntry
                )
            }
        exception.message?.let {
            assertContains(
                invalidMedicalExaminationMessage,
                it
            )
        }
        val results = service.getAllExaminations()
        assertEquals(0, results.size)
    }

    @Test
    fun testInsertvalidMedicalExaminationPatient() {
        val service = CloudService()
        val current = LocalDate.now()
        val birthdate = LocalDate.of(2010, 1, 8)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "testInsert",
            birthDate = birthdate,
            age = 10,
            animalBreed = "Beagle",
            examinationDate = current,
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.ORANGE),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms")
        )
        val resultId = service.createMedicalExamination(cloudDataEntry)
        val results = service.getAllExaminations()
        assertEquals(cloudDataEntry._id, resultId)
        assertEquals(1, results.size)
    }

    @Test
    fun testInsertNonRequiredValuesCanBeNull() {
        val service = CloudService()
        val current = LocalDate.now()
        val birthdate = LocalDate.of(2010, 1, 8)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "testInsert",
            birthDate = birthdate,
            age = 15,
            animalBreed = "Labrador",
            examinationDate = current,
            existingDisease = listOf(),
            bodyMarker = listOf(null, null),
            muscleCircumference = listOf(null, null, null),
            movement = listOf(null, null, null, null, null, null),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
        )
        val resultId = service.createMedicalExamination(cloudDataEntry)
        val results = service.getAllExaminations()
        assertEquals(cloudDataEntry._id, resultId)
        assertEquals(1, results.size)
        assertNull(results.first().bodyPosture)
        assertNull(results.first().breathMovement)
        assertNotNull(results.first().age)
        assertEquals(3, results.first().muscleCircumference.size)
    }

    @Test
    fun testInsertInvalidNameFails() {
        val service = CloudService()
        val current = LocalDate.now()
        val birthdate = LocalDate.of(2010, 1, 8)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "",
            birthDate = birthdate,
            age = 10,
            animalBreed = "German Shepherd",
            examinationDate = current,
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.ORANGE),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms")
        )
        val exception =
            assertFailsWith<IllegalArgumentException> {
                service.createMedicalExamination(
                    cloudDataEntry
                )
            }
        exception.message?.let {
            assertContains(
                invalidMedicalExaminationMessage,
                it
            )
        }
        val results = service.getAllExaminations()
        assertEquals(0, results.size)
    }

    @Test
    fun testInsertInvalidIdFails() {
        val service = CloudService()
        val current = LocalDate.now()
        val birthdate = LocalDate.of(2010, 1, 8)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "dog",
            birthDate = birthdate,
            age = 10,
            animalBreed = "Husky",
            examinationDate = current,
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.ORANGE),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms"),
            _id = "abc"
        )
        val exception =
            assertFailsWith<IllegalArgumentException> {
                service.createMedicalExamination(
                    cloudDataEntry
                )
            }
        exception.message?.let {
            assertContains(
                invalidMedicalExaminationMessage,
                it
            )
        }
        val results = service.getAllExaminations()
        assertEquals(0, results.size)
    }

    @Test
    fun testInsertIdEmptyStringFails() {
        val service = CloudService()
        val current = LocalDate.now()
        val birthdate = LocalDate.of(2010, 1, 8)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "dog",
            birthDate = birthdate,
            age = 10,
            animalBreed = "Husky",
            examinationDate = current,
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.ORANGE),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms"),
            _id = ""
        )
        val exception =
            assertFailsWith<IllegalArgumentException> {
                service.createMedicalExamination(
                    cloudDataEntry
                )
            }
        exception.message?.let {
            assertContains(
                invalidMedicalExaminationMessage,
                it
            )
        }
        val results = service.getAllExaminations()
        assertEquals(0, results.size)
    }

    @Test
    fun testInsertEmptyAnimalBreedFails() {
        val service = CloudService()
        val current = LocalDate.now()
        val birthdate = LocalDate.of(2010, 1, 8)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "dog",
            birthDate = birthdate,
            age = 10,
            animalBreed = "",
            examinationDate = current,
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.ORANGE),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms")
        )
        val exception =
            assertFailsWith<IllegalArgumentException> {
                service.createMedicalExamination(
                    cloudDataEntry
                )
            }
        exception.message?.let {
            assertContains(
                invalidMedicalExaminationMessage,
                it
            )
        }
        val results = service.getAllExaminations()
        assertEquals(0, results.size)
    }

    @Test
    fun testLoadAllEntriesSucceeds() {
        val service = CloudService()
        val current = LocalDate.now()
        val birthdate = LocalDate.of(2010, 1, 8)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "testInsert",
            birthDate = birthdate,
            age = 10,
            animalBreed = "Beagle",
            examinationDate = current,
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.ORANGE),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms")
        )
        val cloudDataEntry2 = MedicalExamination(
            patientIdentifier = "testInsert2",
            birthDate = birthdate,
            age = 10,
            animalBreed = "Husky",
            examinationDate = current,
            existingDisease = listOf("asthma", "broken leg", "more broken legs"),
            bodyMarker = listOf(
                MedicalFinding.PENDING,
                MedicalFinding.YELLOW,
                null,
                MedicalFinding.ROSE
            ),
            muscleCircumference = listOf(23.1f, 25.3f, 12.3f),
            movement = listOf("move1", null),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf(null, "symptoms")
        )

        service.createMedicalExamination(cloudDataEntry)
        service.createMedicalExamination(cloudDataEntry2)
        val results = service.getAllExaminations()
        assertEquals(2, results.size)
        results.forEach {
            run {
                assertTrue(it.patientIdentifier != "")
                assertTrue(it.animalBreed != "")
            }
        }
    }

    @Test
    fun testLoadMedicalExaminationPatient() {
        val service = CloudService()
        val current = LocalDate.now()
        val birthdate = LocalDate.of(2010, 1, 8)
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "testInsert",
            birthDate = birthdate,
            age = 10,
            animalBreed = "Beagle",
            examinationDate = current,
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.ORANGE),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms")
        )
        val resultId = service.createMedicalExamination(cloudDataEntry)
        val foundExamination = service.getExaminationById(resultId)
        assertNotNull(foundExamination)
        foundExamination?.let {
            assertEquals(cloudDataEntry.patientIdentifier, foundExamination.patientIdentifier)
        }
    }

    @Test
    fun testUploadFile() {
        val service = CloudService()
        val current = LocalDate.now()
        val birthdate = LocalDate.of(2010, 1, 8)
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val cloudDataEntry = MedicalExamination(
            patientIdentifier = "testInsert",
            birthDate = birthdate,
            age = 10,
            animalBreed = "Beagle",
            examinationDate = current,
            existingDisease = listOf("asthma", "broken leg"),
            bodyMarker = listOf(MedicalFinding.PENDING, null, null, MedicalFinding.ORANGE),
            muscleCircumference = listOf(23.1f, 25.3f),
            movement = listOf("move1", "move2"),
            bodyPosture = null,
            breathMovement = null,
            symptoms = listOf("some", "symptoms"),
            pictures = listOf(file, file2),
        )
        val resultId = service.createMedicalExamination(cloudDataEntry)
        service.uploadExaminationImages(cloudDataEntry)
        val foundExamination = service.getExaminationById(resultId)
        assertNotNull(foundExamination)
        foundExamination?.let {
            assertEquals(cloudDataEntry.patientIdentifier, foundExamination.patientIdentifier)
            assertNotEquals(cloudDataEntry.pictures.size, foundExamination.pictures.size)
        }
        file.deleteOnExit()
        file2.deleteOnExit()
    }
}
