package com.example.newsapp.data.remote.model


import com.example.newsapp.domain.model.Article
import com.google.gson.annotations.SerializedName

data class ApiResponseDto(
    @SerializedName("copyright")
    val copyright: String,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("num_results")
    val numResults: Int,
    @SerializedName("results")
    val results: List<ArticleDto>,
    @SerializedName("section")
    val section: String,
    @SerializedName("status")
    val status: String
)