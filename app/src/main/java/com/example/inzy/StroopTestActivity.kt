package com.example.inzy

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.inzy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Arrays
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class StroopTestActivity : AppCompatActivity() {
    private lateinit var wordTextView: TextView
    private lateinit var correctButton: Button
    private lateinit var incorrectButton: Button
    private lateinit var scoreTextView: TextView

    private val words = arrayOf("Czerwony", "Niebieski", "Zielony", "Żółty")
    private val colors = arrayOf(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW)

    private var score = 0
    private var timer: CountDownTimer? = null
    private var correctCount = 0
    private var inCorrectCount = 0
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stroop_test)

        wordTextView = findViewById(R.id.wordTextView)
        correctButton = findViewById(R.id.correctButton)
        incorrectButton = findViewById(R.id.incorrectButton)
        scoreTextView = findViewById(R.id.scoreTextView)

        correctButton.setOnClickListener { checkAnswer(true) }
        incorrectButton.setOnClickListener { checkAnswer(false) }

        // Start the test
        showRandomWord()

        startTimer()
    }

    private fun startTimer(){
        timer = object : CountDownTimer(2000,1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                showRandomWord()
                startTimer()
                inCorrectCount++
                updateScore()
            }
        }
        timer?.start()
    }

    private fun showRandomWord() {
        
        val randomIndex = Random.nextInt(0, words.size)
        val word = words[randomIndex]
        val color = colors[Random.nextInt(0, colors.size)]

        wordTextView.text = word
        wordTextView.setTextColor(color)
    }

    private fun checkAnswer(userSaidCorrect: Boolean) {

        timer?.cancel()

        val correctColor = wordTextView.currentTextColor == colors[words.indexOf(wordTextView.text.toString())]

        if ((userSaidCorrect && correctColor) || (!userSaidCorrect && !correctColor)) {
            score++
            correctCount++
        }else{
            score++
            inCorrectCount++
        }

        if (correctCount == 10) {
            saveScore()
            // Game finished, start ResultsActivity
            val intent = Intent(this, stroopResultActivity::class.java)
            intent.putExtra("correctCount", correctCount)
            intent.putExtra("incorrectCount", inCorrectCount)
            startActivity(intent)
            finish() // finish the StroopTestActivity
        } else {
            showRandomWord()
            updateScore()
            startTimer()
        }
    }

    private fun updateScore() {
        scoreTextView.text = "Liczba prawidłowych odpowiedzi: $correctCount"
    }

    private fun saveScore(){
        val user = auth.currentUser
        val userEmail = user?.email ?: "unknown"

        val scoresCollection = firestore.collection("scoresStroopTest")
        val timestampFormat = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.US)
        val timestamp = timestampFormat.format(Date())

        val scoreData = hashMapOf(
            "userEmail" to userEmail,
            "score" to (correctCount-inCorrectCount),
            "correct" to (correctCount),
            "incorrect" to inCorrectCount,
            "timestamp" to timestamp
        )

        scoresCollection.add(scoreData)
            .addOnSuccessListener { documentReference ->
                println("Score saved with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                println("Error saving score: $e")
            }
    }
    override fun onBackPressed() {
    }

}
