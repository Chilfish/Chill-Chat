package top.chilfish.chillchat.ui.edit_profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import top.chilfish.chillchat.R
import top.chilfish.chillchat.navigation.EditType
import top.chilfish.chillchat.ui.components.IconBtn
import top.chilfish.chillchat.ui.components.appBarColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileBar(
    viewModel: EditViewModel,
    navController: NavHostController,
    type: String,
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.edit_my, Title(type)),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        },
        navigationIcon = {
            IconBtn(
                onClick = { navController.popBackStack() },
                imageVector = Icons.Outlined.ArrowBack
            )
        },
        actions = {
            IconBtn(
                onClick = { viewModel.saveEdit() },
                imageVector = Icons.Outlined.Done
            )
        },
        colors = appBarColors()
    )
}


private val Title = @Composable { type: String ->
    when (type) {
        EditType.Name -> stringResource(R.string.username)
        EditType.Email -> stringResource(R.string.email)
        EditType.Bio -> stringResource(R.string.bio)
        EditType.Avatar -> stringResource(R.string.avatar)
        EditType.Password -> stringResource(R.string.password)
        else -> stringResource(R.string.username)
    }
}