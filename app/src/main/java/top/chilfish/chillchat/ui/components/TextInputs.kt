package top.chilfish.chillchat.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    label: String = "",
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorText: String = "",
) {
    val (passwordHidden, setPasswordHidden) = rememberSaveable { mutableStateOf(true) }

    val (text, setText) = rememberSaveable { mutableStateOf(value) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            modifier = modifier.width(300.dp),
            value = text,
            onValueChange = {
                setText(it)
                onValueChange(it)
            },
            label = { Text(text = label) },
            isError = isError,
            singleLine = true,

            visualTransformation = if (isPassword && passwordHidden)
                PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPassword) VisibilityBtn(passwordHidden, setPasswordHidden)
            },

            placeholder = { Text(text = placeholder) },
            supportingText = {
                if (isError) Text(
                    text = "* $errorText",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        )
    }
}