package com.togetherwords

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale

internal class PhraseSpeaker(context: Context) {
    private val mainHandler = Handler(Looper.getMainLooper())
    private var engine: TextToSpeech? = null
    private var ready = false
    private var failed = false
    private var closed = false

    @get:StringRes
    var messageId by mutableStateOf<Int?>(null)
        private set

    init {
        engine =
            TextToSpeech(context.applicationContext) { status ->
                mainHandler.post {
                    if (closed) return@post
                    ready = status == TextToSpeech.SUCCESS
                    failed = !ready
                    if (failed) messageId = R.string.voice_unavailable
                }
            }
    }

    fun speak(phrase: String, language: AppLanguage) {
        val voice = engine
        if (!ready || voice == null) {
            messageId =
                if (failed || voice == null) R.string.voice_unavailable
                else R.string.voice_preparing
            return
        }
        val availability = voice.setLanguage(Locale.forLanguageTag(language.code))
        if (
            availability == TextToSpeech.LANG_MISSING_DATA ||
                availability == TextToSpeech.LANG_NOT_SUPPORTED
        ) {
            messageId = R.string.voice_unavailable
            return
        }
        messageId =
            if (
                voice.speak(phrase, TextToSpeech.QUEUE_FLUSH, null, "phrase") == TextToSpeech.ERROR
            ) {
                R.string.voice_unavailable
            } else null
    }

    fun stop() {
        engine?.stop()
        messageId = null
    }

    fun close() {
        closed = true
        engine?.stop()
        engine?.shutdown()
        engine = null
    }
}
