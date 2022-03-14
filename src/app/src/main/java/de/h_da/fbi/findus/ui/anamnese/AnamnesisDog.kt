package de.h_da.fbi.findus.ui.anamnese

import org.litote.kmongo.Id
import java.util.*

 data class AnamnesisDog(
    val id: Id<AnamnesisDog>,
    val date: Date
) {
    val anamnesisOrgan = listOf<Organ>()
    val anamnesisMuscle = listOf<Muscle>()
}