package com.togetherwords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
    private lateinit var flow: AppFlow
    private lateinit var languages: LanguageSettings
    private lateinit var speaker: PhraseSpeaker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        flow =
            AppFlow(
                savedInstanceState?.getStringArrayList(FLOW_KEY)?.mapNotNull { saved ->
                    runCatching { Screen.valueOf(saved) }.getOrNull()
                } ?: listOf(Screen.HOME)
            )
        languages = LanguageSettings(this)
        speaker = PhraseSpeaker(this)
        val initialLanguage = languages.read()

        setContent {
            var languageChoice by remember { mutableStateOf(initialLanguage.choice) }
            val navigateBack = {
                speaker.stop()
                flow.back()
            }

            BackHandler(enabled = flow.screen != Screen.HOME, onBack = navigateBack)
            TogetherWordsApp(
                flow = flow,
                language = languageChoice.resolve(initialLanguage.deviceCode),
                languageChoice = languageChoice,
                voiceMessage = speaker.messageId,
                onLanguage = { selected ->
                    speaker.stop()
                    languageChoice = selected
                    languages.select(selected)
                },
                onBack = navigateBack,
                onSpeak = speaker::speak,
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArrayList(FLOW_KEY, ArrayList(flow.path.map(Screen::name)))
        super.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        speaker.close()
        super.onDestroy()
    }

    private companion object {
        const val FLOW_KEY = "flow"
    }
}
