package de.h_da.fbi.findus.entities

import com.papsign.ktor.openapigen.annotations.Response
import de.h_da.fbi.findus.database.models.Sex
import de.h_da.fbi.findus.database.models.Status
import de.h_da.fbi.findus.database.models.StatusChanged
import de.h_da.fbi.findus.database.models.Type
import kotlinx.serialization.Serializable
import java.util.*

@Response("A patient which is stored in the database.", statusCode = 200)
@Serializable
data class ApiPatient(
    var id: String,
    var name: String,
    var weight: Float,
    var comments: String,
    var sex: Sex,
    var birthDate: String,
    var species: String,
    var ownerName: String,
    var ownerAddress: String,
    var ownerPhoneNumber: String,

    var diagnostic: String,
    var diagnosticSymptoms: List<String>,
    var diagnosticNotes: List<String>,

    var chartDataWeight: List<ApiChartData>,
    var chartDataMuscle: List<ApiChartData>,

    var anamnese: List<ApiAnamnese>,
    var bodyMaps: List<ApiBodyMap>,
) {
    companion object {
        val EXAMPLE = ApiPatient(
            "randomId",
            "Bello",
            100.0f,
            "Is a nice dog ;)",
            Sex.MALE,
            "LocalDate(2020, 2, 21)",
            "Dog",
            "Max Mustermann",
            "Hauptstraße 1",
            "123456789",
            "Has a broken leg",
            listOf("Pain", "Swollen leg"),
            listOf("diagnosticNote1", "diagnosticNote2"),
            listOf(
                ApiChartData(Date().toString(), 10.0f),
                ApiChartData(Date().toString(), 15.0f),
                ApiChartData(Date().toString(), 20.0f)
            ),
            listOf(
                ApiChartData(Date().toString(), 5.0f),
                ApiChartData(Date().toString(), 10.0f),
                ApiChartData(Date().toString(), 15.0f)
            ),
            listOf(
                ApiAnamnese(
                    "exampleId",
                    " LocalDate(2020, 2, 21)",
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
                            comment = "",
                            type = Type.GREEN,
                            checkedStateInjury = false,
                            checkedStateSwelling = false,
                            checkedStateHematoma = true
                        )
                    )
                ),
                ApiAnamnese(
                    "exampleId",
                    "LocalDate(2020, 2, 21)",
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
                            comment = "",
                            type = Type.GREEN,
                            checkedStateInjury = false,
                            checkedStateSwelling = false,
                            checkedStateHematoma = true
                        )
                    )
                ),
            ),
            listOf(
                ApiBodyMap(
                    "1", listOf(
                        ApiFieldValues(
                            id = "1",
                            painIntensity = 1,
                            tensionIntensity = 3
                        ),
                        ApiFieldValues(
                            id = "2",
                            painIntensity = 3,
                            tensionIntensity = 1
                        )
                    )
                )
            )
        )
        val EXAMPLE_LIST = listOf(
            EXAMPLE,
            ApiPatient(
                "randomId",
                "Bella",
                90.0f,
                "Is a nice dog ;)",
                Sex.FEMALE,
                "LocalDate(2020, 2, 21)",
                "Dog",
                "Max Mustermann",
                "Hauptstraße 1",
                "123456789",
                "Has a broken leg",
                listOf("Pain", "Swollen leg"),
                listOf("diagnosticNote1", "diagnosticNote2"),
                listOf(
                    ApiChartData(Date().toString(), 10.0f),
                    ApiChartData(Date().toString(), 15.0f),
                    ApiChartData(Date().toString(), 20.0f)
                ),
                listOf(
                    ApiChartData(Date().toString(), 5.0f),
                    ApiChartData(Date().toString(), 10.0f),
                    ApiChartData(Date().toString(), 15.0f)
                ),
                listOf(
                    ApiAnamnese(
                        "exampleId",
                        "LocalDate(2020, 2, 21)",
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
                                comment = "",
                                type = Type.GREEN,
                                checkedStateInjury = false,
                                checkedStateSwelling = false,
                                checkedStateHematoma = true
                            )
                        )
                    ),
                    ApiAnamnese(
                        "exampleId",
                        " LocalDate(2020, 2, 21)",
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
                                comment = "",
                                type = Type.GREEN,
                                checkedStateInjury = false,
                                checkedStateSwelling = false,
                                checkedStateHematoma = true
                            )
                        )
                    ),
                ),
                listOf(
                    ApiBodyMap(
                        "2", listOf(
                            ApiFieldValues(
                                id = "1",
                                painIntensity = 0,
                                tensionIntensity = 2
                            ),
                            ApiFieldValues(
                                id = "2",
                                painIntensity = 1,
                                tensionIntensity = 3
                            )
                        )
                    )
                )
            )
        )
    }
}
