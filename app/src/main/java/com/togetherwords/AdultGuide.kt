package com.togetherwords

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

private data class GuideStep(@StringRes val title: Int, @StringRes val body: Int, val tag: String)

private val guideSteps =
    listOf(
        GuideStep(R.string.before_title, R.string.before_body, "adult_before"),
        GuideStep(R.string.during_title, R.string.during_body, "adult_during"),
        GuideStep(R.string.after_title, R.string.after_body, "adult_after"),
    )

private val guideSources =
    listOf(
        "NHS" to "https://www.nhs.uk/baby/babys-development/behaviour/temper-tantrums/",
        "CDC" to "https://www.cdc.gov/parenting-toddlers/noticing-and-naming/emotion-coaching.html",
        "UNICEF" to
            "https://www.unicef.org/uruguay/crianza/primeros-anos/como-manejar-las-rabietas-o-berrinches",
        "AAP" to
            "https://www.healthychildren.org/English/family-life/power-of-play/Pages/pretend-play-ways-children-can-exercise-their-imagination.aspx",
    )

@Composable
internal fun AdultGuide(
    strings: StringResolver,
    choice: LanguageChoice,
    onLanguage: (LanguageChoice) -> Unit,
) {
    var languageExpanded by remember { mutableStateOf(false) }
    val selectedLabel =
        when (choice) {
            LanguageChoice.DEVICE -> strings(R.string.device_language)
            LanguageChoice.ENGLISH -> "English"
            LanguageChoice.SPANISH -> "Español"
        }
    val uriHandler = LocalUriHandler.current

    Heading(strings(R.string.adults_title), null)
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                strings(R.string.language_label)
                    .uppercase(
                        Locale.forLanguageTag(if (choice == LanguageChoice.SPANISH) "es" else "en")
                    ),
                fontSize = 12.sp,
                color = Teal,
                fontWeight = FontWeight.SemiBold,
            )
            TextButton(
                onClick = { languageExpanded = !languageExpanded },
                modifier = Modifier.fillMaxWidth().testTag("language_selector"),
            ) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        selectedLabel,
                        color = Ink,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(if (languageExpanded) "⌃" else "⌄", color = Ink)
                }
            }
            if (languageExpanded) {
                listOf(
                        strings(R.string.device_language) to LanguageChoice.DEVICE,
                        "Español" to LanguageChoice.SPANISH,
                        "English" to LanguageChoice.ENGLISH,
                    )
                    .forEach { (label, option) ->
                        LanguageButton(label, option, choice) {
                            onLanguage(it)
                            languageExpanded = false
                        }
                    }
            }
        }
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Mint),
        shape = RoundedCornerShape(22.dp),
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                strings(R.string.about_tantrums_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
            )
            Text(strings(R.string.about_tantrums), fontSize = 16.sp, lineHeight = 23.sp)
        }
    }

    Text(
        strings(R.string.practical_steps_title),
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
    )
    Text(strings(R.string.practical_steps_intro), fontSize = 16.sp, lineHeight = 23.sp)
    guideSteps.forEach { step ->
        Card(
            modifier = Modifier.fillMaxWidth().testTag(step.tag),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    strings(step.title).uppercase(),
                    color = Teal,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(strings(step.body), fontSize = 16.sp, lineHeight = 23.sp)
            }
        }
    }

    Text(strings(R.string.how_app_helps_title), fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
    Text(strings(R.string.how_app_helps), fontSize = 16.sp, lineHeight = 23.sp)

    Text(strings(R.string.concerned_title), fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
    Text(strings(R.string.concerned_body), fontSize = 16.sp, lineHeight = 23.sp)
    Text(
        strings(R.string.sources_title),
        fontSize = 14.sp,
        color = Teal,
        fontWeight = FontWeight.SemiBold,
    )
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        guideSources.forEach { (label, url) ->
            TextButton(onClick = { uriHandler.openUri(url) }, modifier = Modifier.height(48.dp)) {
                Text(label, color = Teal, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun LanguageButton(
    label: String,
    option: LanguageChoice,
    current: LanguageChoice,
    onSelect: (LanguageChoice) -> Unit,
) {
    Button(
        onClick = { onSelect(option) },
        modifier =
            Modifier.fillMaxWidth()
                .height(52.dp)
                .testTag("language_${option.savedCode ?: "device"}"),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = if (option == current) Ink else Color(0xFFE8ECE8),
                contentColor = if (option == current) Color.White else Ink,
            ),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
    ) {
        Text(label, fontSize = 14.sp)
    }
}
