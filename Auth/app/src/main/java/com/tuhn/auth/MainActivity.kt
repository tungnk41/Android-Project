package com.tuhn.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.auth.AuthScreen.component.LoginScreen
import com.google.firebase.FirebaseApp
import com.tuhn.auth.ViewModel.LoginViewModel
import com.tuhn.auth.ui.theme.AuthTheme


class MainActivity : ComponentActivity() {
    val loginViewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthTheme {
                LoginScreen(this, loginViewModel)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loginViewModel.onActivityResultForFaceBook(
            requestCode = requestCode,
            resultCode = resultCode,
            data = data
        )
        super.onActivityResult(requestCode, resultCode, data)
    }
}
