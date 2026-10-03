package com.togetherwords

import androidx.compose.runtime.mutableStateListOf

internal enum class Screen {
    HOME,
    HELP,
    WORDS,
    BALLOON,
    ADULTS,
}

internal class AppFlow(initialPath: List<Screen> = listOf(Screen.HOME)) {
    private val stack =
        mutableStateListOf<Screen>().apply {
            addAll(initialPath.ifEmpty { listOf(Screen.HOME) })
        }
    val screen: Screen
        get() = stack.last()

    val path: List<Screen>
        get() = stack.toList()

    fun openPractice() {
        stack.add(Screen.WORDS)
    }

    fun openHelp() {
        stack.add(Screen.HELP)
    }

    fun openWords() {
        stack.add(Screen.WORDS)
    }

    fun openBalloon() {
        stack.add(Screen.BALLOON)
    }

    fun openAdults() {
        stack.add(Screen.ADULTS)
    }

    fun back() {
        if (stack.size > 1) stack.removeAt(stack.lastIndex)
    }
}
