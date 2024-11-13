package com.example.newsapp.data.repository

import com.example.newsapp.data.remote.api.NewYorkTimesApi
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.ArticleDetail
import com.example.newsapp.domain.repository.ArticleRepository


class ArticlesRepositoryImppll(private val api: NewYorkTimesApi) : ArticleRepository {
    override suspend fun getArticles(): List<Article> {
        return api.getArticls().results.map { it.toArticle() }
    }

//    override suspend fun getArticlesDetail(coinId: String): ArticleDetail {
//        return api.getArticlsDetail(coinId).toArticleDetail()
//    }
}