package top.chilfish.chillchat.ui.message

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import top.chilfish.chillchat.ui.components.IconBtn

private val MaxHeight = 150.dp

@Preview(showBackground = true)
@Composable
fun UserInputPreview() {
    UserInput()
}

@Composable
fun UserInput(
    modifier: Modifier = Modifier,
    onSend: (String) -> Unit = {},
    onMood: () -> Unit = {},
    resetScroll: () -> Unit = {},
) {

    val (mes, setMes) = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    BasicTextField(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = MaxHeight)
            .background(MaterialTheme.colorScheme.surface)
            .onFocusChanged { state ->
                if (state.isFocused) {
                    resetScroll()
                }
            },
        value = mes,
        onValueChange = setMes,
        maxLines = 8,
        singleLine = false,
        textStyle = MaterialTheme.typography.bodyLarge,
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconBtn(
                    onClick = onMood,
                    imageVector = Icons.Default.Face,
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.CenterStart,
                    content = { innerTextField() },
                )
                IconBtn(
                    onClick = {
                        onSend(mes.text)
                        setMes(TextFieldValue())
                    },
                    imageVector = Icons.Default.Send,
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Send
        ),
    )
}
