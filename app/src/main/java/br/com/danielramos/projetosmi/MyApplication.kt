package br.com.danielramos.projetosmi

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configurarRealm()
    }

    private fun configurarRealm() {
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("projeto.realm").build()
        Realm.setDefaultConfiguration(configuration)
    }

}