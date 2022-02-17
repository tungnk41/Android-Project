package com.tuhn.auth.LoginScreen.component

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OptionLogin(onGoogleSignIn: (() -> Unit)? = null, onFacebookSignIn: (() -> Unit)? = null,onGithubSignIn: (() -> Unit)? = null) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            Modifier
                .padding(vertical = 50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SignInButton(text = "Google") {
                onGoogleSignIn?.invoke()
            }
            SignInButton(text = "Facebook") {
                onFacebookSignIn?.invoke()
            }
            SignInButton(text = "Github") {
                onGithubSignIn?.invoke()
            }
        }
    }
}

@Preview
@Composable
fun OptionLoginPreview() {
    OptionLogin()
}