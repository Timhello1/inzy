package com.example.inzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inzy.databinding.ActivityReitanWelcomeBinding

class ReitanWelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReitanWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReitanWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playButton.setOnClickListener {
            val intent = Intent(this, Reitan2::class.java)
            startActivity(intent)
        }
    }
}