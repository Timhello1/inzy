package com.example.inzy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inzy.databinding.ActivityStroopTestWelcomeBinding


class StroopTestWelcomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityStroopTestWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStroopTestWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Po naciśnięciu przycisku "Zagraj" przenieś do StroopTestActivity
        binding.playButton.setOnClickListener {
            val intent = Intent(this, StroopTestActivity::class.java)
            startActivity(intent)
        }
    }
}
