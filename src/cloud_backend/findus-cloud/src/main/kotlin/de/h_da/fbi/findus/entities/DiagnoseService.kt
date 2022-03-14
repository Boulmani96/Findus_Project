package de.h_da.fbi.findus.entities

/**
 * interface that processes different therapy requests
 *
 */
interface DiagnoseService {
    fun requestDiagnose(validRequest: Boolean): String {
        return ""
    }
}
