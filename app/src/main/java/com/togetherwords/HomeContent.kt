package com.togetherwords

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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

@Composable
internal fun HomeScreen(
    strings: StringResolver,
    onPractice: () -> Unit,
    onHelp: () -> Unit,
) {
    HomeHero(strings)
    Text(
        text = strings(R.string.home_title),
        color = Ink,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        fontWeight = FontWeight.SemiBold,
    )
    HomeActionCard(
        title = strings(R.string.practice),
        hint = strings(R.string.practice_hint),
        color = Mint,
        icon = R.drawable.ic_speech,
        tag = "practice",
        onClick = onPractice,
    )
    HomeActionCard(
        title = strings(R.string.help_now),
        hint = strings(R.string.help_now_hint),
        color = Peach,
        icon = R.drawable.ic_pause,
        tag = "help_now",
        onClick = onHelp,
    )
}

@Composable
private fun HomeHero(strings: StringResolver) {
    Column(Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 8.dp).testTag("home_hero")) {
        Box(
            Modifier.width(42.dp)
                .height(6.dp)
                .background(Color(0xFFEACCB8), RoundedCornerShape(3.dp))
        )
        Spacer(Modifier.height(14.dp))
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(
                    strings(R.string.home_reassurance_first),
                    color = Ink,
                    fontSize = 29.sp,
                    lineHeight = 34.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    strings(R.string.home_reassurance_second),
                    color = Teal,
                    fontSize = 31.sp,
                    lineHeight = 36.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            Image(
                painterResource(R.drawable.ic_home_balloon),
                contentDescription = null,
                modifier = Modifier.width(118.dp).height(126.dp),
            )
        }
        Spacer(Modifier.height(10.dp))
        Text(
            strings(R.string.home_reassurance_detail),
            color = Ink,
            fontSize = 17.sp,
            lineHeight = 24.sp,
        )
    }
}

@Composable
private fun HomeActionCard(
    title: String,
    hint: String,
    color: Color,
    @DrawableRes icon: Int,
    tag: String,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().heightIn(min = 112.dp).testTag(tag),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(26.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                Modifier.size(50.dp).background(Color.White.copy(alpha = 0.48f), CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.size(34.dp),
                )
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(
                    title,
                    fontSize = 21.sp,
                    lineHeight = 27.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Ink,
                )
                Spacer(Modifier.height(4.dp))
                Text(hint, fontSize = 15.sp, lineHeight = 20.sp, color = Ink)
            }
        }
    }
}
