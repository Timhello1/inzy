package com.example.inzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.inzy.databinding.ActivityStroopResultBinding

class stroopResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStroopResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStroopResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val correctCount = intent.getIntExtra("correctCount", 0)
        val incorrectCount = intent.getIntExtra("incorrectCount", 0)
        val result = correctCount - incorrectCount

        val resultTextView: TextView = findViewById(R.id.resultTextView)
        resultTextView.text = "Test ukończony!\nPrawidłowe odpowiedzi: $correctCount\nNieprawidłowe odpowiedzi: $incorrectCount\nWynik: $result"

        binding.goBackMenu.setOnClickListener {
            val goMenuIntent = Intent(this, MainActivity::class.java)
            startActivity(goMenuIntent)
        }
    }
    override fun onBackPressed() {
    }
}