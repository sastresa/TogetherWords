package com.togetherwords

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun Heading(title: String, hint: String?) {
    Text(title, fontSize = 30.sp, lineHeight = 36.sp, fontWeight = FontWeight.SemiBold, color = Ink)
    if (hint != null) Text(hint, fontSize = 18.sp, lineHeight = 25.sp, color = Ink)
}

@Composable
internal fun ChoiceCard(
    title: String,
    color: Color,
    tag: String,
    @DrawableRes iconRes: Int,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().heightIn(min = 94.dp).testTag(tag),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(24.dp),
    ) {
        Column(
            Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painterResource(iconRes),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
            )
            Spacer(Modifier.height(10.dp))
            Text(
                title,
                fontSize = 23.sp,
                lineHeight = 29.sp,
                fontWeight = FontWeight.SemiBold,
                color = Ink,
            )
        }
    }
}
