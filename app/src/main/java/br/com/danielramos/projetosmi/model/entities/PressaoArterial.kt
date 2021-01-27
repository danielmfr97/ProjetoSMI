package br.com.danielramos.projetosmi.model.entities

import androidx.room.PrimaryKey
import io.realm.RealmObject

open class PressaoArterial : RealmObject() {

    @PrimaryKey
    var id = ""
    var pressao = ""
    var pessoa_monitorada_id = ""
}