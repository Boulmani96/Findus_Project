package de.h_da.fbi.findus.routes

import com.papsign.ktor.openapigen.route.info
import com.papsign.ktor.openapigen.route.path.normal.NormalOpenAPIRoute
import com.papsign.ktor.openapigen.route.path.normal.get
import com.papsign.ktor.openapigen.route.response.respond
import com.papsign.ktor.openapigen.route.route
import de.h_da.fbi.findus.database.services.PatientService
import de.h_da.fbi.findus.entities.StatusResponse

const val dummyDataRoute = "/startup/add/dummydata"
const val dummyResponse = "successfully created dummy data"
const val dummyResponseError = "error occurred while creating dummy data"

/**
 * route that can be used to create dummyData
 *
 */
fun NormalOpenAPIRoute.dummyRoute() {

    val patientPersistenceService = PatientService()

    route(dummyDataRoute) {
        get<Unit, StatusResponse>(
            info(
                summary = "insert dummy data",
                description = "inserts dummy data into the database."
            ),
            example = StatusResponse(dummyResponse)
        ) {
            when (patientPersistenceService.createDummyData()) {
                true -> respond(StatusResponse(dummyResponse))
                else -> respond(StatusResponse(dummyResponseError))
            }
        }
    }
}
