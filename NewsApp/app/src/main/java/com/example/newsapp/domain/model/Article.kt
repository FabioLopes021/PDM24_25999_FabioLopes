package com.example.newsapp.domain.model

data class Article (
    val title: String,
    val section: String,
    val webUrl: String
){
    fun toArticle(): Article {
        return Article(title = title, section = section, webUrl = webUrl)
        //Implementar depois
    }
}