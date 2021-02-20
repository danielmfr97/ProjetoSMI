package br.com.danielramos.projetosmi.presenter

import android.widget.Toast
import br.com.danielramos.projetosmi.contracts.RegisterContract
import br.com.danielramos.projetosmi.model.entities.Users
import br.com.danielramos.projetosmi.utils.ValidatorUtils
import br.com.danielramos.projetosmi.view.activities.MainActivity
import br.com.danielramos.projetosmi.view.fragments.RegisterFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterPresenter(override val view: RegisterFragment) : RegisterContract.RegisterPresenter {

    private val auth: FirebaseAuth

    init {
        auth = FirebaseAuth.getInstance()
    }

    override fun cadastrarUsuario() {
        if (isCamposValidos()) {
            val nome = view.getNome().text.toString()
            val email = view.getEmail().text.toString()
            val senha = view.getSenha().text.toString()
            view.enableDisableProgressbar(true)
        }
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