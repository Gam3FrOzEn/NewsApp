package com.android.newsapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.newsapp.R
import com.android.newsapp.databinding.ActivityMainBinding
import com.android.newsapp.fragments.TopicFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().replace(R.id.main_container, TopicFragment())
            .commit()
    }
}