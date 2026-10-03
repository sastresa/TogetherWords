package com.togetherwords

import org.junit.Assert.assertEquals
import org.junit.Test

class AppFlowTest {
    @Test
    fun practiceAndBackReturnHome() {
        val flow = AppFlow()
        flow.openPractice()
        assertEquals(Screen.WORDS, flow.screen)
        flow.back()
        assertEquals(Screen.HOME, flow.screen)
    }

    @Test
    fun helpOffersWordsAndBalloonWithoutTrappingTheChild() {
        val flow = AppFlow()
        flow.openHelp()
        assertEquals(Screen.HELP, flow.screen)
        flow.openWords()
        assertEquals(Screen.WORDS, flow.screen)
        flow.back()
        assertEquals(Screen.HELP, flow.screen)
        flow.openBalloon()
        assertEquals(Screen.BALLOON, flow.screen)
        flow.back()
        assertEquals(Screen.HELP, flow.screen)
    }

    @Test
    fun languageUsesSupportedDeviceLanguageAndAllowsOverride() {
        assertEquals(AppLanguage.SPANISH, AppLanguage.fromDevice("es"))
        assertEquals(AppLanguage.ENGLISH, AppLanguage.fromDevice("en"))
        assertEquals(AppLanguage.ENGLISH, AppLanguage.fromDevice("fr"))
    }

    @Test
    fun adultsGuideReturnsToHome() {
        val flow = AppFlow()
        flow.openAdults()
        assertEquals(Screen.ADULTS, flow.screen)
        flow.back()
        assertEquals(Screen.HOME, flow.screen)
    }

    @Test
    fun adultScreenSurvivesActivityRecreationForLanguageChanges() {
        val flow = AppFlow()
        flow.openAdults()
        val restored = AppFlow(flow.path)
        assertEquals(Screen.ADULTS, restored.screen)
        restored.back()
        assertEquals(Screen.HOME, restored.screen)
    }

    @Test
    fun deviceLanguageChoiceCanResetAnOverride() {
        assertEquals(AppLanguage.SPANISH, LanguageChoice.DEVICE.resolve("es"))
        assertEquals(AppLanguage.ENGLISH, LanguageChoice.DEVICE.resolve("fr"))
        assertEquals(AppLanguage.SPANISH, LanguageChoice.SPANISH.resolve("en"))
        assertEquals(LanguageChoice.DEVICE, LanguageChoice.fromSavedCode(null))
        assertEquals(LanguageChoice.DEVICE, LanguageChoice.fromSavedCode("device"))
        assertEquals(LanguageChoice.ENGLISH, LanguageChoice.fromSavedCode("en"))
    }
}
