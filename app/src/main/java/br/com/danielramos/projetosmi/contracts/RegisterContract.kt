package br.com.danielramos.projetosmi.contracts

import android.widget.EditText
import br.com.danielramos.projetosmi.view.fragments.RegisterFragment
import com.google.android.material.textfield.TextInputEditText

interface RegisterContract {

    interface RegisterView {
        fun registrarUsuario()
        fun getNome(): EditText
        fun getEmail(): EditText
        fun getSenha(): EditText
        fun enableDisableProgressbar(flag: Boolean)
    }

    interface RegisterPresenter {
        fun cadastrarUsuario()
        fun isCamposValidos(): Boolean

        val view: RegisterFragment
    }
}