package com.empresa.myapplication.marvelapp._model.remote.pojos.eventos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultEventos {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("resourceURI")
    @Expose
    var resourceURI: String? = null

    @SerializedName("urls")
    @Expose
    var urls: List<Url>? = null

    @SerializedName("modified")
    @Expose
    var modified: String? = null

    @SerializedName("start")
    @Expose
    var start: Any? = null

    @SerializedName("end")
    @Expose
    var end: Any? = null

    @SerializedName("thumbnail")
    @Expose
    var thumbnail: Thumbnail? = null

    @SerializedName("creators")
    @Expose
    var creators: Creators? = null

    @SerializedName("characters")
    @Expose
    var characters: Characters? = null

    @SerializedName("stories")
    @Expose
    var stories: Stories? = null

    @SerializedName("comics")
    @Expose
    var comics: Comics? = null

    @SerializedName("series")
    @Expose
    var series: Series? = null

    @SerializedName("next")
    @Expose
    var next: Any? = null

    @SerializedName("previous")
    @Expose
    var previous: Any? = null

    /**
     * No args constructor for use in serialization
     *
     */
    constructor() {}

    /**
     *
     * @param next
     * @param thumbnail
     * @param stories
     * @param previous
     * @param creators
     * @param comics
     * @param start
     * @param description
     * @param resourceURI
     * @param title
     * @param characters
     * @param urls
     * @param series
     * @param modified
     * @param end
     * @param id
     */
    constructor(
        id: Int?,
        title: String?,
        description: String?,
        resourceURI: String?,
        urls: List<Url>?,
        modified: String?,
        start: Any?,
        end: Any?,
        thumbnail: Thumbnail?,
        creators: Creators?,
        characters: Characters?,
        stories: Stories?,
        comics: Comics?,
        series: Series?,
        next: Any?,
        previous: Any?
    ) : super() {
        this.id = id
        this.title = title
        this.description = description
        this.resourceURI = resourceURI
        this.urls = urls
        this.modified = modified
        this.start = start
        this.end = end
        this.thumbnail = thumbnail
        this.creators = creators
        this.characters = characters
        this.stories = stories
        this.comics = comics
        this.series = series
        this.next = next
        this.previous = previous
    }
}