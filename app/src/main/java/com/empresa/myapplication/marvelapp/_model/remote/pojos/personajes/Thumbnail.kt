package com.empresa.myapplication.marvelapp._model.remote.pojos.personajes

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Thumbnail {
    @SerializedName("path")
    @Expose
    var path: String? = null

    @SerializedName("extension")
    @Expose
    var extension: String? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param path
     * @param extension
     */
    constructor(path: String?, extension: String?) : super() {
        this.path = path
        this.extension = extension
    }
}