package com.dicoding.picodiploma.loginwithanimation.view.addstory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.response.AddResponse
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.File

class AddStoryViewModel(private val repository: UserRepository):ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun addNewStory(token: String, description: String, photo: File) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.addNewStory(token,description,photo).enqueue(object : Callback<AddResponse>{
                override fun onResponse(call: Call<AddResponse>, response: Response<AddResponse>) {

                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _success.value = true
                    } else {
                        _error.value = response.message()
                    }
                }
                override fun onFailure(call: Call<AddResponse>, t: Throwable) {
                    _isLoading.value = false
                    _error.value = t.message
                }
            })
        }
    }
}