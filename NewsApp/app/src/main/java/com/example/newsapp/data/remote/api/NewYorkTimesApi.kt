package com.example.newsapp.data.remote.api

import com.example.newsapp.data.remote.model.ApiArticleDetailsResponseDto
import com.example.newsapp.data.remote.model.ApiArticlesResponseDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object RetrofitInstance {
    val api: NewYorkTimesApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewYorkTimesApi::class.java)
    }
}


interface NewYorkTimesApi {
    @GET("topstories/v2/arts.json?api-key=6SV4QcALEfHDDToNDY3ADiBnMFdmabJ3")
    suspend fun getArticls(): ApiArticlesResponseDto

    @GET("search/v2/articlesearch.json?fq=web_url:({\"url\"})&api-key=6SV4QcALEfHDDToNDY3ADiBnMFdmabJ3")
    suspend fun getArticleDetail(@Path("url") webUrl: String): ApiArticleDetailsResponseDto

//  search/v2/articlesearch.json?fq=web_url:("{url}")&api-key=6SV4QcALEfHDDToNDY3ADiBnMFdmabJ3
    // No final o link tem que ficar assim -> vai faltar acrescentar  ""
//    @GET("v1/coins/{id}")
//    suspend fun getCoinDetail(@Path("id") coinId: String): CoinDetailDto

}