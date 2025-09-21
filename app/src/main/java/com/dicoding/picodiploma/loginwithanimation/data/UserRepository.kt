package com.dicoding.picodiploma.loginwithanimation.data

import StoryPagingSource
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.response.AddResponse
import com.dicoding.picodiploma.loginwithanimation.response.DetailResponse
import com.dicoding.picodiploma.loginwithanimation.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.response.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.response.StoryResponse
import com.dicoding.picodiploma.loginwithanimation.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import java.io.File

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    fun registerUser(name: String, email: String, password: String): Call<RegisterResponse> {
        return apiService.register(name, email, password)
    }
    fun loginUser(email: String, password: String): Call<LoginResponse> {
        return apiService.login(email, password)
    }
    fun getDetailStory(token: String, id: String): Call<DetailResponse> {
        return apiService.getDetailStory("Bearer $token", id)
    }
    fun addNewStory(token: String, description: String, photo: File): Call<AddResponse> {
        val requestBodyDescription = description.toRequestBody("text/plain".toMediaType())
        val requestBodyPhoto = photo.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "photo",
            photo.name,
            requestBodyPhoto
        )
        return apiService.addNewStory("Bearer $token", requestBodyDescription, multipartBody)
    }
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }
    fun getLocation(token: String): Call<StoryResponse> {
        return apiService.getLocation("Bearer $token")
    }
    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5, // Tentukan ukuran halaman
                enablePlaceholders = false // Nonaktifkan placeholder jika tidak diperlukan
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService, "Bearer $token")
            }
        ).liveData
    }


    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}