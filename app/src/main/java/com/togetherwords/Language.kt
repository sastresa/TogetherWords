package com.togetherwords

internal enum class LanguageChoice(val savedCode: String?) {
    DEVICE(null),
    ENGLISH("en"),
    SPANISH("es");

    fun resolve(deviceCode: String): AppLanguage =
        when (this) {
            DEVICE -> AppLanguage.fromDevice(deviceCode)
            ENGLISH -> AppLanguage.ENGLISH
            SPANISH -> AppLanguage.SPANISH
        }

    companion object {
        fun fromSavedCode(code: String?): LanguageChoice =
            entries.firstOrNull { it.savedCode != null && it.savedCode == code } ?: DEVICE
    }
}

internal enum class AppLanguage(val code: String) {
    ENGLISH("en"),
    SPANISH("es");

    companion object {
        fun fromDevice(deviceCode: String): AppLanguage =
            if (deviceCode == SPANISH.code) SPANISH else ENGLISH
    }
}
