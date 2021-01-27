package br.com.danielramos.projetosmi.model.entities

import androidx.room.PrimaryKey

open class Localizacao {

    @PrimaryKey
    var id = ""
    var latitude = ""
    var longitude = ""
    var pessoa_monitorada_id = ""
}