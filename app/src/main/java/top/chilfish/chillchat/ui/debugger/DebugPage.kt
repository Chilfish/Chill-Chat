package top.chilfish.chillchat.ui.debugger

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import top.chilfish.chillchat.R
import top.chilfish.chillchat.data.contacts.Host
import top.chilfish.chillchat.provider.BaseHost
import top.chilfish.chillchat.ui.components.ChillScaffold
import top.chilfish.chillchat.ui.components.ChillTopBar
import top.chilfish.chillchat.ui.components.TextInput

private val ItemPadding = Modifier.padding(bottom = 16.dp)

@Composable
fun DebugPage(
    viewModel: DebugViewModel = hiltViewModel(),
) {
    ChillScaffold(
        topBar = { ChillTopBar(title = "Debugger Page") }
    ) {
        Column(Modifier.padding(16.dp)) {
            BaseURL(viewModel)

            Button(onClick = { viewModel.find() }) {
                Text("Find")
            }
        }
    }
}

@Composable
private fun BaseURL(
    viewModel: DebugViewModel,
) {
    var url by rememberSaveable { mutableStateOf("") }
    var host by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        BaseHost.collect { host = it }
    }

    Text("Base Host: $host")
    Row(
        modifier = ItemPadding,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextInput(
            modifier = Modifier.width(300.dp),
            value = url,
            onValueChange = { url = it }
        )
        Button(onClick = { viewModel.setHost(url) }) {
            Text(stringResource(R.string.save))
        }
    }
}

