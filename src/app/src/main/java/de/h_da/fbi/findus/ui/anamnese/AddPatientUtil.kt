package de.h_da.fbi.findus.ui.anamnese

object AddPatientUtil {
    private const val MAX_NAME_LENGTH = 30
    private const val MAX_NOTE_LENGTH = 500

    /**
     * the input is NOT valid if...
     * ...the patient name      is more than MAX_NAME_LENGTH characters long
     * ...                      is empty
     * ...the patient weight    is not convertible to a float
     * ...                      is empty
     * ...the patient notes     is more than MAX_NOTE_LENGTH characters long
     *
     * patient pictures are not relevant in this test due to the fact that it uses
     * Windows File Explorer logic with the set restriction that only .jpg files can be selected
     */


    /**
     * @brief : Function that checks if user input is valid
     * @param : Takes a String for the name of a patient, the weight and the notes as parameters
     * @return : True if input is valid; False if not
     */
    fun validatePatientData(
        patientName: String,
        patientWeight: String,
        patientNotes: String
    ): Boolean {

        if (patientName.isEmpty() || patientWeight.isEmpty()) {
            return false
        }

        if (patientName.length > MAX_NAME_LENGTH) {
            return false
        }

        try {
            patientWeight.toFloat()
        } catch (ERROR: NumberFormatException) {
            return false
        }

        if (patientNotes.length > MAX_NOTE_LENGTH) {
            return false
        }

        return true
    }
}
