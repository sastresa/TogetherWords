package com.togetherwords

import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class MainFlowTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun childCanOpenWordsAndReturnHome() {
        rule.onNodeWithTag("practice").performClick()
        rule.onNodeWithTag("phrase_no").assertExists()
        rule.onNodeWithTag("phrase_stop").assertExists()
        rule.onNodeWithTag("phrase_help").assertExists()
        rule.onNodeWithTag("phrase_break").assertExists()
        rule.onNodeWithTag("phrase_help").performClick()
        rule.onNodeWithTag("phrase_help").assertExists()
        rule.onNodeWithTag("back").performClick()
        rule.onNodeWithTag("practice").assertExists()
    }

    @Test
    fun childCanReachBalloonFromHelpAndSwitchToSpanish() {
        rule.onNodeWithTag("adults").performClick()
        rule.onNodeWithTag("language_selector").performClick()
        rule.onNodeWithTag("language_es").performClick()
        rule.onNodeWithTag("back").performClick()
        rule.onNodeWithText("Necesito ayuda ahora").assertExists()
        rule.onNodeWithTag("help_now").performClick()
        rule.onNodeWithTag("language_es").assertDoesNotExist()
        rule.onNodeWithTag("calm_down").performClick()
        rule.onNodeWithTag("balloon").performClick()
        rule.onNodeWithText("Déjalo descansar").assertExists()
        rule.onNodeWithTag("back").performClick()
        rule.onNodeWithTag("say_something").assertExists()
        rule.onNodeWithTag("say_something").performClick()
        rule.onNodeWithText("Necesito un descanso").assertExists()
    }

    @Test
    fun adultsCanReadGuideAndReturnToDeviceLanguage() {
        rule.onNodeWithTag("adults").performClick()
        rule.onNodeWithTag("adult_before").assertExists()
        rule.onNodeWithTag("adult_during").assertExists()
        rule.onNodeWithTag("adult_after").assertExists()
        rule.onNodeWithTag("privacy_policy").assertExists()
        rule.onNodeWithTag("language_selector").performClick()
        rule.onNodeWithTag("language_es").performClick()
        rule.onNodeWithText("Para adultos").assertExists()
        rule.onNodeWithText("Por qué aparecen las rabietas").assertExists()
        rule.onNodeWithText("Cómo ayudarle").assertExists()
        rule.onNodeWithTag("language_selector").performClick()
        rule.onNodeWithTag("language_device").performClick()
        rule.onNodeWithTag("back").performClick()
        rule.onNodeWithTag("language_es").assertDoesNotExist()
        rule.onNodeWithTag("practice").assertExists()
    }
}
