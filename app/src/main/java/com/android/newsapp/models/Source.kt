package com.android.newsapp.models

import java.io.Serializable

class Source : Serializable {
    var name: String? = null
    var id: String? = null
    var description: String? = null
    var url: String? = null
    var category: String? = null
    var language: String? = null
    var country: String? = null

    constructor() {}
    constructor(
        name: String?,
        id: String?,
        description: String?,
        url: String?,
        category: String?,
        language: String?,
        country: String?
    ) {
        this.name = name
        this.id = id
        this.description = description
        this.url = url
        this.category = category
        this.language = language
        this.country = country
    }
}