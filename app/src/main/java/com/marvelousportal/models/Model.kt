package com.marvelousportal.models

import com.google.gson.annotations.SerializedName


/**
 * Created by Bhavik on 1/30/2018.
 */

data class Model(
        @SerializedName("code") val code: Int?, //200
        @SerializedName("status") val status: String?, //Ok
        @SerializedName("copyright") val copyright: String?, //© 2018 MARVEL
        @SerializedName("attributionText") val attributionText: String?, //Data provided by Marvel. © 2018 MARVEL
        @SerializedName("attributionHTML") val attributionHTML: String?, //<a href="http://marvel.com">Data provided by Marvel. © 2018 MARVEL</a>
        @SerializedName("etag") val etag: String?, //8564460db2969053daea6042fb7b535a7f12351f
        @SerializedName("data") val data: Data?
)

data class Data(
        @SerializedName("offset") val offset: Int, //0
        @SerializedName("limit") val limit: Int, //20
        @SerializedName("total") val total: Int, //1491
        @SerializedName("count") val count: Int, //20
        @SerializedName("results") val results: List<Result>
)

data class Result(
        @SerializedName("id") val id: Int, //1011334
        @SerializedName("digitalId") val digitalId: Int, //1011334
        @SerializedName("name") val name: String, //3-D Man
        @SerializedName("title") val title: String, //3-D Man
        @SerializedName("description") val description: String,
        @SerializedName("modified") val modified: String, //2014-04-29T14:18:17-0400
        @SerializedName("thumbnail") val thumbnail: Thumbnail,
        @SerializedName("resourceURI") val resourceURI: String, //http://gateway.marvel.com/v1/public/characters/1011334
        @SerializedName("comics") val comics: Comics,
        @SerializedName("series") val series: Series,
        @SerializedName("stories") val stories: Stories,
        @SerializedName("events") val events: Events,
        @SerializedName("urls") val urls: List<Url>,
        @SerializedName("issueNumber") val issueNumber: Int,
        @SerializedName("variantDescription") val variantDescription: String,
        @SerializedName("isbn") val isbn: String,
        @SerializedName("upc") val upc: String,
        @SerializedName("diamondCode") val diamondCode: String,
        @SerializedName("ean") val ean: String,
        @SerializedName("issn") val issn: String,
        @SerializedName("format") val format: String,
        @SerializedName("pageCount") val pageCount: Int,
        @SerializedName("textObjects") val textObjects: List<TextObject>,
        @SerializedName("variants") val variants: List<Variant>,
        @SerializedName("collections") val collections: List<Any>,
        @SerializedName("collectedIssues") val collectedIssues: List<Any>,
        @SerializedName("dates") val dates: List<Date>,
        @SerializedName("prices") val prices: List<Price>,
        @SerializedName("images") val images: List<Image>,
        @SerializedName("creators") val creators: Creators,
        @SerializedName("characters") val characters: Characters,
        @SerializedName("start") val start: String,
        @SerializedName("end") val end: String
)

data class Thumbnail(
        @SerializedName("path") val path: String, //http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784
        @SerializedName("extension") val extension: String //jpg
)

data class Stories(
        @SerializedName("available") val available: Int, //21
        @SerializedName("collectionURI") val collectionURI: String, //http://gateway.marvel.com/v1/public/characters/1011334/stories
        @SerializedName("items") val items: List<Item>,
        @SerializedName("returned") val returned: Int //20
)

data class Item(
        @SerializedName("resourceURI") val resourceURI: String, //http://gateway.marvel.com/v1/public/stories/19947
        @SerializedName("name") val name: String, //Cover #19947
        @SerializedName("type") val type: String //cover
)

data class Events(
        @SerializedName("available") val available: Int, //1
        @SerializedName("collectionURI") val collectionURI: String, //http://gateway.marvel.com/v1/public/characters/1011334/events
        @SerializedName("items") val items: List<Item>,
        @SerializedName("returned") val returned: Int //1
)

data class Comics(
        @SerializedName("available") val available: Int, //12
        @SerializedName("collectionURI") val collectionURI: String, //http://gateway.marvel.com/v1/public/characters/1011334/comics
        @SerializedName("items") val items: List<Item>,
        @SerializedName("returned") val returned: Int //12
)

data class Url(
        @SerializedName("type") val type: String, //detail
        @SerializedName("url") val url: String //http://marvel.com/characters/74/3-d_man?utm_campaign=apiRef&utm_source=bae8d2281a0d569b0cfd5fbc7cd6ee6f
)

data class Series(
        @SerializedName("available") val available: Int, //3
        @SerializedName("collectionURI") val collectionURI: String, //http://gateway.marvel.com/v1/public/characters/1011334/series
        @SerializedName("items") val items: List<Item>,
        @SerializedName("returned") val returned: Int //3
)

data class TextObject(
        @SerializedName("type") val type: String,
        @SerializedName("language") val language: String,
        @SerializedName("text") val text: String
)

data class Variant(
        @SerializedName("resourceURI") val resourceURI: String,
        @SerializedName("name") val name: String
)

data class Date(
        @SerializedName("type") val type: String,
        @SerializedName("date") val date: String
)

data class Price(
        @SerializedName("type") val type: String,
        @SerializedName("price") val price: Double
)

data class Image(
        @SerializedName("path") val path: String,
        @SerializedName("extension") val extension: String
)

data class Creators(
        @SerializedName("available") val available: Int,
        @SerializedName("collectionURI") val collectionURI: String,
        @SerializedName("items") val items: List<Items>,
        @SerializedName("returned") val returned: Int
)

data class Characters(
        @SerializedName("available") val available: Int? = null,
        @SerializedName("collectionURI") val collectionURI: String,
        @SerializedName("items") val items: List<Item>,
        @SerializedName("returned") val returned: Int
)

data class Items(
        @SerializedName("resourceURI") val resourceURI: String,
        @SerializedName("name") val name: String,
        @SerializedName("role") val role: String
)