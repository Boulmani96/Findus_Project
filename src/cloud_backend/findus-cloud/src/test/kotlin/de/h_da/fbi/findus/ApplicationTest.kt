package de.h_da.fbi.findus

import com.google.gson.Gson
import de.h_da.fbi.findus.database.di.cloudDatabaseModule
import de.h_da.fbi.findus.database.models.MedicalFinding
import de.h_da.fbi.findus.database.services.CloudService
import de.h_da.fbi.findus.entities.DiagnoseInput
import de.h_da.fbi.findus.entities.MedicalExaminationAPI
import de.h_da.fbi.findus.entities.MedicalExaminationSuccessful
import de.h_da.fbi.findus.entities.StatusResponse
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.datetime.*
import org.junit.After
import org.koin.core.context.GlobalContext
import org.koin.test.KoinTest
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class ApplicationTest : KoinTest {

    private val timeOffset = "UTC+1"

    @After
    fun teardown() {
        GlobalContext.startKoin { modules(cloudDatabaseModule) }
        CloudService().deleteAllCheckUps()
        GlobalContext.stopKoin()
    }

    @Test
    fun testGetDocumentation() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/openapi.json") {
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testNotFound() {
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/api/doesNotExist") {
            }.apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }


    @Test
    fun testUploadData() {
        val current = Clock.System.now().toLocalDateTime(TimeZone.of(timeOffset))
        val birthdate = LocalDate(2010, 1, 8).atStartOfDayIn(TimeZone.of(timeOffset))
            .toLocalDateTime(TimeZone.of(timeOffset))
        //valid request succeeds
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/api/examination/upload") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        MedicalExaminationAPI(
                            patientIdentifier = "someDog",
                            birthDate = birthdate,
                            age = 10,
                            animalBreed = "Beagle",
                            examinationDate = current,
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
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
        //test invalid patientIdentifier fails
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/api/examination/upload") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        MedicalExaminationAPI(
                            patientIdentifier = "",
                            birthDate = birthdate,
                            age = 10,
                            animalBreed = "Beagle",
                            examinationDate = current,
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
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
        //test invalid age fails
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/api/examination/upload") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        MedicalExaminationAPI(
                            patientIdentifier = "testname",
                            birthDate = birthdate,
                            age = -200,
                            animalBreed = "Beagle",
                            examinationDate = current,
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
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
        //test non-required null values
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/api/examination/upload") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        MedicalExaminationAPI(
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
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
        //test invalid birthday
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/api/examination/upload") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        MedicalExaminationAPI(
                            patientIdentifier = "testname",
                            birthDate = LocalDate(1974, 12, 31).atStartOfDayIn(
                                TimeZone.of(
                                    timeOffset
                                )
                            ).toLocalDateTime(TimeZone.of(timeOffset)),
                            age = 20,
                            animalBreed = "Beagle",
                            examinationDate = current,
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
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
        //test invalid checkup date before birth
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/api/examination/upload") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        MedicalExaminationAPI(
                            patientIdentifier = "testname",
                            birthDate = LocalDate(2000, 12, 31).atStartOfDayIn(
                                TimeZone.of(
                                    timeOffset
                                )
                            ).toLocalDateTime(TimeZone.of(timeOffset)),
                            age = 20,
                            animalBreed = "Beagle",
                            examinationDate = LocalDate(1999, 1, 1).atStartOfDayIn(
                                TimeZone.of(
                                    timeOffset
                                )
                            ).toLocalDateTime(TimeZone.of(timeOffset)),
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
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
        //test valid checkup date in future
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/api/examination/upload") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        MedicalExaminationAPI(
                            patientIdentifier = "testname",
                            birthDate = LocalDate(2000, 12, 31).atStartOfDayIn(
                                TimeZone.of(
                                    timeOffset
                                )
                            ).toLocalDateTime(TimeZone.of(timeOffset)),
                            age = 20,
                            animalBreed = "Beagle",
                            examinationDate = LocalDate(2100, 1, 1).atStartOfDayIn(
                                TimeZone.of(
                                    timeOffset
                                )
                            ).toLocalDateTime(TimeZone.of(timeOffset)),
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
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }


    @Test
    fun testDownloadExportData() {
        val current = Clock.System.now().toLocalDateTime(TimeZone.of(timeOffset))
        val birthdate = LocalDate(2010, 1, 8).atStartOfDayIn(TimeZone.of(timeOffset))
            .toLocalDateTime(TimeZone.of(timeOffset))
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/api/examination/upload") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        MedicalExaminationAPI(
                            patientIdentifier = "myDog",
                            birthDate = birthdate,
                            age = 10,
                            animalBreed = "Beagle",
                            examinationDate = current,
                            existingDisease = listOf(
                                "asthma",
                                "broken leg",
                                "some",
                                "disease",
                                "to",
                                "worry",
                                "about"
                            ),
                            bodyMarker = listOf(
                                MedicalFinding.PENDING,
                                null,
                                null,
                                MedicalFinding.RED,
                                null,
                                MedicalFinding.PENDING
                            ),
                            muscleCircumference = listOf(23.1f, 25.3f, 21.0f, 11.0f),
                            movement = listOf("move1", "move2", "move3", "move4"),
                            bodyPosture = null,
                            breathMovement = null,
                            symptoms = listOf(
                                "some",
                                "symptoms",
                                "to",
                                "have",
                                "a",
                                "close",
                                "look"
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                assertNotEquals(
                    "",
                    Gson().fromJson(response.content, MedicalExaminationSuccessful::class.java).id
                )
            }
        }

        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/api/examination/download") {
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val outputFile = File("exportData.csv")
                assertTrue(outputFile.length() > 0)
                outputFile.deleteOnExit()
            }
        }

    }

    @Test
    fun testUploadImage() {
        val current = Clock.System.now().toLocalDateTime(TimeZone.of(timeOffset))
        val birthdate = LocalDate(2010, 1, 8).atStartOfDayIn(TimeZone.of(timeOffset))
            .toLocalDateTime(TimeZone.of(timeOffset))
        var id: String? = null
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/api/examination/upload") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        MedicalExaminationAPI(
                            patientIdentifier = "someDog",
                            birthDate = birthdate,
                            age = 10,
                            animalBreed = "Beagle",
                            examinationDate = current,
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
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                id = Gson().fromJson(response.content, MedicalExaminationSuccessful::class.java).id
            }
        }
        withTestApplication({ module() }) {
            handleRequest(
                HttpMethod.Post,
                "/api/examination/upload/picture/$id/name/testImage.jpg"
            ) {
                val file = File("./src/test/resources/ktor.jpg")
                val fileBytes = file.readBytes()

                addHeader(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
                setBody(fileBytes)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
        withTestApplication({ module() }) {
            handleRequest(
                HttpMethod.Post,
                "/api/examination/upload/picture/$id/name/testImage1.jpg"
            ) {
                val file = File("./src/test/resources/ktor.jpg")
                val fileBytes = file.readBytes()
                addHeader(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
                setBody(fileBytes)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
        withTestApplication({ module() }) {
            handleRequest(
                HttpMethod.Post,
                "/api/examination/upload/picture/$id/name/testImage1.jpg"
            ) {
                val file = File("./src/test/resources/ktor.jpg")
                val fileBytes = file.readBytes()
                addHeader(HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
                setBody(fileBytes)
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testDiagnose() {
        //valid diagnose request id and description
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/api/examination/diagnose") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())

                setBody(
                    Gson().toJson(
                        DiagnoseInput("1234", "has a flu")
                    )
                )
            }.apply {
                assertNotEquals(
                    "",
                    Gson().fromJson(response.content, StatusResponse::class.java).status
                )
                assertEquals(
                    "your therapy plan",
                    Gson().fromJson(response.content, StatusResponse::class.java).status
                )
            }
        }

        //invalid: no id but description
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Post, "/api/examination/diagnose") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        DiagnoseInput("", "has a flu")
                    )
                )
            }
                .apply {
                    assertNotEquals(
                        "",
                        Gson().fromJson(response.content, StatusResponse::class.java).status
                    )
                    assertEquals(
                        "Malformed examination or invalid!",
                        Gson().fromJson(response.content, StatusResponse::class.java).status
                    )
                }
        }
    }
}
