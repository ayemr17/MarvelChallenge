package com.empresa.myapplication.marvelapp._model.remote.pojos.eventos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class EventosPojo {
    @SerializedName("code")
    @Expose
    var code: Int? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("copyright")
    @Expose
    var copyright: String? = null

    @SerializedName("attributionText")
    @Expose
    var attributionText: String? = null

    @SerializedName("attributionHTML")
    @Expose
    var attributionHTML: String? = null

    @SerializedName("etag")
    @Expose
    var etag: String? = null

    @SerializedName("data")
    @Expose
    var data: Data? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param copyright
     * @param code
     * @param data
     * @param attributionHTML
     * @param attributionText
     * @param etag
     * @param status
     */
    constructor(
        code: Int?,
        status: String?,
        copyright: String?,
        attributionText: String?,
        attributionHTML: String?,
        etag: String?,
        data: Data?
    ) : super() {
        this.code = code
        this.status = status
        this.copyright = copyright
        this.attributionText = attributionText
        this.attributionHTML = attributionHTML
        this.etag = etag
        this.data = data
    }
}