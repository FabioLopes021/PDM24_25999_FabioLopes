package com.example.newsapp.data.remote.model

import com.example.newsapp.domain.model.Article


data class ArticleDto (
    val title: String,
    val section: String,
    val webUrl: String,
){
    fun toArticle(): Article {
        return Article(title = title, section = section, webUrl = webUrl)
        //Implementar depois
    }
}

