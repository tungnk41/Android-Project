package com.example.auth.AuthScreen.component

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.facebook.login.LoginManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuhn.auth.GoogleSignInClient
import com.tuhn.auth.LoginScreen.GoogleAuthResultContract
import com.tuhn.auth.LoginScreen.component.EmailLogin
import com.tuhn.auth.LoginScreen.component.OptionLogin
import com.tuhn.auth.ViewModel.LoginViewModel

@Composable
fun LoginScreen(context: Context? = null, loginViewModel: LoginViewModel) {

    val googleAuthResultLauncher = rememberLauncherForActivityResult(
        contract = GoogleAuthResultContract(),
        onResult = { task ->
            loginViewModel.handleLoginGoogle(task?.getResult())
        })

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        EmailLogin(onLoginClicked = { email, pasword ->
            loginViewModel.loginEmail(email,pasword)
        })
        OptionLogin(
            onGoogleSignIn = {
                googleAuthResultLauncher.launch(1)
            },
            onFacebookSignIn = {
                context?.let {
                    loginViewModel.loginFacebook(context)
                }
            },
            onGithubSignIn = {

            }
        )
        Button(onClick = {
            context?.let {
                GoogleSignInClient(context).signOut()
                LoginManager.getInstance().logOut()
                Firebase.auth.signOut()
            }
        }) {
            Text(text = "Logout")
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(context = null, loginViewModel = LoginViewModel())
}