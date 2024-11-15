package com.example.newsapp.data.repository

import android.util.Log
import com.example.newsapp.data.remote.api.NewYorkTimesApi
import com.example.newsapp.domain.model.Article
import com.example.newsapp.domain.model.ArticleDetail
import com.example.newsapp.domain.repository.ArticleRepository


class ArticlesRepositoryImppll(private val api: NewYorkTimesApi) : ArticleRepository {
    override suspend fun getArticles(): List<Article> {
        return api.getArticls().results.map { it.toArticle() }
    }

    override suspend fun getArticleDetail(webUrl: String): ArticleDetail {
        Log.d("CoinListViewModel", "Web URL: ${webUrl}")
        return api.getArticleDetail(webUrl).response.docs[0].toArticleDetail()
    }
}