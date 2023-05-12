package top.chilfish.chillchat.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import top.chilfish.chillchat.R

private val MaxWidth = 300.dp

@Composable
fun MessageItem(
    modifier: Modifier = Modifier,
    message: String,
    time: String,
    isMe: Boolean = true,
    isShowTime: Boolean = false,
) {
    if (isShowTime) TimeBubble(time)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start,
    ) {
        Row(
            modifier = Modifier
                .widthIn(max = MaxWidth)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    color =
                    if (isMe) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.surface
                ),
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(12.dp),
            )
        }
    }
}

@Composable
fun TimeBubble(
    time: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    MaterialTheme.colorScheme.surfaceVariant
                        .copy(alpha = 0.4f)
                )
                .padding(6.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessagePreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        MessageItem(
            message = stringResource(R.string.place_long_content),
            time = "10-11 12:00",
            isShowTime = true,
        )
    }
}