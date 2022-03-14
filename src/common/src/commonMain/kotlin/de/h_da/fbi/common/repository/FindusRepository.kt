package de.h_da.fbi.common.repository

import co.touchlab.kermit.Kermit
import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import de.h_da.fbi.common.entity.*
import de.h_da.fbi.common.remote.FindusApi
import io.ktor.client.call.*
import io.ktor.client.features.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface FindusRepositoryInterface {

    /**
     * load all saved patients
     *
     * @return the found patients
     */
    suspend fun fetchPatients(): List<ApiPatient>

    /**
     * load a specific patient by it's id
     *
     * @param id the id of the patient
     * @return the found patient
     */
    suspend fun fetchPatient(id: String): ApiPatient

    /**
     * save a new patient to the database
     *
     * @param patientToAdd the patient data that should be saved
     * @return the id of the new patient
     */
    suspend fun postPatient(patientToAdd: PatientCreate): String

    /**
     * update a patient
     *
     * @param id the id of the patient
     * @param patientToUpdate the patient information that should be updated
     * @return the response whether the operation was successful
     */
    suspend fun patchPatient(id: String, patientToUpdate: PatientUpdate): String

    /**
     * delete a patient by it's id
     *
     * @param id the id of the patient
     * @return the response whether the operation was successful
     */
    suspend fun deletePatient(id: String): String

    /**
     * load a specific anamnesis
     *
     * @param id the id of the patient
     * @param anamneseId the id of the anamnesis
     * @return the found anamnesis object
     */
    suspend fun fetchAnamnese(id: String, anamneseId: String): ApiAnamnese

    /**
     * create a new anamnesis
     *
     * @param id the id of the patient
     * @param anamnese the anamnesis to save
     * @return the id of the new anamnesis
     */
    suspend fun postAnamnese(Id: String, anamnese: ApiAnamneseCreateUpdate): String

    /**
     * update an anamnesis
     *
     * @param id the id of the patient
     * @param AnamneseId the id of the anamnesis
     * @param anamnese the anamnesis to update
     * @return the response whether the operation was successful
     */
    suspend fun patchAnamnese(
        Id: String,
        AnamneseId: String,
        anamnese: ApiAnamneseCreateUpdate
    ): String

    /**
     * delete an anamnesis
     *
     * @param id the id of the patient
     * @param AnamneseId the id of the anamnesis
     * @return the response whether the operation was successful
     */
    suspend fun deleteAnamnese(Id: String, AnamneseId: String): String

    /**
     * load a bodyMap with a specific id
     *
     * @param id the id of the patient
     * @param bodyMapId the id of the bodyMap
     * @return the found bodyMap
     */
    suspend fun fetchBodyMap(id: String, bodyMapId: String): ApiBodyMap

    /**
     * load all bodyMaps of a patient
     *
     * @param id the id of the patient
     * @return all found bodyMaps of the patient
     */
    suspend fun fetchBodyMaps(id: String): List<ApiBodyMap>

    /**
     * create a new bodyMap
     *
     * @param Id the id of the patient
     * @param bodyMap the bodyMap information to save
     * @return the id of the new bodyMap
     */
    suspend fun postBodyMap(Id: String, bodyMap: ApiBodyMapCreateUpdate): String

    /**
     * update a bodyMap
     *
     * @param Id the id of the patient
     * @param bodyMapId the id of the bodyMap
     * @param bodyMap the bodyMap to update
     * @return the response whether the operation was successful
     */
    suspend fun patchBodyMap(
        Id: String,
        bodyMapId: String,
        bodyMap: ApiBodyMapCreateUpdate,
    ): String

    /**
     * delete a bodyMap
     *
     * @param Id the id of the patient
     * @param bodyMapId the id of the bodyMap
     * @return the response whether the operation was successful
     */
    suspend fun deleteBodyMap(Id: String, bodyMapId: String): String

    /**
     * upload an image
     *
     * @param id the id of the patient
     * @param image the image to upload
     * @return the response whether the operation was successful
     */
    suspend fun postImage(id: String, image: ByteArray): String

    /**
     * load a specific image
     *
     * @param id the id of the patient
     * @param imageId the id of the image to load
     * @return the found image
     */
    suspend fun fetchImage(id: String, imageId: String): ByteArray

    /**
     * create dummydata in the database
     *
     * @return the response whether the operation was successful
     */

    suspend fun createDummyData(): String

}

class FindusRepository : KoinComponent, FindusRepositoryInterface {

    private val findusApi: FindusApi by inject()
    private val logger: Kermit by inject()
    private var token = ""
    private val loggerMessage= "Error calling api:"

    @NativeCoroutineScope
    private val coroutineScope: CoroutineScope = MainScope()

    private suspend fun generateToken() {
        val response = findusApi.loginRequest().receive<LoginResponse>()
        token = response.token.toString()
    }

    override suspend fun fetchPatients(): List<ApiPatient> {
        checkToken()
        return try {
            findusApi.fetchPatients(token)
        } catch (exception: Exception) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }

    }

    override suspend fun fetchPatient(id: String): ApiPatient {
        checkToken()
        return try {
            findusApi.fetchPatient(token, id)
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun postPatient(patientToAdd: PatientCreate): String {
        checkToken()
        try {
            return findusApi.postPatients(patientToAdd, token).receive<PatientCreateSuccessful>().id
        } catch (exception: Exception) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun patchPatient(id: String, patientToUpdate: PatientUpdate): String {
        checkToken()
        try {
            return findusApi.patchPatient(patientToUpdate, token, id).status ?: ""
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun deletePatient(id: String): String {
        checkToken()
        try {
            return findusApi.deletePatient(id, token).status ?: ""
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun fetchAnamnese(id: String, anamneseId: String): ApiAnamnese {
        checkToken()
        return try {
            findusApi.fetchAnamnese(id, anamneseId, token)
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }


    override suspend fun postAnamnese(Id: String, anamnese: ApiAnamneseCreateUpdate): String {
        checkToken()
        try {
            return findusApi.postAnamnese(Id, anamnese, token)
                .receive<AnamneseCreateSuccessful>().id
        } catch (exception: Exception) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun patchAnamnese(
        Id: String,
        AnamneseId: String,
        anamnese: ApiAnamneseCreateUpdate
    ): String {
        return try {
            checkToken()
            findusApi.patchAnamnese(Id, AnamneseId, anamnese, token).status ?: ""
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun deleteAnamnese(Id: String, AnamneseId: String): String {
        checkToken()
        return try {
            findusApi.deleteAnamnese(Id, AnamneseId, token).status ?: ""
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun fetchBodyMap(id: String, bodyMapId: String): ApiBodyMap {
        checkToken()
        return try {
            findusApi.getBodyMap(id, bodyMapId, token)
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun fetchBodyMaps(id: String): List<ApiBodyMap> {
        checkToken()
        return try {
            findusApi.getBodyMaps(id, token)
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }

    }

    override suspend fun postBodyMap(Id: String, bodyMap: ApiBodyMapCreateUpdate): String {
        checkToken()
        return try {
            findusApi.postBodyMap(Id, bodyMap, token).receive<BodyMapCreateSuccessful>().id
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun patchBodyMap(
        Id: String,
        bodyMapId: String,
        bodyMap: ApiBodyMapCreateUpdate
    ): String {
        checkToken()
        return try {
            findusApi.patchBodyMap(Id, bodyMapId, bodyMap, token).status ?: ""
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun deleteBodyMap(Id: String, bodyMapId: String): String {
        checkToken()
        return try {
            findusApi.deleteBodyMap(Id, bodyMapId, token).status ?: ""
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun postImage(id: String, image: ByteArray): String {
        checkToken()
        return try {
            findusApi.uploadImage(id, image, token).receive<StatusResponse>().status ?: ""
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun fetchImage(id: String, imageId: String): ByteArray {
        checkToken()
        return try {
            findusApi.fetchImage(id, imageId, token)
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }

    override suspend fun createDummyData(): String {
        checkToken()
        return try {
            findusApi.createDummyData().status ?: ""
        } catch (exception: ClientRequestException) {
            logger.e { "$loggerMessage $exception" }
            throw exception
        }
    }
    private suspend fun checkToken() {
        if (token == "") {
            generateToken()
        }
    }

}
