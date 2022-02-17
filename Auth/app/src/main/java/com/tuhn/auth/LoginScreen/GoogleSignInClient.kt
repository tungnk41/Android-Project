package com.tuhn.auth

import android.content.Context
import androidx.compose.ui.res.stringResource
import com.example.auth.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn


fun GoogleSignInClient(context: Context): GoogleSignInClient {
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//         Request id token if you intend to verify google user from your backend server
        .requestIdToken(context.getString(R.string.web_client_id))
        .build()

    return GoogleSignIn.getClient(context, signInOptions)
}

