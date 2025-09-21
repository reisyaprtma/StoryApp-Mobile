package com.dicoding.picodiploma.loginwithanimation.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.response.StoryResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: UserRepository) : ViewModel() {

    private val _stories = MutableLiveData<List<ListStoryItem>?>()
    val stories: LiveData<List<ListStoryItem>?> = _stories
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error



    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
    fun getStories(token: String): LiveData<PagingData<ListStoryItem>> {
        val story: LiveData<PagingData<ListStoryItem>> =
            repository.getStories(token).cachedIn(viewModelScope)
        Log.d("MainViewModel", "getStories: $story")
        return story
    }
//    fun getStories(token: String, page: Int, size: Int){
//        viewModelScope.launch {
//            repository.getLocation(token, page, size).enqueue(object : Callback<StoryResponse>{
//                override fun onResponse(
//                    call: Call<StoryResponse>,
//                    response: Response<StoryResponse>
//                ) {
//                    if (response.isSuccessful){
//                        _stories.value = response.body()?.listStory
//                    }
//                    else{
//                        _error.value = response.message()
//                    }
//
//                }
//                override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
//                    _error.value = t.message
//                }
//            })
//
//        }
}