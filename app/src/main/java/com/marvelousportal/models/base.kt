package com.marvelousportal.models

import android.media.Image
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Bhavik Makwana on 2/1/2018.
 */

class base {
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null

    inner class Result {

        @SerializedName("id")
        private val id: Int? = null
        @SerializedName("digitalId")
        @Expose
        private val digitalId: Int? = null
        @SerializedName("title")
        @Expose
        private val title: String? = null
        @SerializedName("issueNumber")
        @Expose
        private val issueNumber: Int? = null
        @SerializedName("variantDescription")
        @Expose
        private val variantDescription: String? = null
        @SerializedName("description")
        @Expose
        private val description: String? = null
        @SerializedName("modified")
        @Expose
        private val modified: String? = null
        @SerializedName("isbn")
        @Expose
        private val isbn: String? = null
        @SerializedName("upc")
        @Expose
        private val upc: String? = null
        @SerializedName("diamondCode")
        @Expose
        private val diamondCode: String? = null
        @SerializedName("ean")
        @Expose
        private val ean: String? = null
        @SerializedName("issn")
        @Expose
        private val issn: String? = null
        @SerializedName("format")
        @Expose
        private val format: String? = null
        @SerializedName("pageCount")
        @Expose
        private val pageCount: Int? = null
        @SerializedName("textObjects")
        @Expose
        private val textObjects: List<TextObject>? = null
        @SerializedName("resourceURI")
        @Expose
        private val resourceURI: String? = null
        @SerializedName("urls")
        @Expose
        private val urls: List<Url>? = null
        @SerializedName("series")
        @Expose
        private val series: Series? = null
        @SerializedName("variants")
        @Expose
        private val variants: List<Variant>? = null
        @SerializedName("collections")
        @Expose
        private val collections: List<Any>? = null
        @SerializedName("collectedIssues")
        @Expose
        private val collectedIssues: List<Any>? = null
        @SerializedName("dates")
        @Expose
        private val dates: List<Date>? = null
        @SerializedName("prices")
        @Expose
        private val prices: List<Price>? = null
        @SerializedName("thumbnail")
        @Expose
        private val thumbnail: Thumbnail? = null
        @SerializedName("images")
        @Expose
        private val images: List<Image>? = null
        @SerializedName("creators")
        @Expose
        private val creators: Creators? = null
        @SerializedName("characters")
        @Expose
        private val characters: Characters? = null
        @SerializedName("stories")
        @Expose
        private val stories: Stories? = null
        @SerializedName("events")
        @Expose
        private val events: Events? = null
    }

    inner class Series {

        @SerializedName("resourceURI")
        @Expose
        private val resourceURI: String? = null
        @SerializedName("name")
        @Expose
        private val name: String? = null
    }

    inner class Item {

        @SerializedName("resourceURI")
        @Expose
        private val resourceURI: String? = null
        @SerializedName("name")
        @Expose
        private val name: String? = null
        @SerializedName("role")
        @Expose
        private val role: String? = null
    }

    inner class Item_ {

        @SerializedName("resourceURI")
        @Expose
        private val resourceURI: String? = null
        @SerializedName("name")
        @Expose
        private val name: String? = null
        @SerializedName("type")
        @Expose
        private val type: String? = null
    }

    inner class Events {

        @SerializedName("available")
        @Expose
        private val available: Int? = null
        @SerializedName("collectionURI")
        @Expose
        private val collectionURI: String? = null
        @SerializedName("items")
        @Expose
        private val items: List<Any>? = null
        @SerializedName("returned")
        @Expose
        private val returned: Int? = null
    }

    inner class Stories {

        @SerializedName("available")
        @Expose
        private val available: Int? = null
        @SerializedName("collectionURI")
        @Expose
        private val collectionURI: String? = null
        @SerializedName("items")
        @Expose
        private val items: List<Item_>? = null
        @SerializedName("returned")
        @Expose
        private val returned: Int? = null
    }

    data class TextObject(
            @SerializedName("type")
            @Expose
            private val type: String? = null,
            @SerializedName("language")
            @Expose
            private val language: String? = null,
            @SerializedName("text")
            @Expose
            private val text: String? = null
    )

    inner class Thumbnail {

        @SerializedName("path")
        @Expose
        private val path: String? = null
        @SerializedName("extension")
        @Expose
        private val extension: String? = null
    }

    inner class Url {

        @SerializedName("type")
        @Expose
        private val type: String? = null
        @SerializedName("url")
        @Expose
        private val url: String? = null
    }
}
