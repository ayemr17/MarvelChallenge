package com.empresa.myapplication.marvelapp.model.remote.pojos.eventos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Item_ {
    @SerializedName("resourceURI")
    @Expose
    var resourceURI: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("type")
    @Expose
    var type: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param name
     * @param resourceURI
     * @param type
     */
    constructor(resourceURI: String?, name: String?, type: String?) : super() {
        this.resourceURI = resourceURI
        this.name = name
        this.type = type
    }
}