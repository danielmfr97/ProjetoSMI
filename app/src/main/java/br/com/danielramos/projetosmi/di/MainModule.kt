package br.com.danielramos.projetosmi.di

import br.com.danielramos.projetosmi.contracts.MainContract
import br.com.danielramos.projetosmi.presenter.MainPresenter
import org.koin.dsl.module

val mainModule = module {
    factory<MainContract.MainPresenter>{
        (view: MainContract.MainView) -> MainPresenter(view)
    }
}