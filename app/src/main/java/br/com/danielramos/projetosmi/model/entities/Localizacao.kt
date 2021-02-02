package br.com.danielramos.projetosmi.model.entities

import io.realm.annotations.PrimaryKey

open class Localizacao {

    @PrimaryKey
    var id = ""
    var latitude = ""
    var longitude = ""
    var pessoa_monitorada_id = ""
}