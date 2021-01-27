package br.com.danielramos.projetosmi.model.entities

import androidx.room.PrimaryKey
import io.realm.RealmObject
import java.util.*

open class Alarme : RealmObject() {

    @PrimaryKey
    var id = ""
    var data: Date? = null
    var label = ""
    var pessoa_monitorada_id = ""
}