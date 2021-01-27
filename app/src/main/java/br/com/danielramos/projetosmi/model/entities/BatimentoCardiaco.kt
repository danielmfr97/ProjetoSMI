package br.com.danielramos.projetosmi.model.entities

import androidx.room.PrimaryKey
import io.realm.RealmObject

open class BatimentoCardiaco : RealmObject() {

    @PrimaryKey
    var id = ""
    var batimento = ""
    var pessoa_monitorada_id = ""
}