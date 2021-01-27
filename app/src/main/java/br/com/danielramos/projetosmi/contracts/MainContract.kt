package br.com.danielramos.projetosmi.contracts

interface MainContract {

    interface MainView {

    }

    interface MainPresenter {
        val view: MainView
    }
}