package com.example.inzy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inzy.databinding.ActivityClickTestWelcomeBinding
import com.example.inzy.databinding.ActivityStroopTestWelcomeBinding


class ClickTestWelcome : AppCompatActivity() {

    private lateinit var binding:ActivityClickTestWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClickTestWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Po naciśnięciu przycisku "Zagraj" przenieś do StroopTestActivity
        binding.playButton.setOnClickListener {
            val intent = Intent(this, ClickTest::class.java)
            startActivity(intent)
        }
    }
}
