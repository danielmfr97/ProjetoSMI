package br.com.danielramos.projetosmi.presenter

import androidx.navigation.fragment.findNavController
import br.com.danielramos.projetosmi.R
import br.com.danielramos.projetosmi.view.activities.MainActivity
import br.com.danielramos.projetosmi.view.fragments.LoginFragment
import com.google.firebase.auth.FirebaseAuth

class LoginPresenter(private val view: LoginFragment) {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun isCredentialsValid(): Boolean {
        return view.getEmail().isNotBlank() && view.getSenha().isNotBlank()
    }

    fun logarUsuario() {
        auth.signInWithEmailAndPassword(view.getEmail(), view.getSenha())
            .addOnCompleteListener(MainActivity()) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    MainActivity.instance!!.openToastShort("Authenticaton Success $user.")
                    view.findNavController().navigate(R.id.action_loginfragment_to_dashboard)
                } else {
                    MainActivity.instance!!.openToastShort("Authenticaton failed.")
                }
            }
    }

}