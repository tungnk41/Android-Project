package com.tuhn.auth.ViewModel

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuhn.auth.model.User
import java.util.*

class LoginViewModel : ViewModel() {
    private var auth: FirebaseAuth

    sealed class LoginState {
        object Initial : LoginState()
        object Processing : LoginState()
        data class Success(val loginResult: LoginResult) : LoginState()
        data class Error(val exception: FacebookException) : LoginState()
    }

    private var callbackManager = CallbackManager.Factory.create()
    var state by mutableStateOf<LoginState>(LoginState.Initial)
        private set

    init {
        auth = Firebase.auth
        initLoginFacebook()
    }

    fun saveUser(user: User) {
        Log.d("TAG", "saveUser: " + user.displayName + " ," + user.email + " ," + user.accessToken)
    }

    private fun initLoginFacebook() {
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    firebaseAuthWithFacebook(loginResult.accessToken.token)
                    state = LoginState.Success(loginResult)
                }

                override fun onCancel() {
                    state = LoginState.Initial
                }

                override fun onError(exception: FacebookException) {
                    state = LoginState.Error(exception)
                }
            }
        )
    }

    fun loginFacebook(context: Context) {
        state = LoginState.Processing
        LoginManager.getInstance()
            .logInWithReadPermissions(context as Activity, Arrays.asList("public_profile"));
    }

    fun handleLoginGoogle(account: GoogleSignInAccount?) {
        try {
            if (account == null) {
                Log.d("TAG", "AuthScreen: " + "SignIn Failed")
            } else {
                Log.d("TAG", "AuthScreen: " + "SignIn Success")
                firebaseAuthWithGoogle(account.idToken)
            }
        } catch (e: ApiException) {
            Log.d("TAG", "AuthScreen: " + "Google sign in failed")
        }
    }

    fun loginEmail(email: String, password: String) {
        if (email != "" && password != "") {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                    } else {
                        Log.d(TAG, "signInWithEmail:failed")
                    }
                }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    auth.currentUser?.let { userAuthed ->
                        saveUser(
                            User(
                                email = userAuthed.email ?: "",
                                displayName = userAuthed.displayName ?: "",
                                accessToken = idToken
                            )
                        )
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun firebaseAuthWithFacebook(idToken: String) {
        val credential = FacebookAuthProvider.getCredential(idToken)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    auth.currentUser?.let { userAuthed ->
                        saveUser(
                            User(
                                email = userAuthed.email ?: "",
                                displayName = userAuthed.displayName ?: "",
                                accessToken = idToken
                            )
                        )
                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        LoginManager.getInstance().unregisterCallback(callbackManager)
    }

    fun onActivityResultForFaceBook(requestCode: Int, resultCode: Int, data: Intent?): Boolean =
        callbackManager.onActivityResult(requestCode, resultCode, data)
}