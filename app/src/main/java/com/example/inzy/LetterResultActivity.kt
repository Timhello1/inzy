package com.example.inzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LetterResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_result)

        val foundLettersTextView: TextView = findViewById(R.id.foundLettersTextView)
        val timeTextView: TextView = findViewById(R.id.timeTextView)
        val backButton: Button = findViewById(R.id.backButton)

        // Retrieve data from Intent
        val foundLetters = intent.getIntExtra("foundLetters",0)
        val timeInSeconds = intent.getLongExtra("timeInSeconds", 0)

        // Display found letters and time
        foundLettersTextView.text = "Znalezione litery: ${foundLetters} "
        timeTextView.text = "czas: $timeInSeconds sekund"

        // Set up click listener for the back button
        backButton.setOnClickListener {
            val mainMenuIntent = Intent(this, MainActivity::class.java)
            startActivity(mainMenuIntent)
        }
    }
    override fun onBackPressed() {
    }
}