package br.com.danielramos.projetosmi

import android.app.Application
import com.google.firebase.FirebaseApp
import io.realm.Realm
import io.realm.RealmConfiguration


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configurarRealm()
        inicializarFirebase()
    }

    private fun configurarRealm() {
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("projeto.realm").build()
        Realm.setDefaultConfiguration(configuration)
    }

    private fun inicializarFirebase() {
        FirebaseApp.initializeApp(this)
    }
}