package de.h_da.fbi.common.remote

import de.h_da.fbi.common.entity.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent

/**
 * @brief Connects the Frontend to the MongoDB of this project
 *
 * The FindusApi uses KTor to connect to MongoDB with HttpRequest and HttpResponse via post, get, patch, delete to change data in the Database
 */
class FindusApi(

    private val client: HttpClient,
    private val baseUrl: String = "http://localhost:8082/api",
    private val loginURL: String = "$baseUrl/login",
    private val patientURL: String = "$baseUrl/patient",
    private val dummyRoute: String = "$baseUrl/startup/add/dummydata"
) : KoinComponent {

    /**
     * create a token for API-authentication
     *
     * @return the token
     */
    suspend fun loginRequest(): HttpResponse = client.post(loginURL) {
        contentType(ContentType.Application.Json)
        body = LoginRequest("Admin", "Admin")
    }

    /**
     * fetch all saved patients from the db
     *
     * @param token the API-authentication
     * @return all found patients
     */
    suspend fun fetchPatients(token: String) = client.get<List<ApiPatient>>(patientURL) {
        contentType(ContentType.Application.Json)
        header("Authorization", "Bearer $token")
    }

    /**
     * fetch a patient with a specific id from the API
     *
     * @param token the API-authentication
     * @param Id the id of the patient
     * @return the patient with the given id
     */
    suspend fun fetchPatient(token: String, Id: String) =
        client.get<ApiPatient>("$patientURL/$Id") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }


    /**
     * update a patient
     *
     * @param patientToUpdate the patient information that should get updated
     * @param token the API-authentication
     * @param Id the id of the patient
     * @return the response whether or not the update was successful
     */
    suspend fun patchPatient(patientToUpdate: PatientUpdate, token: String, Id: String) =
        client.patch<StatusResponse>("$patientURL/$Id") {
            header("Authorization", "Bearer $token")
            contentType(ContentType.Application.Json)
            body = patientToUpdate
        }

    /**
     * save a new patient
     *
     * @param addPatient the patient that should be stored
     * @param token the API-authentication
     * @return the id of the newly created patient
     */
    suspend fun postPatients(addPatient: PatientCreate, token: String): HttpResponse =
        client.post(patientURL) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
            body = addPatient
        }

    /**
     * Deletes a specific Patient from the database
     *
     * @param Id is the id of the Patient
     * @param token the API-authentication
     */
    suspend fun deletePatient(Id: String, token: String) =
        client.delete<StatusResponse>("$patientURL/$Id") {
            header("Authorization", "Bearer $token")
        }


    /**
     * Uploads an image to a specific patient
     *
     * @param id is the Id of the Patient, to whom the Image should be uploaded
     * @param image is the image File as a ByteArray
     * @param token The authentication token
     *
     * @return HttpResponse
     */
    suspend fun uploadImage(id: String, image: ByteArray, token: String): HttpResponse =
        client.post("$patientURL/$id/picture") {
            contentType(ContentType.Image.Any)
            header("Authorization", "Bearer $token")
            body = image
        }

    /**
     * Downloads an image from a specific patient
     * @return The requested image
     * @param id is the Id of the Patient, from whom the Image should be downloaded
     * @param imageId the image id
     * @param token The authentication token
     */
    suspend fun fetchImage(id: String, imageId: String, token: String) =
        client.get<ByteArray>("$patientURL/$id/picture/$imageId") {
            accept(ContentType.Image.Any)
            header("Authorization", "Bearer $token")
        }

    /**
     * create a new anamnesis
     *
     * @param Id the id of the patient
     * @param anamnese the anamnesis to insert
     * @param token the API-authentication
     * @return the id of the anamnesis
     */
    suspend fun postAnamnese(
        Id: String,
        anamnese: ApiAnamneseCreateUpdate,
        token: String
    ): HttpResponse =
        client.post("$patientURL/$Id/anamnese") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
            body = anamnese
        }

    /**
     * update an anamnesis
     *
     * @param Id the patient id
     * @param AnamneseId the id of the anamnesis
     * @param anamnese the anamnesis to update
     * @param token the API-authentication
     * @return the response whether the operation was successful
     */
    suspend fun patchAnamnese(
        Id: String,
        AnamneseId: String,
        anamnese: ApiAnamneseCreateUpdate,
        token: String
    ) = client.patch<StatusResponse>("$patientURL/$Id/anamnese/$AnamneseId") {
        header("Authorization", "Bearer $token")
        contentType(ContentType.Application.Json)
        body = anamnese
    }

    /**
     * load a specific anamnesis by it's id
     *
     * @param Id the patient id
     * @param AnamneseId the id of the anamnesis
     * @param token the API-authentication
     * @return the found anamnesis
     */
    suspend fun fetchAnamnese(Id: String, AnamneseId: String, token: String) =
        client.get<ApiAnamnese>("$patientURL/$Id/anamnese/$AnamneseId") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }

    /**
     * delete a specific anamnesis by it's id
     *
     * @param Id the patient id
     * @param AnamneseId the id of the anamnesis
     * @param token the API-authentication
     * @return the response whether the operation was successful
     */
    suspend fun deleteAnamnese(Id: String, AnamneseId: String, token: String) =
        client.delete<StatusResponse>("$patientURL/$Id/anamnese/$AnamneseId") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }

    /**
     * create a new bodymap
     *
     * @param Id the patient id
     * @param bodyMap the bodymap to save
     * @param token  the API-authentication
     * @return the id of the newly created bodymap
     */
    suspend fun postBodyMap(
        Id: String,
        bodyMap: ApiBodyMapCreateUpdate,
        token: String
    ): HttpResponse =
        client.post("$patientURL/$Id/bodymap") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
            body = bodyMap
        }

    /**
     * load all bodyMaps of a patient
     *
     * @param Id the patient id
     * @param token  the API-authentication
     * @return all found bodyMaps of the patient
     */
    suspend fun getBodyMaps(Id: String, token: String) =
        client.get<List<ApiBodyMap>>("$patientURL/$Id/bodymap") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }

    /**
     * load a specific bodymap by it's id
     *
     * @param Id the patient id
     * @param bodyMapId the bodymap's id
     * @param token  the API-authentication
     * @return the found bodyMap
     */
    suspend fun getBodyMap(Id: String, bodyMapId: String, token: String) =
        client.get<ApiBodyMap>("$patientURL/$Id/bodymap/$bodyMapId") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }

    /**
     * update a bodyMap by it's id
     *
     * @param Id the patient id
     * @param bodyMapId the bodymap's id
     * @param bodyMap the bodyMap to update
     * @param token  the API-authentication
     * @return the response whether the operation was successful
     */
    suspend fun patchBodyMap(
        Id: String,
        bodyMapId: String,
        bodyMap: ApiBodyMapCreateUpdate,
        token: String
    ) = client.patch<StatusResponse>("$patientURL/$Id/bodymap/$bodyMapId") {
        header("Authorization", "Bearer $token")
        contentType(ContentType.Application.Json)
        body = bodyMap
    }

    /**
     * delete a bodyMap by it's id
     *
     * @param Id the patient id
     * @param bodyMapId the bodymap's id
     * @param token  the API-authentication


     */
    suspend fun deleteBodyMap(Id: String, bodyMapId: String, token: String) =
        client.delete<StatusResponse>("$patientURL/$Id/bodymap/$bodyMapId") {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $token")
        }

    /**
     * create dummydata in the database
     *
     * @return the response whether the operation was successful
     */
    suspend fun createDummyData() =
        client.get<StatusResponse>(dummyRoute) {
            contentType(ContentType.Application.Json)
        }

}
