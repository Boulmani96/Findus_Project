package de.h_da.fbi.findus.model

import de.h_da.fbi.findus.R

const val ONBOARDING_TITLE_ONE = "Willkommen"
const val ONBOARDING_DESCRIPTION_ONE = "Willkommen in der Findus App. Hier können Sie veterinäre Befunde und Behandlungen dokumentieren."

const val ONBOARDING_TITLE_TWO = "Anamnese"
const val ONBOARDING_DESCRIPTION_TWO = "Es ist in der App möglich, die Daten eines Patienten im Anamnese Screen zu erfassen und zu laden. \n" +
                                        "Im nächsten Schritt fragen Wir Sie nach \n erforderlichen Zugriffen auf Ihre Kamera und Medien."

const val ONBOARDING_TITLE_THREE = "Bodymapping"
const val ONBOARDING_DESCRIPTION_THREE = "Die App ermöglicht es Ihnen, die Bodymap von einem Patienten zu erstellen" +
                                            "\n und per Drag & Drop Schmerzpunkte zu markieren."

const val ONBOARDING_TITLE_FOUR = "Los geht's!"
const val ONBOARDING_DESCRIPTION_FOUR = "Jetzt wo Sie über die Funktionen bescheid wissen, können Sie anfangen."

/**
 * Page is a page on the Onboarding
 * @param index is the chronological index number of the Page
 * @param title is the title or header of the Page
 * @param description description of text of the Page
 * @param image an animation or image of the Page
 */
data class Page(val index: Int, val title: String , val description : String,
               val image: Int)

val onboardPages = listOf(
    Page(
        0,
        ONBOARDING_TITLE_ONE,
        ONBOARDING_DESCRIPTION_ONE,
        R.raw.dog_walking
    ),
    Page(
        1,
        ONBOARDING_TITLE_TWO,
        ONBOARDING_DESCRIPTION_TWO,
        R.raw.cameras_and_photography
    ),
    Page(
        2,
        ONBOARDING_TITLE_THREE,
        ONBOARDING_DESCRIPTION_THREE,
        R.raw.drag_animation
    ),
    Page(
        3,
        ONBOARDING_TITLE_FOUR,
        ONBOARDING_DESCRIPTION_FOUR,
        R.raw.rocket_animation
    )
)
