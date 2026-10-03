package com.togetherwords

import androidx.compose.runtime.Composable

@Composable
internal fun HelpScreen(
    strings: StringResolver,
    onWords: () -> Unit,
    onBalloon: () -> Unit,
) {
    Heading(strings(R.string.help_title), strings(R.string.help_hint))
    ChoiceCard(
        title = strings(R.string.say_something),
        color = Mint,
        tag = "say_something",
        iconRes = R.drawable.ic_speech,
        onClick = onWords,
    )
    ChoiceCard(
        title = strings(R.string.calm_down),
        color = Blue,
        tag = "calm_down",
        iconRes = R.drawable.ic_pause,
        onClick = onBalloon,
    )
}
