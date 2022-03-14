package de.h_da.fbi.findus.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import de.h_da.fbi.findus.R
import org.junit.Rule
import org.junit.Test
import java.util.*

class InternationalizationTest {
    // Access to an activity
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Check if the texts is displayed
     * @see https://developer.android.com/jetpack/compose/testing
     */
    @Test
    fun test_GUI() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.app_name),
            useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.mainactivitiy_btn_logout),
            useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.mainactivitiy_btn_nav_dashboard),
            useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.mainactivitiy_btn_nav_anamnese),
            useUnmergedTree = true).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.mainactivitiy_btn_nav_bodymapping),
            useUnmergedTree = true).assertIsDisplayed()
    }
}
