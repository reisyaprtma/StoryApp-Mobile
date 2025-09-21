package com.dicoding.picodiploma.loginwithanimation.view.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailBinding
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityLoginBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginViewModel

class DetailActivity : AppCompatActivity() {
    private val viewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityDetailBinding
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id = intent.getStringExtra("EXTRA_ID")
        viewModel.getSession().observe(this) { user ->
            if (id != null) {
                viewModel.getStory(user.token, id)
            }
        }


        viewModel.story.observe(this) { story ->
            if (story != null) {
                binding.apply {
                    tvDetailName.text = story.name
                    tvDetailDesc.text = story.description
                    Glide.with(this@DetailActivity)
                        .load(story.photoUrl)
                        .into(imgDetail)
                }
            }
        }
    }
}