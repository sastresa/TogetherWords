package com.togetherwords

import android.animation.ValueAnimator
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun BalloonActivity(strings: StringResolver) {
    var grown by remember { mutableStateOf(false) }
    val expansion by
        animateFloatAsState(
            targetValue = if (grown) 1f else 0f,
            animationSpec =
                tween(
                    durationMillis = if (ValueAnimator.areAnimatorsEnabled()) 1800 else 0,
                    easing = FastOutSlowInEasing,
                ),
            label = "balloon expansion",
        )

    Heading(strings(R.string.balloon_title), strings(R.string.balloon_hint))
    Box(
        Modifier.fillMaxWidth()
            .height(430.dp)
            .testTag("balloon")
            .semantics {
                contentDescription =
                    strings(if (grown) R.string.balloon_rest else R.string.balloon_grow)
            }
            .clickable { grown = !grown }
    ) {
        Canvas(Modifier.fillMaxSize()) {
            val bodyWidth = size.width * (0.74f + 0.20f * expansion)
            val bodyHeight = bodyWidth * 1.04f
            val centerX = size.width / 2f
            val centerY = size.height * 0.43f
            val left = centerX - bodyWidth / 2f
            val right = centerX + bodyWidth / 2f
            val top = centerY - bodyHeight / 2f
            val bottom = centerY + bodyHeight / 2f
            val stroke = 3.dp.toPx()

            val breeze =
                Path().apply {
                    moveTo(2.dp.toPx(), centerY - 40.dp.toPx())
                    cubicTo(
                        left * 0.35f,
                        centerY - 52.dp.toPx(),
                        left * 0.6f,
                        centerY - 24.dp.toPx(),
                        left + 6.dp.toPx(),
                        centerY - 30.dp.toPx(),
                    )
                    moveTo(0f, centerY + 14.dp.toPx())
                    cubicTo(
                        left * 0.3f,
                        centerY - 2.dp.toPx(),
                        left * 0.65f,
                        centerY + 26.dp.toPx(),
                        left + 8.dp.toPx(),
                        centerY + 11.dp.toPx(),
                    )
                }
            drawPath(
                breeze,
                color = Color(0xFF9FCFC8).copy(alpha = 0.65f * (1f - expansion)),
                style = Stroke(width = stroke, cap = StrokeCap.Round),
            )

            val body =
                Path().apply {
                    moveTo(centerX, top)
                    cubicTo(
                        centerX + bodyWidth * 0.4f,
                        top,
                        right,
                        top + bodyHeight * 0.18f,
                        right,
                        top + bodyHeight * 0.48f,
                    )
                    cubicTo(
                        right,
                        top + bodyHeight * 0.78f,
                        centerX + bodyWidth * 0.2f,
                        bottom - bodyHeight * 0.03f,
                        centerX,
                        bottom,
                    )
                    cubicTo(
                        centerX - bodyWidth * 0.2f,
                        bottom - bodyHeight * 0.03f,
                        left,
                        top + bodyHeight * 0.78f,
                        left,
                        top + bodyHeight * 0.48f,
                    )
                    cubicTo(
                        left,
                        top + bodyHeight * 0.18f,
                        centerX - bodyWidth * 0.4f,
                        top,
                        centerX,
                        top,
                    )
                    close()
                }
            drawPath(
                body,
                brush =
                    Brush.linearGradient(
                        colors = listOf(Color(0xFFD2EEE8), Color(0xFFA6D6D0), Color(0xFF80C0BA)),
                        start = Offset(left, top),
                        end = Offset(right, bottom),
                    ),
            )
            rotate(-28f, pivot = Offset(left + bodyWidth * 0.32f, top + bodyHeight * 0.2f)) {
                drawOval(
                    Color.White.copy(alpha = 0.36f),
                    topLeft = Offset(left + bodyWidth * 0.18f, top + bodyHeight * 0.16f),
                    size = Size(bodyWidth * 0.3f, bodyHeight * 0.105f),
                )
            }
            val knot =
                Path().apply {
                    moveTo(centerX, bottom - 3.dp.toPx())
                    lineTo(centerX - 12.dp.toPx(), bottom + 18.dp.toPx())
                    lineTo(centerX + 12.dp.toPx(), bottom + 18.dp.toPx())
                    close()
                }
            drawPath(knot, color = Color(0xFF75AFA9))
            val string =
                Path().apply {
                    moveTo(centerX, bottom + 18.dp.toPx())
                    cubicTo(
                        centerX + 19.dp.toPx(),
                        bottom + 47.dp.toPx(),
                        centerX - 18.dp.toPx(),
                        size.height - 38.dp.toPx(),
                        centerX,
                        size.height - 10.dp.toPx(),
                    )
                }
            drawPath(
                string,
                color = Color(0xFF75AFA9),
                style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round),
            )
        }
    }
    Text(
        strings(if (grown) R.string.balloon_rest else R.string.balloon_grow),
        modifier = Modifier.fillMaxWidth(),
        color = Teal,
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
    )
    Text(
        strings(R.string.balloon_exit_hint),
        modifier = Modifier.fillMaxWidth(),
        color = Ink,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
    )
}
