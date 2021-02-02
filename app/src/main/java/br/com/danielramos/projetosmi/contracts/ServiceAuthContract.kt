package br.com.danielramos.projetosmi.contracts

import com.google.firebase.auth.FirebaseUser

interface ServiceAuthContract {
    fun signIn(
        email: String,
        password: String,
        success: (FirebaseUser?) -> Unit,
        error: (Exception?) -> Unit
    )

    fun register(
        email: String,
        password: String,
        success: (FirebaseUser?) -> Unit,
        error: (Exception?) -> Unit
    )

    fun signOut()
}