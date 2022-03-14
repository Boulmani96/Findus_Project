package de.h_da.fbi.findus.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import org.junit.Rule
import org.junit.Test

class MainActivityTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun testTextFieldDisplay() {
        val textFieldName = composeTestRule.onNodeWithText(
            "Name",
            useUnmergedTree = true
        )
        textFieldName.assertIsDisplayed()

        val textFieldWeight = composeTestRule.onNodeWithText(
            "Weight",
            useUnmergedTree = true
        )
        textFieldWeight.assertIsDisplayed()

        val textFieldNotes = composeTestRule.onNodeWithText(
            "Notes",
            useUnmergedTree = true
        )
        textFieldNotes.assertIsDisplayed()

        // ====================

        val buttonSave = composeTestRule.onNodeWithText(
            "Save",
            useUnmergedTree = true
        )
        buttonSave.assertIsDisplayed()

        val buttonChangeImage = composeTestRule.onNodeWithText(
            "Change",
            useUnmergedTree = true
        )
        buttonChangeImage.assertIsDisplayed()

        val buttonDeleteImage = composeTestRule.onNodeWithText(
            "- Delete",
            useUnmergedTree = true
        )
        buttonDeleteImage.assertIsDisplayed()

        val buttonLoad = composeTestRule.onNodeWithText(
            "Load Patient",
            useUnmergedTree = true
        )
        buttonLoad.assertIsDisplayed()

        val buttonDelete = composeTestRule.onNodeWithText(
            "Delete Patient",
            useUnmergedTree = true
        )
        buttonDelete.assertIsDisplayed()

        val buttonNewPatient = composeTestRule.onNodeWithText(
            "New Patient",
            useUnmergedTree = true
        )
        buttonNewPatient.performScrollTo()
        buttonNewPatient.assertIsDisplayed()
    }
}
