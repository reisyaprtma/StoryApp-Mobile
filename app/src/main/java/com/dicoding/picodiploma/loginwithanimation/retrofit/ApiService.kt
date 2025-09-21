package com.dicoding.picodiploma.loginwithanimation.retrofit

import com.dicoding.picodiploma.loginwithanimation.response.AddResponse
import com.dicoding.picodiploma.loginwithanimation.response.DetailResponse
import com.dicoding.picodiploma.loginwithanimation.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.response.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.response.StoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @Multipart
    @POST("stories")
    fun addNewStory(
        @Header("Authorization") token: String?,  // Header untuk token
        @Part("description") description: RequestBody?,  // Parameter deskripsi
        @Part photo: MultipartBody.Part?,  // File foto
    ): Call<AddResponse>
    @GET("stories")
    fun getLocation(
        @Header("Authorization") token: String, // Header untuk token
        @Query("location") location : Int = 1
    ): Call<StoryResponse>
    @GET("stories")
    fun getAllStories(
        @Header("Authorization") token: String, // Header untuk token
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 20
    ): Call<StoryResponse>
    @GET("stories/{id}")
    fun getDetailStory(
        @Header("Authorization") token: String, // Header untuk token
        @Path("id") id: String
    ): Call<DetailResponse>


}