package com.empresa.myapplication.marvelapp.model.remote.pojos.personajes

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
    var results: List<Result>? = null

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
     * @param results
     */
    constructor(
        offset: Int?,
        limit: Int?,
        total: Int?,
        count: Int?,
        results: List<Result>?
    ) : super() {
        this.offset = offset
        this.limit = limit
        this.total = total
        this.count = count
        this.results = results
    }
}