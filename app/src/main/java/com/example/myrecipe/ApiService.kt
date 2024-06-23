package com.example.myrecipe


import com.example.recipeapp.Recipe
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("recipes")
    fun getRecipes(): Call<List<Recipe>>

    @POST("recipes")
    fun addRecipe(@Body recipe: Recipe): Call<Void>

    @PUT("recipes/{id}")
    fun updateRecipe(@Path("id") id: Int, @Body recipe: Recipe): Call<Void>

    @DELETE("recipes/{id}")
    fun deleteRecipe(@Path("id") id: Int): Call<Void>

    companion object {
        private const val BASE_URL = "https://45a0-112-215-154-190.ngrok-free.app"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
