package com.tuhn.auth.LoginScreen.component

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EmailLogin(onLoginClicked: (String,String)->Unit) {

    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(value = email, maxLines = 1,
            placeholder = {
                Text(text = "Email")
            },
            onValueChange = {
                email = it
            })
        Spacer(modifier = Modifier.height(20.dp))
        TextField(value = password, maxLines = 1,
            placeholder = {
                Text(text = "Password")
            },
            onValueChange = {
                password = it
            })
    }
    Spacer(modifier = Modifier.height(20.dp))
    SignInButton(text = "Login") {
        onLoginClicked(email,password)
    }
}

@Preview(showSystemUi = true)
@Composable
fun EmailPasswordLoginPreview() {
    EmailLogin(onLoginClicked = {email,password->

    })
}