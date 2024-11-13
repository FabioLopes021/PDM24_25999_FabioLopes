package com.example.newsapp.domain.model

data class Article (
    val abstract: String,
    val section: String,
    val subsection: String,
    val title: String,
    val url: String
){
    fun toArticle(): Article {
        return Article(abstract = abstract, section = section, subsection = subsection, title = title, url = url)
    }
}