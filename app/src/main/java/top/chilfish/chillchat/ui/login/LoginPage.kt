package top.chilfish.chillchat.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import top.chilfish.chillchat.R
import top.chilfish.chillchat.navigation.Routers
import top.chilfish.chillchat.ui.components.TextInput

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    navController: NavHostController,
) {
    val loginState = viewModel.loginState.collectAsState().value

    Column(
        modifier
            .fillMaxSize()
            .padding(16.dp, top = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .width(150.dp)
                .padding(12.dp)
                .clip(RoundedCornerShape(12.dp))
                .clickable { navController.navigate(Routers.Debug) },
            contentDescription = stringResource(R.string.app_name),
            painter = painterResource(R.drawable.logo)
        )
        Title()
        LoginInputs(
            modifier = modifier.padding(0.dp, 20.dp),
            viewModel = viewModel,
            loginState = loginState
        )
        ButtonBar(viewModel = viewModel)
    }
}

@Composable
private fun Title() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.login_title),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(R.string.login_prompt),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(0.dp, 10.dp)
        )
    }
}

@Composable
fun LoginInputs(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    loginState: LoginState
) {
    Column(modifier = modifier) {
        TextInput(
            label = stringResource(R.string.username),
            value = loginState.username,
            onValueChange = {
                viewModel.checkUsername(it)
            },
            placeholder = stringResource(R.string.username_hint),
            isError = loginState.isUsernameError,
            errorText = stringResource(R.string.username_invalid)
        )
        TextInput(
            label = stringResource(R.string.password),
            value = loginState.password,
            onValueChange = {
                viewModel.checkPassword(it)
            },
            placeholder = stringResource(R.string.password_hint),
            isPassword = true,
            isError = loginState.isPasswordError,
            errorText = stringResource(R.string.password_invalid)
        )
    }
}

@Composable
fun ButtonBar(
    viewModel: LoginViewModel
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Btn(
            text = stringResource(R.string.register),
            onClick = { viewModel.goToRegister() }
        )
        Btn(
            text = stringResource(R.string.login),
            onClick = { viewModel.goToLogin() }
        )
    }
}

@Composable
private fun Btn(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier
            .padding(16.dp)
            .width(110.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
    ) {
        Text(text = text, color = MaterialTheme.colorScheme.onPrimary)
    }
}