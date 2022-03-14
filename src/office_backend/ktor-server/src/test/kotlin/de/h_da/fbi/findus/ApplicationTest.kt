package de.h_da.fbi.findus

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.h_da.fbi.findus.auth.JwtConfig
import de.h_da.fbi.findus.auth.JwtUser
import de.h_da.fbi.findus.database.models.Sex
import de.h_da.fbi.findus.database.models.Status
import de.h_da.fbi.findus.database.models.StatusChanged
import de.h_da.fbi.findus.database.models.Type
import de.h_da.fbi.findus.entities.*
import de.h_da.fbi.findus.routes.patientHasNoPictures
import de.h_da.fbi.findus.routes.patientHasOnlyNPictures
import io.ktor.http.*
import io.ktor.server.testing.*
import org.junit.After
import org.koin.core.context.GlobalContext
import org.koin.test.KoinTest
import java.io.File
import kotlin.test.*

class ApplicationTest : KoinTest {

    @After
    fun teardown() {
        GlobalContext.stopKoin()
    }

    @Test
    fun testLogin() {
        var token: String? = null

        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/login") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(LoginRequest("Admin", "Admin")))
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val loginResponse: LoginResponse =
                    Gson().fromJson(response.content, LoginResponse::class.java)
                assertEquals(loginResponse.ok, true)
                assertNotNull(loginResponse.token)
                assertNotNull(loginResponse.message)

                token = loginResponse.token
            }
        }

        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/me") {
                addHeader("Authorization", "Bearer $token")
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val user: JwtUser = Gson().fromJson(response.content, JwtUser::class.java)

                assertEquals(user.id, 1)
                assertEquals(user.name, "Admin")
            }
        }

    }

    @Test
    fun testGetDocumentation() {
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/openapi.json") {
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    @Test
    fun testNotFound() {
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/doesNotExist") {
            }.apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }

    @Test
    fun testAuthenticationFailed() {
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient") {
            }.apply {
                assertEquals(HttpStatusCode.Unauthorized, response.status())
            }
        }
    }

    @Test
    fun testGetAllPatients() {
        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient1",
                            150.0f,
                            "This is just a simple test 1",
                            Sex.MALE,
                            "kotlinx.datetime.LocalDate(2020, 2, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient2",
                            140.0f,
                            "This is just a simple test 2",
                            Sex.MALE,
                            "kotlinx.datetime.LocalDate(2020, 2, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }

        // check if created
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())

                val itemType = object : TypeToken<List<ApiPatient>>() {}.type
                val apiPatientList: List<ApiPatient> = Gson().fromJson(response.content, itemType)

                assertTrue(apiPatientList.size >= 2)
            }
        }

    }

    @Test
    fun testGetPatient() {
        var id: String? = null

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient3",
                            130.0f,
                            "This is just a simple test 3",
                            Sex.MALE,
                            "kotlinx.datetime.LocalDate(2020, 2, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: PatientCreateSuccessful =
                    Gson().fromJson(response.content, PatientCreateSuccessful::class.java)
                id = status.id
            }
        }

        // check if created
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("Patient3", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("This is just a simple test 3", apiPatient.comments)
                assertEquals(130.0f, apiPatient.weight)
                assertEquals(Sex.MALE, apiPatient.sex)
                assertEquals(
                    "kotlinx.datetime.LocalDate(2020, 2, 21)", apiPatient.birthDate
                )
                assertEquals("Dog", apiPatient.species)
                assertEquals("Max Mustermann", apiPatient.ownerName)
                assertEquals("Hauptstraße 1", apiPatient.ownerAddress)
                assertEquals("123456789", apiPatient.ownerPhoneNumber)
            }
        }

    }

    @Test
    fun testCreatePatient() {
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient4",
                            130.0f,
                            "This is just a simple test 4",
                            Sex.MALE,
                            "kotlinx.datetime.LocalDate(2020, 2, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
            }
        }
    }

    @Test
    fun testDeletePatient() {
        var id: String? = null

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient5",
                            150.0f,
                            "This is just a simple test 5",
                            Sex.MALE,
                            " kotlinx.datetime.LocalDate(2020, 2, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: PatientCreateSuccessful =
                    Gson().fromJson(response.content, PatientCreateSuccessful::class.java)
                id = status.id
            }
        }

        // check if created
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("Patient5", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("This is just a simple test 5", apiPatient.comments)
                assertEquals(150.0f, apiPatient.weight)
                assertEquals(Sex.MALE, apiPatient.sex)
                assertEquals("Dog", apiPatient.species)
                assertEquals("Max Mustermann", apiPatient.ownerName)
                assertEquals("Hauptstraße 1", apiPatient.ownerAddress)
                assertEquals("123456789", apiPatient.ownerPhoneNumber)
            }
        }

        // delete patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Delete, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }

        // check if deleted
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }

    }

    @Test
    fun testPatchPatient() {
        var id: String? = null

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient6",
                            160.0f,
                            "This is just a simple test 6",
                            Sex.MALE,
                            "kotlinx.datetime.LocalDate(2020, 2, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: PatientCreateSuccessful =
                    Gson().fromJson(response.content, PatientCreateSuccessful::class.java)
                id = status.id
            }
        }

        // check if created
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("Patient6", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("This is just a simple test 6", apiPatient.comments)
                assertEquals(160.0f, apiPatient.weight)
                assertEquals(Sex.MALE, apiPatient.sex)
                assertEquals("Dog", apiPatient.species)
                assertEquals("Max Mustermann", apiPatient.ownerName)
                assertEquals("Hauptstraße 1", apiPatient.ownerAddress)
                assertEquals("123456789", apiPatient.ownerPhoneNumber)
            }
        }

        // patch all attributes patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Patch, "/api/patient/$id") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientUpdate(
                            "New Patient Name",
                            444.44f,
                            "Is it changed?",
                            Sex.FEMALE,
                            "kotlinx.datetime.LocalDate(2020, 2, 21)",
                            "New Dog",
                            "New Max Mustermann",
                            "New Hauptstraße 1",
                            "New 123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }

        // check if patched
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("New Patient Name", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("Is it changed?", apiPatient.comments)
                assertEquals(444.44f, apiPatient.weight)
                assertEquals(Sex.FEMALE, apiPatient.sex)
                assertEquals("New Dog", apiPatient.species)
                assertEquals("New Max Mustermann", apiPatient.ownerName)
                assertEquals("New Hauptstraße 1", apiPatient.ownerAddress)
                assertEquals("New 123456789", apiPatient.ownerPhoneNumber)
            }
        }

        // patch not all attributes patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Patch, "/api/patient/$id") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientUpdate(
                            "New New Patient Name",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }

        // check if patched
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("New New Patient Name", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("Is it changed?", apiPatient.comments)
                assertEquals(444.44f, apiPatient.weight)
                assertEquals(Sex.FEMALE, apiPatient.sex)
                assertEquals(
                    "kotlinx.datetime.LocalDate(2020, 2, 21)", apiPatient.birthDate
                )
                assertEquals("New Dog", apiPatient.species)
                assertEquals("New Max Mustermann", apiPatient.ownerName)
                assertEquals("New Hauptstraße 1", apiPatient.ownerAddress)
                assertEquals("New 123456789", apiPatient.ownerPhoneNumber)
            }
        }

    }

    @Test
    fun testImageUploadAndDownload() {
        var id: String? = null

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient7",
                            170.0f,
                            "This is just a simple test 7",
                            Sex.MALE,
                            "kotlinx.datetime.LocalDate(2020, 2, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: PatientCreateSuccessful =
                    Gson().fromJson(response.content, PatientCreateSuccessful::class.java)
                id = status.id
            }
        }

        // check if created
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("Patient7", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("This is just a simple test 7", apiPatient.comments)
                assertEquals(170.0f, apiPatient.weight)
                assertEquals(Sex.MALE, apiPatient.sex)
                assertEquals("Dog", apiPatient.species)
                assertEquals("Max Mustermann", apiPatient.ownerName)
                assertEquals("Hauptstraße 1", apiPatient.ownerAddress)
                assertEquals("123456789", apiPatient.ownerPhoneNumber)
            }
        }

        // download picture fail
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id/picture/0") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.NotFound, response.status())

                val statusResponse: StatusResponse =
                    Gson().fromJson(response.content, StatusResponse::class.java)

                assertEquals(patientHasNoPictures(id), statusResponse.status)
            }
        }

        // load picture
        val file = File("./src/test/resources/ktor.png")
        val fileBytes = file.readBytes()

        // upload picture
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient/$id/picture") {
                addJwtHeader()

                addHeader(
                    HttpHeaders.ContentType,
                    ContentType.Image.PNG.toString()
                )

                setBody(fileBytes)

            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }

        }

        // download picture fail
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id/picture/1") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.NotFound, response.status())

                val statusResponse: StatusResponse =
                    Gson().fromJson(response.content, StatusResponse::class.java)

                assertEquals(patientHasOnlyNPictures(id, 1), statusResponse.status)
            }
        }

        // download picture
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id/picture/0") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertContentEquals(fileBytes, response.byteContent)
            }
        }

    }

    @Test
    fun testAddAnamnese() {
        var id: String? = null
        var anamneseId: String? = null

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient8",
                            180.0f,
                            "This is just a simple test 8",
                            Sex.MALE,
                            "kotlinx.datetime.LocalDate(2021, 2, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: PatientCreateSuccessful =
                    Gson().fromJson(response.content, PatientCreateSuccessful::class.java)
                id = status.id
            }
        }

        // check if created
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("Patient8", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("This is just a simple test 8", apiPatient.comments)
                assertEquals(180.0f, apiPatient.weight)
                assertEquals(Sex.MALE, apiPatient.sex)
                assertEquals(
                    "kotlinx.datetime.LocalDate(2021, 2, 21)", apiPatient.birthDate
                )
                assertEquals("Dog", apiPatient.species)
                assertEquals("Max Mustermann", apiPatient.ownerName)
                assertEquals("Hauptstraße 1", apiPatient.ownerAddress)
                assertEquals("123456789", apiPatient.ownerPhoneNumber)
            }
        }

        // add anamnese
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient/$id/anamnese") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        ApiAnamneseCreateUpdate(
                            "kotlinx.datetime.LocalDate(2021, 2, 21)",
                            listOf(
                                ApiOrgan(
                                    name = "lungs",
                                    status = Status.UNKNOWN,
                                    statusChanged = StatusChanged.ANECHOGEN,
                                    comment = "",
                                    type = Type.RED
                                )
                            ),
                            listOf(
                                ApiMuscle(
                                    name = "muscle",
                                    status = Status.CHANGED,
                                    comment = "this is a simple comment",
                                    type = Type.GREEN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = true
                                )
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val anamneseCreateSuccessful: AnamneseCreateSuccessful =
                    Gson().fromJson(response.content, AnamneseCreateSuccessful::class.java)
                anamneseId = anamneseCreateSuccessful.id
            }
        }

        // check if added
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("Patient8", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("This is just a simple test 8", apiPatient.comments)
                assertEquals(180.0f, apiPatient.weight)
                assertEquals(Sex.MALE, apiPatient.sex)
                assertEquals(
                    "kotlinx.datetime.LocalDate(2021, 2, 21)", apiPatient.birthDate
                )
                assertEquals("Dog", apiPatient.species)
                assertEquals("Max Mustermann", apiPatient.ownerName)
                assertEquals("Hauptstraße 1", apiPatient.ownerAddress)
                assertEquals("123456789", apiPatient.ownerPhoneNumber)
                assertEquals("muscle", apiPatient.anamnese[0].muscles[0].name)
                assertEquals(1, apiPatient.anamnese.size)
                assertEquals(anamneseId, apiPatient.anamnese[0].id)
                assertEquals(
                    "kotlinx.datetime.LocalDate(2021, 2, 21)", apiPatient.anamnese[0].date
                )
                assertEquals(Status.UNKNOWN, apiPatient.anamnese[0].organs[0].status)
                assertEquals(
                    StatusChanged.ANECHOGEN,
                    apiPatient.anamnese[0].organs[0].statusChanged
                )
                assertEquals("this is a simple comment", apiPatient.anamnese[0].muscles[0].comment)
                assertEquals(Type.GREEN, apiPatient.anamnese[0].muscles[0].type)
            }
        }

    }

    @Test
    fun testGetAnamnese() {
        var id: String? = null
        var anamneseId: String? = null

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient8",
                            180.0f,
                            "This is just a simple test 8",
                            Sex.MALE,
                            "kotlinx.datetime.LocalDate(2021, 2, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: PatientCreateSuccessful =
                    Gson().fromJson(response.content, PatientCreateSuccessful::class.java)
                id = status.id
            }
        }

        // check if created
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("Patient8", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("This is just a simple test 8", apiPatient.comments)
                assertEquals(180.0f, apiPatient.weight)
                assertEquals(Sex.MALE, apiPatient.sex)
                assertEquals("Dog", apiPatient.species)
                assertEquals("Max Mustermann", apiPatient.ownerName)
                assertEquals("Hauptstraße 1", apiPatient.ownerAddress)
                assertEquals("123456789", apiPatient.ownerPhoneNumber)
            }
        }

        // add anamnese
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient/$id/anamnese") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        ApiAnamneseCreateUpdate(
                            "kotlinx.datetime.LocalDate(2021, 2, 21)",
                            listOf(
                                ApiOrgan(
                                    name = "lungs",
                                    status = Status.UNCHANGED,
                                    statusChanged = StatusChanged.HYPOECHOGEN,
                                    comment = "",
                                    type = Type.UNKNOWN
                                )
                            ),
                            listOf(
                                ApiMuscle(
                                    name = "muscle1",
                                    status = Status.CHANGED,
                                    comment = "this is a simple muscle comment",
                                    type = Type.YELLOW,
                                    checkedStateInjury = true,
                                    checkedStateSwelling = false,
                                    checkedStateHematoma = true
                                )
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val anamneseCreateSuccessful: AnamneseCreateSuccessful =
                    Gson().fromJson(response.content, AnamneseCreateSuccessful::class.java)
                anamneseId = anamneseCreateSuccessful.id
            }
        }

        // check if added
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id/anamnese/$anamneseId") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiAnamnese: ApiAnamnese =
                    Gson().fromJson(response.content, ApiAnamnese::class.java)
                assertEquals(anamneseId, apiAnamnese.id)
                assertEquals(
                    "kotlinx.datetime.LocalDate(2021, 2, 21)", apiAnamnese.date
                )
                assertEquals(Status.CHANGED, apiAnamnese.muscles[0].status)
                assertEquals(
                    StatusChanged.HYPOECHOGEN,
                    apiAnamnese.organs[0].statusChanged
                )
                assertEquals("this is a simple muscle comment", apiAnamnese.muscles[0].comment)
                assertEquals(Type.YELLOW, apiAnamnese.muscles[0].type)
            }
        }
    }

    @Test
    fun testUpdateAnamnese() {
        var id: String? = null
        var anamneseId: String? = null

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient8",
                            180.0f,
                            "This is just a simple test 8",
                            Sex.MALE,
                            "kotlinx.datetime.LocalDate(2021, 12, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: PatientCreateSuccessful =
                    Gson().fromJson(response.content, PatientCreateSuccessful::class.java)
                id = status.id
            }
        }

        // check if created
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("Patient8", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("This is just a simple test 8", apiPatient.comments)
                assertEquals(180.0f, apiPatient.weight)
                assertEquals(Sex.MALE, apiPatient.sex)
                assertEquals(
                    "kotlinx.datetime.LocalDate(2021, 12, 21)", apiPatient.birthDate
                )
                assertEquals("Dog", apiPatient.species)
                assertEquals("Max Mustermann", apiPatient.ownerName)
                assertEquals("Hauptstraße 1", apiPatient.ownerAddress)
                assertEquals("123456789", apiPatient.ownerPhoneNumber)
            }
        }

        // add anamnese
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient/$id/anamnese") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        ApiAnamneseCreateUpdate(
                            " kotlinx.datetime.LocalDate(2021, 12, 21)",
                            listOf(
                                ApiOrgan(
                                    name = "lungs",
                                    status = Status.UNCHANGED,
                                    statusChanged = StatusChanged.HYPOECHOGEN,
                                    comment = "",
                                    type = Type.YELLOW
                                )
                            ),
                            listOf(
                                ApiMuscle(
                                    name = "muscleTest",
                                    status = Status.CHANGED,
                                    comment = "this is a test comment patched",
                                    type = Type.YELLOW,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = true,
                                    checkedStateHematoma = false
                                )
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val anamneseCreateSuccessful: AnamneseCreateSuccessful =
                    Gson().fromJson(response.content, AnamneseCreateSuccessful::class.java)
                anamneseId = anamneseCreateSuccessful.id
            }
        }

        // check if added
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id/anamnese/$anamneseId") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiAnamnese: ApiAnamnese =
                    Gson().fromJson(response.content, ApiAnamnese::class.java)
                assertEquals(anamneseId, apiAnamnese.id)
                assertEquals(
                    " kotlinx.datetime.LocalDate(2021, 12, 21)", apiAnamnese.date
                )
                assertEquals(Status.UNCHANGED, apiAnamnese.organs[0].status)
                assertEquals(
                    StatusChanged.HYPOECHOGEN,
                    apiAnamnese.organs[0].statusChanged
                )
                assertEquals("this is a test comment patched", apiAnamnese.muscles[0].comment)
                assertEquals(Type.YELLOW, apiAnamnese.muscles[0].type)
            }
        }

        // update anamnese
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Patch, "/api/patient/$id/anamnese/$anamneseId") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        ApiAnamneseCreateUpdate(
                            "kotlinx.datetime.LocalDate(2021, 12, 21)",
                            listOf(
                                ApiOrgan(
                                    name = "lungs",
                                    status = Status.UNCHANGED,
                                    statusChanged = StatusChanged.HYPOECHOGEN,
                                    comment = "",
                                    type = Type.GREEN
                                ),
                                ApiOrgan(
                                    name = "heart",
                                    status = Status.UNCHANGED,
                                    statusChanged = StatusChanged.HYPOECHOGEN,
                                    comment = "",
                                    type = Type.YELLOW
                                ),
                                ApiOrgan(
                                    name = "lungs",
                                    status = Status.CHANGED,
                                    statusChanged = StatusChanged.UNKNOWN,
                                    comment = "",
                                    type = Type.RED
                                )
                            ),
                            listOf(
                                ApiMuscle(
                                    name = "muscleTest",
                                    status = Status.CHANGED,
                                    comment = "this is a test comment patched",
                                    type = Type.YELLOW,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = true,
                                    checkedStateHematoma = false
                                ),
                                ApiMuscle(
                                    name = "muscleTest1",
                                    status = Status.CHANGED,
                                    comment = "this is a simple muscle comment2",
                                    type = Type.GREEN,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = true,
                                    checkedStateHematoma = false
                                )
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }

        // check if updated
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id/anamnese/$anamneseId") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiAnamnese: ApiAnamnese =
                    Gson().fromJson(response.content, ApiAnamnese::class.java)
                assertEquals(anamneseId, apiAnamnese.id)
                assertEquals(
                    "kotlinx.datetime.LocalDate(2021, 12, 21)", apiAnamnese.date
                )
                assertEquals(Status.CHANGED, apiAnamnese.organs[2].status)
                assertEquals(
                    StatusChanged.HYPOECHOGEN,
                    apiAnamnese.organs[0].statusChanged
                )
                assertEquals(
                    "lungs",
                    apiAnamnese.organs[0].name
                )
                assertEquals(
                    "heart",
                    apiAnamnese.organs[1].name
                )
                assertEquals(
                    "lungs",
                    apiAnamnese.organs[2].name
                )
                assertEquals("this is a test comment patched", apiAnamnese.muscles[0].comment)
                assertEquals(Type.YELLOW, apiAnamnese.muscles[0].type)
            }
        }

    }

    @Test
    fun testDeleteAnamnese() {
        var id: String? = null
        var anamneseId: String? = null

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient8",
                            180.0f,
                            "This is just a simple test 8",
                            Sex.MALE,
                            "kotlinx.datetime.LocalDate(2021, 12, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: PatientCreateSuccessful =
                    Gson().fromJson(response.content, PatientCreateSuccessful::class.java)
                id = status.id
            }
        }

        // check if created
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("Patient8", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("This is just a simple test 8", apiPatient.comments)
                assertEquals(180.0f, apiPatient.weight)
                assertEquals(Sex.MALE, apiPatient.sex)
                assertEquals(
                    "kotlinx.datetime.LocalDate(2021, 12, 21)", apiPatient.birthDate
                )
                assertEquals("Dog", apiPatient.species)
                assertEquals("Max Mustermann", apiPatient.ownerName)
                assertEquals("Hauptstraße 1", apiPatient.ownerAddress)
                assertEquals("123456789", apiPatient.ownerPhoneNumber)
            }
        }

        // add anamnese
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient/$id/anamnese") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        ApiAnamneseCreateUpdate(
                            "kotlinx.datetime.LocalDate(2021, 12, 21)",
                            listOf(
                                ApiOrgan(
                                    name = "lungs",
                                    status = Status.UNCHANGED,
                                    statusChanged = StatusChanged.HYPOECHOGEN,
                                    comment = "",
                                    type = Type.YELLOW
                                )
                            ),
                            listOf(
                                ApiMuscle(
                                    name = "muscleTest",
                                    status = Status.CHANGED,
                                    comment = "this is a simple muscle comment1",
                                    type = Type.YELLOW,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = true,
                                    checkedStateHematoma = false
                                ),
                                ApiMuscle(
                                    name = "muscleTest",
                                    status = Status.CHANGED,
                                    comment = "this is a simple muscle at the 2.position",
                                    type = Type.YELLOW,
                                    checkedStateInjury = false,
                                    checkedStateSwelling = true,
                                    checkedStateHematoma = false
                                )
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val anamneseCreateSuccessful: AnamneseCreateSuccessful =
                    Gson().fromJson(response.content, AnamneseCreateSuccessful::class.java)
                anamneseId = anamneseCreateSuccessful.id
            }
        }

        // check if added
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("Patient8", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("This is just a simple test 8", apiPatient.comments)
                assertEquals(180.0f, apiPatient.weight)
                assertEquals(Sex.MALE, apiPatient.sex)
                assertEquals(
                    "kotlinx.datetime.LocalDate(2021, 12, 21)", apiPatient.birthDate
                )
                assertEquals("Dog", apiPatient.species)
                assertEquals("Max Mustermann", apiPatient.ownerName)
                assertEquals("Hauptstraße 1", apiPatient.ownerAddress)
                assertEquals("123456789", apiPatient.ownerPhoneNumber)
                assertEquals(
                    "this is a simple muscle at the 2.position",
                    apiPatient.anamnese[0].muscles[1].comment
                )
                assertEquals(
                    "lungs",
                    apiPatient.anamnese[0].organs[0].name
                )
                assertEquals(1, apiPatient.anamnese.size)
                assertEquals(anamneseId, apiPatient.anamnese[0].id)
                assertEquals(
                    "kotlinx.datetime.LocalDate(2021, 12, 21)", apiPatient.anamnese[0].date
                )
                assertEquals(Status.UNCHANGED, apiPatient.anamnese[0].organs[0].status)
                assertEquals(
                    StatusChanged.HYPOECHOGEN,
                    apiPatient.anamnese[0].organs[0].statusChanged
                )
                assertEquals(
                    "this is a simple muscle comment1",
                    apiPatient.anamnese[0].muscles[0].comment
                )
                assertEquals(Type.YELLOW, apiPatient.anamnese[0].muscles[0].type)
            }
        }

        // delete anamnese
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Delete, "/api/patient/$id/anamnese/$anamneseId") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }

        // delete anamnese fails
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Delete, "/api/patient/$id/anamnese/100") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }

        // check if anamnese is deleted
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id/anamnese/$anamneseId") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }

    }

    @Test
    fun testAddBodyMap() {
        var id: String? = null

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient2",
                            150.0f,
                            "This is just a simple test 2",
                            Sex.MALE,
                            "kotlinx.datetime.LocalDate(2021, 12, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: PatientCreateSuccessful =
                    Gson().fromJson(response.content, PatientCreateSuccessful::class.java)
                id = status.id
            }
        }

        // check if created
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("Patient2", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("This is just a simple test 2", apiPatient.comments)
                assertEquals(150.0f, apiPatient.weight)
            }
        }

        //create bodymap
        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient/$id/bodymap") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        ApiBodyMapCreateUpdate(
                            bodyMapFieldValues = listOf(
                                ApiFieldValues(
                                    id = "1",
                                    painIntensity = 0,
                                    tensionIntensity = 0
                                ),
                                ApiFieldValues(
                                    id = "2",
                                    painIntensity = 3,
                                    tensionIntensity = 1
                                ),
                                ApiFieldValues(
                                    id = "3",
                                    painIntensity = 2,
                                    tensionIntensity = 0
                                )
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: BodyMapCreateSuccessful =
                    Gson().fromJson(response.content, BodyMapCreateSuccessful::class.java)
                val idOfBodyMap = status.id
                assertEquals("1", idOfBodyMap)
            }
        }

    }

    @Test
    fun testGetBodyMaps() {
        var id: String? = null
        var idOfBodyMap: String? = null

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient2",
                            150.0f,
                            "This is just a simple test 2",
                            Sex.MALE,
                            "kotlinx.datetime.LocalDate(2021, 12, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: PatientCreateSuccessful =
                    Gson().fromJson(response.content, PatientCreateSuccessful::class.java)
                id = status.id
            }
        }

        // check if created
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiPatient =
                    Gson().fromJson(response.content, ApiPatient::class.java)
                assertEquals("Patient2", apiPatient.name)
                assertEquals(id, apiPatient.id)
                assertEquals("This is just a simple test 2", apiPatient.comments)
                assertEquals(150.0f, apiPatient.weight)
            }
        }

        //create bodymap
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient/$id/bodymap") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        ApiBodyMapCreateUpdate(
                            bodyMapFieldValues = listOf(
                                ApiFieldValues(
                                    id = "1",
                                    painIntensity = 0,
                                    tensionIntensity = 0
                                ),
                                ApiFieldValues(
                                    id = "2",
                                    painIntensity = 3,
                                    tensionIntensity = 1
                                ),
                                ApiFieldValues(
                                    id = "3",
                                    painIntensity = 2,
                                    tensionIntensity = 0
                                )
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: BodyMapCreateSuccessful =
                    Gson().fromJson(response.content, BodyMapCreateSuccessful::class.java)
                assertEquals("1", status.id)
            }
        }
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient/$id/bodymap") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        ApiBodyMapCreateUpdate(
                            bodyMapFieldValues = listOf(
                                ApiFieldValues(
                                    id = "4",
                                    painIntensity = 1,
                                    tensionIntensity = 3
                                ),
                                ApiFieldValues(
                                    id = "5",
                                    painIntensity = 3,
                                    tensionIntensity = 1
                                ),
                                ApiFieldValues(
                                    id = "6",
                                    painIntensity = 2,
                                    tensionIntensity = 0
                                )
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: BodyMapCreateSuccessful =
                    Gson().fromJson(response.content, BodyMapCreateSuccessful::class.java)
                idOfBodyMap = status.id
                assertEquals("2", idOfBodyMap)
            }
        }
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id/bodymap/$idOfBodyMap") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiBodyMap =
                    Gson().fromJson(response.content, ApiBodyMap::class.java)
                assertEquals(3, apiPatient.bodyMapFieldValues.size)
                assertEquals("5", apiPatient.bodyMapFieldValues[1].id)
            }
        }
    }

    @Test
    fun testDeleteBodyMap() {
        var id: String? = null
        var idOfBodyMap: String? = null

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient8",
                            180.0f,
                            "This is just a simple test 8",
                            Sex.MALE,
                            " kotlinx.datetime.LocalDate(2021, 12, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: PatientCreateSuccessful =
                    Gson().fromJson(response.content, PatientCreateSuccessful::class.java)
                id = status.id
            }
        }

        //create bodymap
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient/$id/bodymap") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        ApiBodyMapCreateUpdate(
                            bodyMapFieldValues = listOf(
                                ApiFieldValues(
                                    id = "1",
                                    painIntensity = 0,
                                    tensionIntensity = 0
                                ),
                                ApiFieldValues(
                                    id = "2",
                                    painIntensity = 3,
                                    tensionIntensity = 1
                                ),
                                ApiFieldValues(
                                    id = "3",
                                    painIntensity = 2,
                                    tensionIntensity = 0
                                )
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: BodyMapCreateSuccessful =
                    Gson().fromJson(response.content, BodyMapCreateSuccessful::class.java)
                assertEquals("1", status.id)
            }
        }
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient/$id/bodymap") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        ApiBodyMapCreateUpdate(
                            bodyMapFieldValues = listOf(
                                ApiFieldValues(
                                    id = "4",
                                    painIntensity = 1,
                                    tensionIntensity = 3
                                ),
                                ApiFieldValues(
                                    id = "5",
                                    painIntensity = 3,
                                    tensionIntensity = 1
                                ),
                                ApiFieldValues(
                                    id = "6",
                                    painIntensity = 2,
                                    tensionIntensity = 0
                                )
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: BodyMapCreateSuccessful =
                    Gson().fromJson(response.content, BodyMapCreateSuccessful::class.java)
                idOfBodyMap = status.id
                assertEquals("2", idOfBodyMap)
            }
        }

        // delete bodymap
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Delete, "/api/patient/$id/bodymap/$idOfBodyMap") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
        // delete bodymap fails
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Delete, "/api/patient/$id/bodymap/100") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }

        // check if deleted
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id/bodymap/$idOfBodyMap") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }

    @Test
    fun testPatchBodymap() {
        var id: String? = null
        var idOfBodyMap: String? = null

        // create patient
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        PatientCreate(
                            "Patient8",
                            180.0f,
                            "This is just a simple test 8",
                            Sex.MALE,
                            " kotlinx.datetime.LocalDate(2021, 12, 21)",
                            "Dog",
                            "Max Mustermann",
                            "Hauptstraße 1",
                            "123456789",
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: PatientCreateSuccessful =
                    Gson().fromJson(response.content, PatientCreateSuccessful::class.java)
                id = status.id
            }
        }

        //create bodymap
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient/$id/bodymap") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        ApiBodyMapCreateUpdate(
                            bodyMapFieldValues = listOf(
                                ApiFieldValues(
                                    id = "1",
                                    painIntensity = 0,
                                    tensionIntensity = 0
                                ),
                                ApiFieldValues(
                                    id = "2",
                                    painIntensity = 3,
                                    tensionIntensity = 1
                                ),
                                ApiFieldValues(
                                    id = "3",
                                    painIntensity = 2,
                                    tensionIntensity = 0
                                )
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: BodyMapCreateSuccessful =
                    Gson().fromJson(response.content, BodyMapCreateSuccessful::class.java)
                assertEquals("1", status.id)
            }
        }
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Post, "/api/patient/$id/bodymap") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(
                    Gson().toJson(
                        ApiBodyMapCreateUpdate(
                            bodyMapFieldValues = listOf(
                                ApiFieldValues(
                                    id = "4",
                                    painIntensity = 1,
                                    tensionIntensity = 3
                                ),
                                ApiFieldValues(
                                    id = "5",
                                    painIntensity = 3,
                                    tensionIntensity = 1
                                ),
                                ApiFieldValues(
                                    id = "6",
                                    painIntensity = 2,
                                    tensionIntensity = 0
                                )
                            )
                        )
                    )
                )
            }.apply {
                assertEquals(HttpStatusCode.Created, response.status())
                val status: BodyMapCreateSuccessful =
                    Gson().fromJson(response.content, BodyMapCreateSuccessful::class.java)
                idOfBodyMap = status.id
                assertEquals("2", idOfBodyMap)
            }
        }
        // patch bodymap
        val bodyMapPatch = ApiBodyMapCreateUpdate(
            bodyMapFieldValues = listOf(
                ApiFieldValues(
                    id = "1",
                    painIntensity = 0,
                    tensionIntensity = 0
                ),
                ApiFieldValues(
                    id = "2",
                    painIntensity = 0,
                    tensionIntensity = 0,
                ),
                ApiFieldValues(
                    id = "3",
                    painIntensity = 1,
                    tensionIntensity = 1
                ),
                ApiFieldValues(
                    id = "4",
                    painIntensity = 3,
                    tensionIntensity = 3
                )
            )
        )
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Patch, "/api/patient/$id/bodymap/$idOfBodyMap") {
                addJwtHeader()
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(Gson().toJson(bodyMapPatch))
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }

        // check if patched
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/patient/$id/bodymap/$idOfBodyMap") {
                addJwtHeader()
            }.apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val apiPatient: ApiBodyMap =
                    Gson().fromJson(response.content, ApiBodyMap::class.java)
                assertEquals(4, apiPatient.bodyMapFieldValues.size)
                assertEquals("4", apiPatient.bodyMapFieldValues[3].id)
                assertEquals(0, apiPatient.bodyMapFieldValues[0].painIntensity)
                assertEquals(1, apiPatient.bodyMapFieldValues[2].tensionIntensity)
            }
        }


    }

    @Test
    fun testCreateDummyData() {
        withTestApplication({ module(true) }) {
            handleRequest(HttpMethod.Get, "/api/startup/add/dummydata") {
            }.apply {
                val statusResponse: StatusResponse =
                    Gson().fromJson(response.content, StatusResponse::class.java)
                assertEquals("successfully created dummy data", statusResponse.status)
                assertEquals(HttpStatusCode.OK, response.status())
            }
        }
    }

    private fun TestApplicationRequest.addJwtHeader() =
        addHeader("Authorization", "Bearer ${getToken()}")

    private fun getToken() = JwtConfig(jwtSecret).generateToken(JwtUser(1, "Admin"))

}
