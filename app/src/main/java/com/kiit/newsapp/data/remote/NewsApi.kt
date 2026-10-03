package com.kiit.newsapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import com.kiit.newsapp.model.NewsResponse

interface NewsApi {

    @GET("top-headlines")  // NewsAPI ke top headlines endpoint ko call karega.
    suspend fun getTopHeadlines(   // function ko temporarily pause karke baad mein resume karne deta hai, bina UI ko block kiye.
        @Query("country") country: String,  //API ko batayega kis country ki news chahiye.
        @Query("apiKey") apiKey: String     //  API key request ke saath bhejega
    ): NewsResponse
}
