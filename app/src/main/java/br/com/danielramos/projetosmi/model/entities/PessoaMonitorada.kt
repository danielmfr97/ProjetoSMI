package br.com.danielramos.projetosmi.model.entities

import androidx.room.PrimaryKey
import io.realm.RealmObject

open class PessoaMonitorada : RealmObject() {

    @PrimaryKey
    var id = ""
    var nome = ""
    var telefone = ""
}