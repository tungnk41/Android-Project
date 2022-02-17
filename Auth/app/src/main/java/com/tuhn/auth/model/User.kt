package com.tuhn.auth.model

import com.google.android.gms.auth.api.credentials.IdToken

data class User(val email: String = "", val displayName: String = "", val accessToken: String? = "", val isExpried: Boolean = false)