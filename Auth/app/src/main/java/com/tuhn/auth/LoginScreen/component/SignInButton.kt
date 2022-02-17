package com.tuhn.auth.LoginScreen.component

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SignInButton(text: String,onClicked: ()->Unit) {
    Button(onClick = { onClicked() }) {
        Text(text = text)
    }
}