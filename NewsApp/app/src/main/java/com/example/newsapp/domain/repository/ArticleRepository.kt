package com.example.newsapp.domain.repository

import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.ArticleDetail

interface ArticleRepository {
    suspend fun getArticles(): List<Article>
    suspend fun getArticlesDetail(coinId: String): ArticleDetail
}