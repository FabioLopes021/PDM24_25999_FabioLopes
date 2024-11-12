package com.example.newsapp.data.remote.model

import com.example.newsapp.domain.model.ArticleDetail

data class ArticleDetailDto (
    val title: String,
    val section: String,
    val abstract: String,
){
    fun toArticleDetail(): ArticleDetail {
        return ArticleDetail(title = title, section = section, abstract = abstract)
        //Implementar depois
    }
}