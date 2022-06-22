package effective.band.compose.drawer_base

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adb
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import effective.band.compose.drawer_base.DebugDrawerModule
import org.junit.Rule
import org.junit.Test

class ModuleHeaderTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun module_header_expands() {
        composeTestRule.setContent {
            TestModule(expanded = false)
        }

        composeTestRule.onNodeWithText("Module content").assertDoesNotExist()

        composeTestRule
            .onNodeWithText("Test module")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithText("Module content").assertIsDisplayed()
    }

    @Test
    fun module_header_collapses() {
        composeTestRule.setContent {
            TestModule(expanded = true)
        }

        composeTestRule.onNodeWithText("Module content").assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Test module")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.onNodeWithText("Module content").assertDoesNotExist()
    }

    @Composable
    fun TestModule(expanded: Boolean = true) {
        DebugDrawerModule(
            title = "Test module",
            icon = { Icons.Default.Adb },
            showBadge = true,
            expanded = expanded,
        ) {
            Text(text = "Module content")
        }
    }
}