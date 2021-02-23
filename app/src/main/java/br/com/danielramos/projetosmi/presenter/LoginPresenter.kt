package br.com.danielramos.projetosmi.presenter

import br.com.danielramos.projetosmi.view.activities.MainActivity
import br.com.danielramos.projetosmi.view.fragments.LoginFragment
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter(private val view: LoginFragment) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()


    fun isCredentialsValid(): Boolean {
        return view.getEmail().isNotBlank() && view.getSenha().isNotBlank()
    }

    private fun signWithEmailAndPassword(): Boolean {
        var success = false
        auth.signInWithEmailAndPassword(view.getEmail(), view.getSenha())
            .addOnCompleteListener(MainActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    MainActivity.instance!!.openToastShort("Authenticaton Success $user.")
                    success = true
                } else {
                    MainActivity.instance!!.openToastShort("Authenticaton failed.")
                    success = false
                }
            }
        return success
    }

    fun logarUsuario(): Boolean {
        return isCredentialsValid() && signWithEmailAndPassword()
    }

}