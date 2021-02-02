package br.com.danielramos.projetosmi.model.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class BatimentoCardiaco : RealmObject() {

    @PrimaryKey
    var id = ""
    var batimento = ""
    var pessoa_monitorada_id = ""
}