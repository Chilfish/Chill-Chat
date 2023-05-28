package top.chilfish.chillchat.ui.debugger

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import top.chilfish.chillchat.provider.BaseHost
import top.chilfish.chillchat.provider.curCid
import top.chilfish.chillchat.ui.components.ChillScaffold
import top.chilfish.chillchat.ui.components.ChillTopBar
import top.chilfish.chillchat.ui.components.PickFile
import top.chilfish.chillchat.ui.components.TextInput


private val ItemPadding = Modifier.padding(bottom = 12.dp)

@Composable
fun DebugPage(
    viewModel: DebugViewModel = hiltViewModel(),
) {
    var host by rememberSaveable { mutableStateOf(BaseHost.value) }
    LaunchedEffect(Unit) { BaseHost.collect { host = it } }

    ChillScaffold(
        topBar = { ChillTopBar(title = "Debugger Page") }
    ) {
        Column(Modifier.padding(16.dp)) {
            Setter(
                title = "SetHost",
                btnText = "Save",
                input = host,
                onBtnClick = { viewModel.setHost(it) }
            )

            Setter(
                title = "Set Cid",
                btnText = "Save",
                input = curCid,
                onBtnClick = { viewModel.setCid(it) }
            )

            Setter(
                title = "Find User",
                btnText = "Find",
                onBtnClick = { viewModel.find(it) }
            )

            Row {
                Button(onClick = { viewModel.loadContacts() }) {
                    Text("Load Contacts")
                }
                Button(onClick = { viewModel.login() }) {
                    Text("Login")
                }

                PickFile(pickedFile = { viewModel.uploadImg(it) }) {
                    Button(onClick = { it() }) {
                        Text("Pick Photo")
                    }
                }
            }
        }
    }
}


@Composable
private fun Setter(
    title: String = "",
    input: String = "",
    btnText: String = "",
    onBtnClick: (String) -> Unit = {},
) {
    var text by rememberSaveable { mutableStateOf(input) }

    Text("$title: $text")
    Row(
        modifier = ItemPadding,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextInput(
            modifier = Modifier.width(300.dp),
            value = text,
            onValueChange = { text = it }
        )
        Button(onClick = { onBtnClick(text) }) {
            Text(btnText)
        }
    }
}

