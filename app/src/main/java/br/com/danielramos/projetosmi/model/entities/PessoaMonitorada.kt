package br.com.danielramos.projetosmi.model.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class PessoaMonitorada : RealmObject() {

    @PrimaryKey
    var id = ""
    var nome = ""
    var telefone = ""
}