package de.h_da.fbi.findus.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class PatientData(
    var name: String,
    var weight: Float,
    var comments: String
)

class TextFieldState {
    var name: String by mutableStateOf("")
    var weight: String by mutableStateOf("0.0")
    var comments: String by mutableStateOf("")
}
