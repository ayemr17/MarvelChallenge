package com.empresa.myapplication.marvelapp.model.remote.pojos.eventos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("offset")
    @Expose
    var offset: Int? = null

    @SerializedName("limit")
    @Expose
    var limit: Int? = null

    @SerializedName("total")
    @Expose
    var total: Int? = null

    @SerializedName("count")
    @Expose
    var count: Int? = null

    @SerializedName("results")
    @Expose
    var resultEventos: List<ResultEventos>? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param total
     * @param offset
     * @param limit
     * @param count
     * @param resultEventos
     */
    constructor(
        offset: Int?,
        limit: Int?,
        total: Int?,
        count: Int?,
        resultEventos: List<ResultEventos>?
    ) : super() {
        this.offset = offset
        this.limit = limit
        this.total = total
        this.count = count
        this.resultEventos = resultEventos
    }
}