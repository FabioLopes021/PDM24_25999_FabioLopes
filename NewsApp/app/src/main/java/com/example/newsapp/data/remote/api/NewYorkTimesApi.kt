package com.example.newsapp.data.remote.api

import com.example.newsapp.data.remote.model.ArticleDetailDto
import com.example.newsapp.data.remote.model.ArticleDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object RetrofitInstance {
    val api: NewYorkTimesApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/topstories/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewYorkTimesApi::class.java)
    }
}


interface NewYorkTimesApi {
    @GET("v2/arts.json?api-key=6SV4QcALEfHDDToNDY3ADiBnMFdmabJ3")
    suspend fun getArticls(): List<ArticleDto>

    @GET("v2/arts.json?api-key=6SV4QcALEfHDDToNDY3ADiBnMFdmabJ3")
    suspend fun getArticlsDetail(@Path("id") coinId: String): ArticleDetailDto


//    @GET("v1/coins/{id}")
//    suspend fun getCoinDetail(@Path("id") coinId: String): CoinDetailDto

}