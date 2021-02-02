package br.com.danielramos.projetosmi.model.repository

import android.util.Log
import br.com.danielramos.projetosmi.contracts.ServiceAuthContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlin.math.log

class ServiceAuth(
    private val auth: FirebaseAuth
) : ServiceAuthContract {

    override fun register(
        email: String,
        password: String,
        success: (FirebaseUser?) -> Unit,
        error: (Exception?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FIREBASE:", "User criado com sucesso")
                    success(auth.currentUser)
                } else {
                    Log.w(
                        "FIREBASE:",
                        "Erro ao criar o usuário!",
                        task.exception)
                    error(task.exception)
                }
            }
    }

    override fun signIn(
        email: String,
        password: String,
        success: (FirebaseUser?) -> Unit,
        error: (Exception?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("FIREBASE:", "Usuário logado com sucesso!")
                    success(auth.currentUser)
                } else {
                    Log.w(
                        "FIREBASE:",
                        "Erro de login!",
                        task.exception)
                    error(task.exception)
                }
            }
    }

    override fun signOut() {
        auth.signOut()
    }

}