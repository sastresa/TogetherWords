package com.togetherwords

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import androidx.core.content.edit
import java.util.Locale

internal data class LanguageSelection(val deviceCode: String, val choice: LanguageChoice)

internal class LanguageSettings(context: Context) {
    private val appContext = context.applicationContext
    private val preferences = appContext.getSharedPreferences("language", Context.MODE_PRIVATE)

    fun read(): LanguageSelection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            readNativeLanguage()
        } else {
            LanguageSelection(
                deviceCode = Locale.getDefault().language,
                choice = LanguageChoice.fromSavedCode(preferences.getString(LANGUAGE_KEY, null)),
            )
        }

    fun select(choice: LanguageChoice) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            appContext.getSystemService(LocaleManager::class.java).applicationLocales =
                if (choice == LanguageChoice.DEVICE) LocaleList.getEmptyLocaleList()
                else LocaleList.forLanguageTags(choice.savedCode)
        } else if (choice == LanguageChoice.DEVICE) {
            preferences.edit { remove(LANGUAGE_KEY) }
        } else {
            preferences.edit { putString(LANGUAGE_KEY, choice.savedCode) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun readNativeLanguage(): LanguageSelection {
        val manager = appContext.getSystemService(LocaleManager::class.java)
        migrateLegacyPreference(manager)
        val deviceCode =
            if (manager.systemLocales.isEmpty) Locale.getDefault().language
            else manager.systemLocales[0].language
        val selectedCode =
            manager.applicationLocales
                .toLanguageTags()
                .substringBefore(',')
                .substringBefore('-')
                .ifEmpty { null }
        return LanguageSelection(deviceCode, LanguageChoice.fromSavedCode(selectedCode))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun migrateLegacyPreference(manager: LocaleManager) {
        if (!preferences.contains(LANGUAGE_KEY)) return
        val oldChoice = LanguageChoice.fromSavedCode(preferences.getString(LANGUAGE_KEY, null))
        if (manager.applicationLocales.isEmpty && oldChoice != LanguageChoice.DEVICE) {
            manager.applicationLocales = LocaleList.forLanguageTags(oldChoice.savedCode)
        }
        preferences.edit { remove(LANGUAGE_KEY) }
    }

    private companion object {
        const val LANGUAGE_KEY = "code"
    }
}
