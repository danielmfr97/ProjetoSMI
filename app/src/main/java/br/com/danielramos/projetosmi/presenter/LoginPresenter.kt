package br.com.danielramos.projetosmi.presenter

import br.com.danielramos.projetosmi.view.fragments.LoginFragment

class LoginPresenter(private val view: LoginFragment) {

    fun isCredentialsValid(): Boolean {
        return view.getEmail().isNullOrBlank() && view.getEmail().isNullOrBlank()
    }

}