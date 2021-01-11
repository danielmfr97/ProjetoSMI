package br.com.danielramos.projetosmi

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configurarRoom()
    }

    fun configurarRoom() {

    }

}