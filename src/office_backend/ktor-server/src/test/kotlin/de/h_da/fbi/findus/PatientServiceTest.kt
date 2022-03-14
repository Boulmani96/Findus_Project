package de.h_da.fbi.findus

import de.h_da.fbi.findus.database.di.databaseModule
import de.h_da.fbi.findus.database.models.BodyMap
import de.h_da.fbi.findus.database.models.FieldValue
import de.h_da.fbi.findus.database.models.Patient
import de.h_da.fbi.findus.database.models.Sex
import de.h_da.fbi.findus.database.services.PatientService
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.test.KoinTest
import java.io.File
import kotlin.test.assertContains
import kotlin.test.assertFailsWith

class PatientServiceTest : KoinTest {

    private val path = "./src/test/kotlin/de/h_da/fbi/findus/"
    private val invalidPatientMessage: String = "the patient argument does not have a valid format"

    @Before
    fun setup() {
        startKoin { modules(databaseModule(isTesting = true)) }
        PatientService().deleteAllPatients()
    }

    @After
    fun teardown() {
        stopKoin()
    }

    @Test
    fun testInsertPatientWithInvalidIdFails() {
        val service = PatientService()
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val patient1 = Patient(
            _id = "ÄÜÖ",
            name = "testRecord",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val exceptionSingleInsert =
            assertFailsWith<IllegalArgumentException> { service.createPatient(patient1) }
        exceptionSingleInsert.message?.let {
            assertContains(
                invalidPatientMessage,
                it
            )
        }
        val foundPatients = service.getAllPatients()
        assertEquals(0, foundPatients.size)
        file.deleteOnExit()
    }

    @Test
    fun testInsertPatientWithEmptyNameFails() {
        val service = PatientService()
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val patient1 = Patient(
            name = "",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )

        val exception =
            assertFailsWith<IllegalArgumentException> { service.createPatient(patient1) }
        exception.message?.let {
            assertContains(
                invalidPatientMessage,
                it
            )
        }
        val foundPatients = service.getAllPatients()
        assertEquals(0, foundPatients.size)
        file.deleteOnExit()
    }

    @Test
    fun testInsertPatientWithInvalidIdNumberFails() {
        val service = PatientService()
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val patient1 = Patient(
            _id = "123",
            name = "testRecord",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val exceptionSingleInsert =
            assertFailsWith<IllegalArgumentException> { service.createPatient(patient1) }
        exceptionSingleInsert.message?.let {
            assertContains(
                invalidPatientMessage,
                it
            )
        }
        val foundPatients = service.getAllPatients()
        assertEquals(0, foundPatients.size)
        file.deleteOnExit()
    }

    @Test
    fun testInsertPatientWithInvalidModifiedIdNumberFails() {
        val service = PatientService()
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val patientToTest = Patient(
            name = "testRecord",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )

        val patient1 = Patient(
            _id = patientToTest._id + "1",
            name = "testRecord",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val exceptionSingleInsert =
            assertFailsWith<IllegalArgumentException> { service.createPatient(patient1) }
        exceptionSingleInsert.message?.let {
            assertContains(
                invalidPatientMessage,
                it
            )
        }
        val foundPatients = service.getAllPatients()
        assertEquals(0, foundPatients.size)
        file.deleteOnExit()
    }

    @Test
    fun testInsertInvalidPatientFileNameSucceeds() {
        val service = PatientService()
        val file = File(path + "data\$!-a1.jpg")
        file.createNewFile()
        val patient1 = Patient(
            name = "testRecord",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val resultId = service.createPatient(patient1)
        assertEquals(patient1._id, resultId)
        val foundPatients = service.getAllPatients()
        assertEquals(1, foundPatients.size)
        assertEquals(file.name, foundPatients.first().pictures.first().name)
        file.deleteOnExit()
    }

    @Test
    fun testInsertInvalidPatientFileSucceedsSpecialFileName() {
        val service = PatientService()
        val file = File(path + "dataÄÖÜa1.jpg")
        file.createNewFile()
        val file2 = File(path + "dataöäü1.jpg")
        file2.createNewFile()
        val patient1 = Patient(
            name = "testRecordÄÖÜÜÜÜÜ",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "ÄÖ!%comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "testRecord2",
            weight = 23.3f,
            pictures = listOf(file, file2),
            comment = "comment2",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val resultIds = service.createMultiplePatients(listOf(patient1, patient2))
        assertEquals(2, resultIds.size)
        val foundPatients = service.getAllPatients()
        assertEquals(2, foundPatients.size)
        val foundPatient1 = service.getPatientById(patient1._id)
        foundPatient1?.let {
            assertEquals(
                patient1.pictures.first().name,
                foundPatient1.pictures.first().name
            )
        }
        file.deleteOnExit()
        file2.deleteOnExit()
    }

    @Test
    fun testInsertPatientWithSpecialCharsSucceeds() {
        val service = PatientService()
        val file = File(path + "data\$!-a1.jpg")
        file.createNewFile()
        val patient1 = Patient(
            name = "testRecord",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val resultId = service.createPatient(patient1)
        assertEquals(patient1._id, resultId)
        val foundPatients = service.getAllPatients()
        assertEquals(1, foundPatients.size)
        file.deleteOnExit()
    }

    @Test
    fun testInsertPatientWithWeightMaxFloatSizeFails() {
        val service = PatientService()
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val patient1 = Patient(
            name = "testRecord",
            weight = Float.MAX_VALUE,
            pictures = listOf(file),
            comment = "ÄÖ!%comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "testRecord2",
            weight = 23.3f,
            pictures = listOf(file, file2),
            comment = "comment2",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val exceptionMultipleInserts = assertFailsWith<IllegalArgumentException> {
            service.createMultiplePatients(
                listOf(
                    patient1,
                    patient2
                )
            )
        }
        exceptionMultipleInserts.message?.let {
            assertContains(
                invalidPatientMessage,
                it
            )
        }
        val foundPatients = service.getAllPatients()
        assertEquals(0, foundPatients.size)
        file.deleteOnExit()
        file2.deleteOnExit()
    }

    @Test
    fun testInsertPatientWithSmallestValidFloatSizeSucceeds() {
        val service = PatientService()
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val patient1 = Patient(
            name = "testRecord",
            weight = Float.MIN_VALUE,
            pictures = listOf(file),
            comment = "ÄÖ!%comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "testRecord2",
            weight = 23.3f,
            pictures = listOf(file, file2),
            comment = "comment2",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val resultIds = service.createMultiplePatients(listOf(patient1, patient2))
        assertEquals(2, resultIds.size)
        assertTrue(resultIds.contains(patient1._id))
        val foundPatients = service.getAllPatients()
        assertEquals(2, foundPatients.size)
        file.deleteOnExit()
        file2.deleteOnExit()
    }

    @Test
    fun testInsertPatientsWithWeightMinAndMaxFloatSizeFails() {
        val service = PatientService()
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val patient1 = Patient(
            name = "testRecord",
            weight = Float.POSITIVE_INFINITY,
            pictures = listOf(file),
            comment = "ÄÖ!%comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "testRecord2",
            weight = Float.NEGATIVE_INFINITY,
            pictures = listOf(file, file2),
            comment = "comment2",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val exceptionMultipleInsert = assertFailsWith<IllegalArgumentException> {
            service.createMultiplePatients(
                listOf(
                    patient1,
                    patient2
                )
            )
        }
        exceptionMultipleInsert.message?.let {
            assertContains(
                invalidPatientMessage,
                it
            )
        }
        val foundPatients = service.getAllPatients()
        assertEquals(0, foundPatients.size)
        file.deleteOnExit()
        file2.deleteOnExit()
    }

    @Test
    fun testInsertPatientsWithNegativeWeightFails() {
        val service = PatientService()
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val patient1 = Patient(
            name = "testRecord",
            weight = -1000.0f,
            pictures = listOf(file),
            comment = "ÄÖ!%comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "testRecord2",
            weight = Float.NEGATIVE_INFINITY,
            pictures = listOf(file, file2),
            comment = "comment2",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val exceptionSingleInsert = assertFailsWith<IllegalArgumentException> {
            service.createMultiplePatients(
                listOf(
                    patient1,
                    patient2
                )
            )
        }
        exceptionSingleInsert.message?.let {
            assertContains(
                invalidPatientMessage,
                it
            )
        }
        val foundPatients = service.getAllPatients()
        assertEquals(0, foundPatients.size)
        file.deleteOnExit()
        file2.deleteOnExit()
    }

    @Test
    fun testInsertPatientsWithWeightZeroFails() {
        val service = PatientService()
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val patient1 = Patient(
            name = "testRecord",
            weight = 0.0f,
            pictures = listOf(file),
            comment = "ÄÖ!%comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "testRecord2",
            weight = 0.0f,
            pictures = listOf(file, file2),
            comment = "comment2",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val exceptionMultipleInserts = assertFailsWith<IllegalArgumentException> {
            service.createMultiplePatients(
                listOf(
                    patient1,
                    patient2
                )
            )
        }
        exceptionMultipleInserts.message?.let {
            assertContains(
                invalidPatientMessage,
                it
            )
        }
        val exceptionSingleInsert =
            assertFailsWith<IllegalArgumentException> { service.createPatient(patient1) }
        exceptionSingleInsert.message?.let {
            assertContains(
                invalidPatientMessage,
                it
            )
        }
        val foundPatients = service.getAllPatients()
        assertEquals(0, foundPatients.size)
        file.deleteOnExit()
        file2.deleteOnExit()
    }

    @Test
    fun testInsertPatientsNameWithSpecialCharsSucceeds() {
        val service = PatientService()

        val file = File(path + "data1.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val name1 = "!testReco\$r&d123"
        val name2 = "tÄsÜRÖcxr?d2"
        val patient1 = Patient(
            name = name1,
            weight = Float.MIN_VALUE,
            pictures = listOf(file),
            comment = "ÄÖ!%comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = name2,
            weight = 12.5f,
            pictures = listOf(file, file2),
            comment = "comment2",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val resultIds = service.createMultiplePatients(listOf(patient1, patient2))
        assertEquals(2, resultIds.size)

        val foundPatients = service.getAllPatients()
        assertEquals(2, foundPatients.size)

        val foundPatient1 = service.getPatientById(patient1._id)
        assertNotNull(foundPatient1)
        foundPatient1?.let {
            assertEquals(name1, foundPatient1.name)
        }
        file.deleteOnExit()
        file2.deleteOnExit()
    }

    @Test
    fun testInsertPatientsNameWithSpecialCommentsSucceeds() {
        val service = PatientService()

        val file = File(path + "data1.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()

        val comment1 = "ÄÖ!%coasomemment"
        val comment2 = "co***\"\"\$1111<>ßÖÜÄöäü@mment2"
        val patient1 = Patient(
            name = "!testReco\$r&d123",
            weight = Float.MIN_VALUE,
            pictures = listOf(file),
            comment = comment1,
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "tÄsÜRÖcxr?d2",
            weight = 12.5f,
            pictures = listOf(file, file2),
            comment = comment2,
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val resultIds = service.createMultiplePatients(listOf(patient1, patient2))
        assertEquals(2, resultIds.size)
        assertTrue(resultIds.contains(patient1._id))

        val foundPatients = service.getAllPatients()
        assertEquals(2, foundPatients.size)

        val foundPatient1 = service.getPatientById(patient1._id)
        assertNotNull(foundPatient1)
        foundPatient1?.let {
            assertEquals(comment1, foundPatient1.comment)
        }
        val foundPatient2 = service.getPatientById(patient2._id)
        assertNotNull(foundPatient2)
        foundPatient2?.let {
            assertEquals(comment2, foundPatient2.comment)
        }
        file.deleteOnExit()
        file2.deleteOnExit()
    }

    @Test
    fun testInsertValidPatient() {
        val service = PatientService()
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val patient1 = Patient(
            name = "testRecord",
            weight = 23.3f,
            pictures = listOf(file, file2),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val resultId = service.createPatient(patient1)
        val foundPatients = service.getAllPatients()
        assertEquals(patient1._id, resultId)
        assertEquals(1, foundPatients.size)
        file.deleteOnExit()
        file2.deleteOnExit()
    }

    @Test
    fun testInsertMultiplePatients() {
        val service = PatientService()
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val file3 = File(path + "data3.jpg")
        file3.createNewFile()
        val file4 = File(path + "data4.jpg")
        file4.createNewFile()
        assertNotNull(service)
        val patient1 = Patient(
            name = "testRecord1",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "testRecord2",
            weight = 11.5f,
            pictures = listOf(file, file2),
            comment = "test",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient3 = Patient(
            name = "testRecord3",
            weight = 5.2f,
            pictures = listOf(file, file2, file3, file4),
            comment = "rec",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val result = service.createMultiplePatients(listOf(patient1, patient2, patient3))

        assertEquals(3, result.size)
        val foundPatients = service.getAllPatients()
        assertEquals(3, foundPatients.size)
        file.deleteOnExit()
        file2.deleteOnExit()
        file3.deleteOnExit()
        file4.deleteOnExit()
    }

    @Test
    fun testFindPatient() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val patient1 = Patient(
            name = "testRecord1",
            weight = 23.3f,
            pictures = listOf(file, file2),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createPatient(patient1)
        val foundPatient = service.getPatientById(patient1._id)
        assertNotNull(foundPatient)
        foundPatient?.let {
            assertEquals(patient1._id, foundPatient._id)
            assertEquals(patient1.pictures.first().name, foundPatient.pictures.first().name)
            assertEquals(patient1.name, foundPatient.name)
            assertEquals(patient1.comment, foundPatient.comment)
            kotlin.test.assertEquals(patient1.weight, foundPatient.weight)
        }
        file.deleteOnExit()
        file2.deleteOnExit()
    }

    @Test
    fun testFindInvalidPatientFails() {
        val service = PatientService()
        assertNotNull(service)
        val patient1 = Patient(
            name = "rec",
            weight = 23.3f,
            pictures = listOf(),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val foundPatient = service.getPatientById(patient1._id)
        assertNull(foundPatient)
    }

    @Test
    fun findPatientsByNameSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val patient1 = Patient(
            name = "patient",
            weight = 23.3f,
            pictures = listOf(),
            comment = "comment1",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "patient",
            weight = 23.3f,
            pictures = listOf(),
            comment = "comment2",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient3 = Patient(
            name = "patient",
            weight = 23.3f,
            pictures = listOf(),
            comment = "comment3",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createMultiplePatients(listOf(patient1, patient2, patient3))
        val result = service.getPatientByName(patient1.name)
        assertEquals(3, result.size)
        result.forEach { elem -> assertEquals(patient1.name, elem.name) }
    }

    @Test
    fun findPatientsByNameWithZeroElementsSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val patient1 = Patient(
            name = "patient",
            weight = 23.3f,
            pictures = listOf(),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val result = service.getPatientByName(patient1.name)
        assertEquals(0, result.size)
    }

    @Test
    fun findAllPatientsSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val patient1 = Patient(
            name = "testPatient1",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "testPatient2",
            weight = 23.3f,
            pictures = listOf(),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient3 = Patient(
            name = "testPatient3",
            weight = 23.3f,
            pictures = listOf(file, file2),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )

        val wasSuccessful = service.createMultiplePatients(listOf(patient1, patient2, patient3))
        assertNotNull(wasSuccessful)
        val result = service.getAllPatients()
        assertEquals(3, result.size)
        result.forEach { elem -> assertNotNull(elem) }
        result.forEach { elem -> assertNotNull(elem.pictures) }
        file.deleteOnExit()
        file2.deleteOnExit()
    }

    @Test
    fun findAllPatientsWithSizeZeroSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val result = service.getAllPatients()
        assertEquals(0, result.size)
    }

    @Test
    fun updatePatientSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()

        val patient1 = Patient(
            name = "patient",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createPatient(patient1)
        val size = service.getAllPatients()
        assertEquals(1, size.size)

        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val file3 = File(path + "data3.jpg")
        file3.createNewFile()

        patient1.name = "updatedName"
        patient1.comment = "updatedComment"
        patient1.pictures = listOf(file2, file3)
        service.updatePatient(patient1)

        val result = service.getPatientById(patient1._id)
        assertNotNull(result)
        result?.let {
            assertEquals(patient1.name, result.name)
            assertEquals(patient1.comment, result.comment)
            assertEquals(3, result.pictures.size)
        }
        file.deleteOnExit()
        file2.deleteOnExit()
        file3.deleteOnExit()
    }

    @Test
    fun updatePatientWithEmptyNameFails() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()

        val patient1 = Patient(
            name = "patient",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createPatient(patient1)
        val size = service.getAllPatients()
        assertEquals(1, size.size)

        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val file3 = File(path + "data3.jpg")
        file3.createNewFile()

        patient1.name = ""
        patient1.comment = "updatedComment"
        patient1.pictures = listOf(file2, file3)
        val exceptionSingleUpdate =
            assertFailsWith<IllegalArgumentException> { service.updatePatient(patient1) }
        exceptionSingleUpdate.message?.let {
            assertContains(
                invalidPatientMessage,
                it
            )
        }
        val result = service.getPatientById(patient1._id)
        assertNotNull(result)
        result?.let {
            assertNotEquals(patient1.name, result.name)
            assertNotEquals(patient1.comment, result.comment)
            assertNotEquals(3, result.pictures.size)
            assertEquals(1, result.pictures.size)
        }
        file.deleteOnExit()
        file2.deleteOnExit()
        file3.deleteOnExit()
    }

    @Test
    fun updatePatientWithInvalidFloatFails() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()

        val patient1 = Patient(
            name = "patient",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createPatient(patient1)
        val size = service.getAllPatients()
        assertEquals(1, size.size)

        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val file3 = File(path + "data3.jpg")
        file3.createNewFile()

        patient1.name = "updatedName"
        patient1.comment = "updatedComment"
        patient1.pictures = listOf(file2, file3)
        patient1.weight = Float.NEGATIVE_INFINITY
        val exceptionSingleUpdate =
            assertFailsWith<IllegalArgumentException> { service.updatePatient(patient1) }
        exceptionSingleUpdate.message?.let {
            assertContains(
                invalidPatientMessage,
                it
            )
        }

        val result = service.getPatientById(patient1._id)
        assertNotNull(result)
        result?.let {
            assertNotEquals(patient1.name, result.name)
            assertNotEquals(patient1.comment, result.comment)
            assertNotEquals(3, result.pictures.size)
            assertNotEquals(patient1.weight, result.weight)
        }
        file.deleteOnExit()
        file2.deleteOnExit()
        file3.deleteOnExit()
    }

    @Test
    fun updatePatientWithSpecialCharNameSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()

        val patient1 = Patient(
            name = "patient",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createPatient(patient1)
        val size = service.getAllPatients()
        assertEquals(1, size.size)

        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val file3 = File(path + "data3.jpg")
        file3.createNewFile()

        val updateName = "ÄÜÖÄÜÖÄÜ.asdg!%%%"
        patient1.name = updateName
        patient1.comment = "updatedComment"
        patient1.pictures = listOf(file2, file3)
        patient1.weight = Float.MIN_VALUE
        val updateResult = service.updatePatient(patient1)
        assertEquals(true, updateResult)

        val result = service.getPatientById(patient1._id)
        assertNotNull(result)
        result?.let {
            assertEquals(updateName, result.name)
            assertEquals(patient1.comment, result.comment)
            assertEquals(3, result.pictures.size)
            assertEquals(patient1.weight, result.weight)
        }
        file.deleteOnExit()
        file2.deleteOnExit()
        file3.deleteOnExit()
    }

    @Test
    fun updatePatientWithSpecialCharCommentsSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()

        val patient1 = Patient(
            name = "patient",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createPatient(patient1)
        val size = service.getAllPatients()
        assertEquals(1, size.size)

        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val file3 = File(path + "data3.jpg")
        file3.createNewFile()

        val updateComment = "ÄÜÖÄÜÖÄÜ.asdg!%%%comment!!!&&&&&&&@@@@@@@@@@@@@||||||||||??????"
        patient1.name = "updateName"
        patient1.comment = "updatedComment"
        patient1.pictures = listOf(file2, file3)
        patient1.weight = Float.MIN_VALUE
        val updateResult = service.updatePatient(patient1)
        assertEquals(true, updateResult)

        val result = service.getPatientById(patient1._id)
        assertNotNull(result)
        result?.let {
            assertEquals(patient1.name, result.name)
            assertNotEquals(updateComment, result.comment)
            assertEquals(3, result.pictures.size)
            assertEquals(patient1.weight, result.weight)
        }
        file.deleteOnExit()
        file2.deleteOnExit()
        file3.deleteOnExit()
    }

    @Test
    fun updatePatientWithInvalidFilePathSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()

        val patient1 = Patient(
            name = "patient",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createPatient(patient1)
        val size = service.getAllPatients()
        assertEquals(1, size.size)

        val file2 = File(path + "data2äääöööppppp!!!!!!!!.jpg")
        file2.createNewFile()
        val file3 = File(path + "data3.jpg")
        file3.createNewFile()

        patient1.name = "updateName"
        patient1.comment = "updatedComment"
        patient1.pictures = listOf(file2, file3)
        patient1.weight = Float.MIN_VALUE
        val updateResult = service.updatePatient(patient1)
        assertEquals(true, updateResult)
        val result = service.getPatientById(patient1._id)
        assertNotNull(result)
        result?.let {
            assertEquals(patient1.name, result.name)
            assertEquals(patient1.comment, result.comment)
            assertEquals(3, result.pictures.size)
            assertEquals(patient1.weight, result.weight)
        }
        file.deleteOnExit()
        file2.deleteOnExit()
        file3.deleteOnExit()
    }


    @Test
    fun updateManyPatientsSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()

        val patient1 = Patient(
            name = "patient1",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "patient2",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient3 = Patient(
            name = "patient3",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createMultiplePatients(listOf(patient1, patient2, patient3))
        val size = service.getAllPatients()
        assertEquals(3, size.size)

        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val file3 = File(path + "data3.jpg")
        file3.createNewFile()

        patient1.name = "updatedName"
        patient1.comment = "newComment"
        patient1.pictures = listOf(file2, file3)
        patient2.name = "comment"
        patient2.comment = "com"
        patient2.pictures = listOf(file3)
        patient3.name = "testName"
        patient3.comment = "thirdComment"
        patient3.pictures = listOf()
        val listToUpdate = listOf(patient1, patient2, patient3)
        val result = service.updateMultiplePatients(listToUpdate)
        assertEquals(true, result)
        val patientFound1 = service.getPatientById(patient1._id)
        val patientFound2 = service.getPatientById(patient2._id)
        val patientFound3 = service.getPatientById(patient3._id)
        assertNotNull(patientFound2)
        assertNotNull(patientFound3)
        assertEquals("comment", patient2.name)
        assertEquals("thirdComment", patient3.comment)
        assertEquals(0, patient3.pictures.size)
        patientFound1?.let { assertEquals(3, patientFound1.pictures.size) }
        file.deleteOnExit()
        file2.deleteOnExit()
        file3.deleteOnExit()
    }

    @Test
    fun updateManyPatientsWithInvalidParametersFails() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()

        val patient1 = Patient(
            name = "patient1",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "patient2",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient3 = Patient(
            name = "patient3",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createMultiplePatients(listOf(patient1, patient2, patient3))
        val size = service.getAllPatients()
        assertEquals(3, size.size)

        val file2 = File(path + "data2äääööööüüü.jpg")
        file2.createNewFile()
        val file3 = File(path + "data3$    $$$$!!!!.jpg")
        file3.createNewFile()

        patient1.name = "updatedName"
        patient1.weight = Float.NEGATIVE_INFINITY
        patient1.comment = "newComment"
        patient1.pictures = listOf(file2, file3)
        patient2.name = "comment"
        patient2.comment = "com"
        patient2.weight = Float.POSITIVE_INFINITY
        patient2.pictures = listOf(file3)
        patient3.name = "testName"
        patient3.comment = "thirdComment"
        patient3.weight = Float.MAX_VALUE
        patient3.pictures = listOf()
        val listToUpdate = listOf(patient1, patient2, patient3)
        val exceptionSingleUpdate =
            assertFailsWith<IllegalArgumentException> { service.updateMultiplePatients(listToUpdate) }
        exceptionSingleUpdate.message?.let {
            assertContains(
                invalidPatientMessage,
                it
            )
        }
        val patientFound1 = service.getPatientById(patient1._id)
        val patientFound2 = service.getPatientById(patient2._id)
        val patientFound3 = service.getPatientById(patient3._id)
        assertNotNull(patientFound2)
        assertNotNull(patientFound3)
        patientFound1?.let { assertNotEquals(patient1.name, patientFound1.name) }
        patientFound2?.let { assertNotEquals(patient2.comment, patientFound2.comment) }
        patientFound1?.let { assertNotEquals(3, patientFound1.pictures.size) }
        patientFound3?.let { assertNotEquals(patient3.weight, patientFound3.weight) }
        file.deleteOnExit()
        file2.deleteOnExit()
        file3.deleteOnExit()
    }

    @Test
    fun deletePatientSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()

        val patient = Patient(
            name = "patient",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createPatient(patient)
        val result = service.deletePatientById(patient._id)
        assertEquals(true, result)
        val foundPatient = service.getPatientById(patient._id)
        assertNull(foundPatient)
        file.deleteOnExit()
    }

    @Test
    fun deletePatientWithInvalidIdFails() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()
        val patient = Patient(
            name = "patient",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val id = service.createPatient(patient)
        assertNotEquals("", id)
        val exception =
            assertFailsWith<IllegalArgumentException> { service.deletePatientById("p") }
        exception.message?.let {
            assertContains(
                "the requested id does not have a valid format",
                it
            )
        }
        val foundPatient = service.getPatientById(id)
        assertNotNull(foundPatient)
        file.deleteOnExit()
    }

    @Test
    fun deleteManyPatientsSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()
        val patient1 = Patient(
            name = "patient1",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "patient2",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient3 = Patient(
            name = "patient3",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createMultiplePatients(listOf(patient1, patient2, patient3))
        service.deleteMultiplePatientsByIds(listOf(patient1._id, patient2._id, patient3._id))
        val foundPatients = service.getAllPatients()
        assertEquals(0, foundPatients.size)
        file.deleteOnExit()
    }

    @Test
    fun deleteManyPatientsWithInvalidIdFails() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()
        val patient1 = Patient(
            name = "patient1",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "patient2",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient3 = Patient(
            name = "patient3",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val resultInsertedIds = service.createMultiplePatients(listOf(patient1, patient2, patient3))
        assertEquals(3, resultInsertedIds.size)
        assertTrue(resultInsertedIds.contains(patient1._id))
        val allInsertedPatients = service.getAllPatients()
        assertEquals(3, allInsertedPatients.size)
        val exceptionMultipleDeletes = assertFailsWith<IllegalArgumentException> {
            service.deleteMultiplePatientsByIds(
                listOf(
                    "abc",
                    "123",
                    "öäü ÖÄÜ"
                )
            )
        }
        exceptionMultipleDeletes.message?.let {
            assertContains(
                "the requested id does not have a valid format",
                it
            )
        }
        val foundPatients = service.getAllPatients()
        assertEquals(3, foundPatients.size)
        file.deleteOnExit()
    }

    @Test
    fun deleteAllPatientsSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val patient1 = Patient(
            name = "patient1",
            weight = 23.3f,
            pictures = listOf(),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient2 = Patient(
            name = "patient2",
            weight = 23.3f,
            pictures = listOf(),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        val patient3 = Patient(
            name = "patient3",
            weight = 23.3f,
            pictures = listOf(),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
        )
        service.createMultiplePatients(listOf(patient1, patient2, patient3))
        service.deleteAllPatients()
        val result = service.getAllPatients()
        assertEquals(0, result.size)
    }

    @Test
    fun testInsertValidPatientWithBodyMap() {
        val service = PatientService()
        val file = File(path + "data1.jpg")
        file.createNewFile()
        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val fieldValues = listOf(FieldValue("1", 2, 1), FieldValue("2", 0, 2))
        val patient1 = Patient(
            name = "patient2",
            weight = 23.3f,
            pictures = listOf(file, file2),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
            bodyMaps = listOf(
                BodyMap(
                    "1", fieldValues
                )
            )
        )
        val resultId = service.createPatient(patient1)
        val foundPatients = service.getAllPatients()
        assertEquals(patient1._id, resultId)
        assertEquals(1, foundPatients.size)
        assertEquals(1, foundPatients.first().bodyMaps.size)
        assertEquals(2, foundPatients.first().bodyMaps.first().fieldValues.size)
        file.deleteOnExit()
        file2.deleteOnExit()
    }

    @Test
    fun updatePatientWithBodyMapSucceeds() {
        val service = PatientService()
        assertNotNull(service)
        val file = File(path + "data.jpg")
        file.createNewFile()

        val patient1 = Patient(
            name = "patient2",
            weight = 23.3f,
            pictures = listOf(file),
            comment = "comment",
            sex = Sex.FEMALE,
            birthDate = "LocalDate.now()",
            species = "Dog",
            ownerName = "Max Mustermann",
            ownerAddress = "Hauptstraße 1",
            ownerPhoneNumber = "123456789",
            bodyMaps = listOf(
            )
        )
        service.createPatient(patient1)
        val size = service.getAllPatients()
        assertEquals(1, size.size)

        val file2 = File(path + "data2.jpg")
        file2.createNewFile()
        val file3 = File(path + "data3.jpg")
        file3.createNewFile()

        val updateComment = "ÄÜÖÄÜÖÄÜ.asdg!%%%comment!!!&&&&&&&@@@@@@@@@@@@@||||||||||??????"
        patient1.name = "updateName"
        patient1.comment = "updatedComment"
        patient1.pictures = listOf(file2, file3)
        patient1.weight = Float.MIN_VALUE

        val fieldValues = listOf(FieldValue("1", 2, 1), FieldValue("2", 0, 2))
        patient1.bodyMaps = listOf(
            BodyMap(
                "1", fieldValues
            )
        )
        val updateResult = service.updatePatient(patient1)
        assertEquals(true, updateResult)

        val result = service.getPatientById(patient1._id)
        assertNotNull(result)
        result?.let {
            assertEquals(patient1.name, result.name)
            assertNotEquals(updateComment, result.comment)
            assertEquals(3, result.pictures.size)
            assertEquals(patient1.weight, result.weight)
            assertEquals(patient1.bodyMaps.size, result.bodyMaps.size)
        }

        val bodyMapList = mutableListOf<BodyMap>()
        bodyMapList.add(
            BodyMap(
                "2", fieldValues
            )
        )
        bodyMapList.add(
            BodyMap(
                "5", fieldValues
            )
        )
        result?.bodyMaps = bodyMapList
        val nextUpdateResult = service.updatePatient(result!!)

        assertEquals(true, nextUpdateResult)
        val result2 = service.getPatientById(patient1._id)
        assertNotNull(result2)
        result2?.let {
            assertEquals(result.bodyMaps.size, result2.bodyMaps.size)
            assertTrue(
                result2.bodyMaps.contains(
                    BodyMap(
                        "5", fieldValues
                    )
                )
            )
        }
        file.deleteOnExit()
        file2.deleteOnExit()
        file3.deleteOnExit()
    }
}
