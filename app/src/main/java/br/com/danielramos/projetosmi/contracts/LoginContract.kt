package br.com.danielramos.projetosmi.contracts

interface LoginContract {

    interface LoginView {
        fun getEmail() : String
        fun getSenha() : String
    }

    interface LoginPresenter {

    }

}