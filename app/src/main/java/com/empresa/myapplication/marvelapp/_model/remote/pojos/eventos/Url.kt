package com.empresa.myapplication.marvelapp._model.remote.pojos.eventos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Url {
    @SerializedName("type")
    @Expose
    var type: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param type
     * @param url
     */
    constructor(type: String?, url: String?) : super() {
        this.type = type
        this.url = url
    }
}