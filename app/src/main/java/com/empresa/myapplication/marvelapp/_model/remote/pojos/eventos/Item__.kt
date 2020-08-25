package com.empresa.myapplication.marvelapp._model.remote.pojos.eventos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item__ {
    @SerializedName("resourceURI")
    @Expose
    var resourceURI: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("role")
    @Expose
    var role: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param role
     * @param name
     * @param resourceURI
     */
    constructor(resourceURI: String?, name: String?, role: String?) : super() {
        this.resourceURI = resourceURI
        this.name = name
        this.role = role
    }
}