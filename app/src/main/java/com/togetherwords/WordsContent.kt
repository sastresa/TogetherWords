package com.togetherwords

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class PhraseStyle(
    @StringRes val textId: Int,
    val tag: String,
    val color: Color,
    @DrawableRes val icon: Int,
)

private val phrases =
    listOf(
        PhraseStyle(R.string.phrase_no, "phrase_no", Mint, R.drawable.ic_word_no),
        PhraseStyle(R.string.phrase_stop, "phrase_stop", Blue, R.drawable.ic_word_stop),
        PhraseStyle(R.string.phrase_help, "phrase_help", Peach, R.drawable.ic_word_help),
        PhraseStyle(R.string.phrase_break, "phrase_break", Lavender, R.drawable.ic_word_break),
    )

@Composable
internal fun WordsScreen(
    strings: StringResolver,
    language: AppLanguage,
    voiceMessage: Int?,
    onSpeak: (String, AppLanguage) -> Unit,
) {
    Heading(strings(R.string.words_title), strings(R.string.words_hint))
    phrases.forEach { style ->
        val phrase = strings(style.textId)
        PhraseCard(phrase, style.color, style.icon, style.tag) {
            onSpeak(phrase, language)
        }
    }
    if (voiceMessage != null) {
        Text(strings(voiceMessage), fontSize = 16.sp, color = Ink)
    }
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = strings(R.string.play_with_toys_title),
            color = Teal,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = strings(R.string.play_with_toys_body),
            color = Ink,
            fontSize = 16.sp,
            lineHeight = 23.sp,
        )
    }
}

@Composable
private fun PhraseCard(
    phrase: String,
    color: Color,
    @DrawableRes icon: Int,
    tag: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().testTag(tag),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(24.dp),
    ) {
        Row(
            Modifier.fillMaxWidth()
                .heightIn(min = 104.dp)
                .padding(horizontal = 22.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                phrase,
                modifier = Modifier.weight(1f),
                color = Ink,
                fontSize = 23.sp,
                lineHeight = 29.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Spacer(Modifier.width(16.dp))
            Image(painterResource(icon), contentDescription = null, modifier = Modifier.size(36.dp))
        }
    }
}
