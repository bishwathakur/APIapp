package com.example.apiapp.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.apiapp.R
import com.example.apiapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    
    lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        
        

        binding.viewProductsButton.setOnClickListener {
            startActivity(Intent(this, ProductListActivity::class.java))
        }
    }
}
