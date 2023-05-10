package top.chilfish.chillchat.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import top.chilfish.chillchat.R

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    Scaffold { padding ->
        Button(
            modifier = modifier.padding(padding),
            onClick = { viewModel.logout() }) {
            Text(stringResource(R.string.logout))
        }
    }
}