package br.com.danielramos.projetosmi.model.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PressaoArterial : RealmObject() {

    @PrimaryKey
    var id = ""
    var pressao = ""
    var pessoa_monitorada_id = ""
}