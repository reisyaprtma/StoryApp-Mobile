package com.dicoding.picodiploma.loginwithanimation.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.response.DetailResponse
import com.dicoding.picodiploma.loginwithanimation.response.Story
import com.dicoding.picodiploma.loginwithanimation.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val storyRepository: UserRepository) : ViewModel() {
    private val _story = MutableLiveData<Story?>()
    val story: LiveData<Story?> = _story
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    fun getSession(): LiveData<UserModel> {
        return storyRepository.getSession().asLiveData()
    }
    fun getStory(token: String, id: String) {
        _isLoading.value=true
        viewModelScope.launch {
            storyRepository.getDetailStory(token,id).enqueue(object : Callback<DetailResponse>{
                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    _isLoading.value=false
                    if (response.isSuccessful){
                        _story.value=response.body()?.story
                    }
                    else{
                        _error.value=response.message()
                    }
                }
                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    _isLoading.value=false
                    _error.value=t.message
                }
            })
        }

    }
}