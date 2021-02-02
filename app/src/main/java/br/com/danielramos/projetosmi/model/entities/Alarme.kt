package br.com.danielramos.projetosmi.model.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Alarme : RealmObject() {

    @PrimaryKey
    var id = ""
    var data: Date? = null
    var label = ""
    var pessoa_monitorada_id = ""
}