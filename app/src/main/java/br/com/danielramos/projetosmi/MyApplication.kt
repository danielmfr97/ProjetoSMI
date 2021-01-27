package br.com.danielramos.projetosmi

import android.app.Application
import br.com.danielramos.projetosmi.di.mainModule
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        configurarRealm()
        configurarKoin()
    }

    private fun configurarRealm() {
        Realm.init(this)
        val configuration = RealmConfiguration.Builder()
            .name("projeto.realm").build()
        Realm.setDefaultConfiguration(configuration)
    }

    private fun configurarKoin() {
        startKoin{
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(
                listOf(mainModule)
            )
        }
    }

}