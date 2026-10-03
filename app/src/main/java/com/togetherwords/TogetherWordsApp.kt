package com.togetherwords

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

internal typealias StringResolver = (Int) -> String

@Composable
internal fun TogetherWordsApp(
    flow: AppFlow,
    language: AppLanguage,
    languageChoice: LanguageChoice,
    voiceMessage: Int?,
    onLanguage: (LanguageChoice) -> Unit,
    onBack: () -> Unit,
    onSpeak: (String, AppLanguage) -> Unit,
) {
    val context = LocalContext.current
    val systemConfiguration = LocalConfiguration.current
    val localizedContext =
        remember(context, language, systemConfiguration) {
            val configuration =
                Configuration(systemConfiguration).apply {
                    setLocale(Locale.forLanguageTag(language.code))
                }
            context.createConfigurationContext(configuration)
        }
    val strings: StringResolver = localizedContext::getString

    MaterialTheme(colorScheme = AppColorScheme) {
        Box(Modifier.fillMaxSize().background(Background)) {
            Image(
                painter = painterResource(R.drawable.app_background),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize(),
            )
            key(flow.screen) {
                Column(
                    modifier =
                        Modifier.fillMaxSize()
                            .safeDrawingPadding()
                            .verticalScroll(rememberScrollState())
                            .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    AppHeader(flow.screen, strings, onBack, flow::openAdults)
                    when (flow.screen) {
                        Screen.HOME -> HomeScreen(strings, flow::openPractice, flow::openHelp)
                        Screen.HELP -> HelpScreen(strings, flow::openWords, flow::openBalloon)
                        Screen.WORDS -> WordsScreen(strings, language, voiceMessage, onSpeak)
                        Screen.BALLOON -> BalloonActivity(strings)
                        Screen.ADULTS -> AdultGuide(strings, languageChoice, onLanguage)
                    }
                    Spacer(Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun AppHeader(
    screen: Screen,
    strings: StringResolver,
    onBack: () -> Unit,
    onAdults: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (screen == Screen.HOME) {
            Text(
                text = strings(R.string.app_name),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
            )
        } else {
            TextButton(
                onClick = onBack,
                modifier = Modifier.heightIn(min = 56.dp).testTag("back"),
                contentPadding = PaddingValues(start = 0.dp, end = 16.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp),
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = strings(R.string.back),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
        if (screen == Screen.HOME) {
            TextButton(
                onClick = onAdults,
                modifier = Modifier.height(52.dp).testTag("adults"),
            ) {
                Text(
                    text = strings(R.string.adults_entry),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}
