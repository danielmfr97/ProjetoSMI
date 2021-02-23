package br.com.danielramos.projetosmi.presenter

import android.util.Log
import android.widget.Toast
import androidx.navigation.findNavController
import br.com.danielramos.projetosmi.contracts.RegisterContract
import br.com.danielramos.projetosmi.model.entities.Users
import br.com.danielramos.projetosmi.utils.ValidatorUtils
import br.com.danielramos.projetosmi.view.activities.MainActivity
import br.com.danielramos.projetosmi.view.fragments.RegisterFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegisterPresenter(override val view: RegisterFragment) : RegisterContract.RegisterPresenter {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val rootDatabase = FirebaseDatabase.getInstance().reference

    override fun cadastrarUsuario() {
        if (isCamposValidos()) {
            val nome = view.getNome().text.toString()
            val email = view.getEmail().text.toString()
            val senha = view.getSenha().text.toString()
            view.enableDisableProgressbar(true)
            auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(MainActivity()) { task ->
                    if (task.isSuccessful) {
                        MainActivity.instance!!.openToastShort("User created.")
                        salvarDadosUsuario(nome, email)
                        view.enableDisableProgressbar(false)
                        onBack()
                    } else {
                        MainActivity.instance!!.openToastShort("Failed to create user.")
                        view.enableDisableProgressbar(false)
                        Log.e("EEROR", task.exception!!.message.toString())
                    }
                }
        }
    }

    private fun salvarDadosUsuario(nome: String, email: String) {
        rootDatabase.child(auth.currentUser!!.uid)
            .child("nome").setValue(nome)
        rootDatabase.child(auth.currentUser!!.uid)
            .child("email").setValue(email)
    }

    private fun onBack() {
        MainActivity.instance!!.onBackPressed()
    }

    override fun isCamposValidos(): Boolean {
        if (!ValidatorUtils.isValidName(
                view.getNome(),
                true
            ) && !ValidatorUtils.isValidEmail(
                view.getEmail(),
                true
            ) && !ValidatorUtils.isValidPassword(view.getSenha(), true)
        )
            return false
        return true
    }
}