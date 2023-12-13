package com.example.inzy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class MemoryScan : AppCompatActivity() {

    private lateinit var textViewSequence: TextView
    private lateinit var layout: LinearLayout
    private lateinit var buttonUnderstood: Button

    private val elements = listOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L')
    private var currentElementIndex = 0
    private var correctAnswers = 0
    private var incorrectAnswers = 0
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_scan)

        textViewSequence = findViewById(R.id.textViewSequence)
        layout = findViewById(R.id.layout)
        buttonUnderstood = findViewById(R.id.buttonUnderstood)

        // Set a click listener for the "Understood" button
        buttonUnderstood.setOnClickListener {
            showNextQuestion()
        }

        // Generate and display the first random sequence
        displayRandomSequence()
    }

    private fun displayRandomSequence() {
        // Display the sequence
        textViewSequence.text = generateRandomSequence()
        textViewSequence.visibility = View.VISIBLE

        // Show the "Understood" button
        buttonUnderstood.visibility = View.VISIBLE
    }

    private fun showNextQuestion() {
        // Remove views associated with the previous question, if any
        layout.removeAllViews()

        // Check if all questions are displayed
        if (currentElementIndex < elements.size) {
            // Create a TextView for the current question
            val textView = TextView(this)
            val currentElement = elements[currentElementIndex]
            textView.text = "Czy element $currentElement był w sekwencji?"
            textView.textSize = 16f
            layout.addView(textView)

            // Create Yes button
            val yesButton = Button(this)
            yesButton.text = "Tak"
            yesButton.setOnClickListener {
                // Handle Yes button click
                handleButtonClick(true, currentElement)
            }
            layout.addView(yesButton)

            // Create No button
            val noButton = Button(this)
            noButton.text = "Nie"
            noButton.setOnClickListener {
                // Handle No button click
                handleButtonClick(false, currentElement)
            }
            layout.addView(noButton)

            // Increment the index for the next question
            currentElementIndex++
        } else {
            // All questions are displayed, show the original sequence
            saveScore()
            showResults()
        }
    }

    private fun handleButtonClick(isYes: Boolean, currentElement: Char) {
        // Check if the user's answer is correct
        val isCorrect = if (isYes) {
            textViewSequence.text.contains(currentElement)
        } else {
            !textViewSequence.text.contains(currentElement)
        }

        // Update counters based on the correctness of the answer
        if (isCorrect) {
            correctAnswers++
        } else {
            incorrectAnswers++
        }

        // After handling the click, show the next question
        showNextQuestion()
    }

    private fun showResults() {
        // Display the results
        val resultTextView = TextView(this)
        resultTextView.text = "Wynik: Prawidłowe : $correctAnswers, Nieprawidłowe : $incorrectAnswers"
        resultTextView.textSize = 18f
        layout.addView(resultTextView)

        // Create a button to return to the main menu
        val returnButton = Button(this)
        returnButton.text = "powrót"
        returnButton.setOnClickListener {
            // Handle the click event for the return button (you can customize this)
            // For example, you might want to finish the current activity and go back to the main menu
            val mainMenuIntent = Intent(this, MainActivity::class.java)
            startActivity(mainMenuIntent)
        }
        layout.addView(returnButton)
    }

    private fun generateRandomSequence(): String {
        val random = Random.Default

        // Shuffle the list and take the first 4 elements to create the sequence
        val shuffledList = elements.shuffled(random)
        return shuffledList.subList(0, 4).joinToString("")
    }
    private fun saveScore(){
        val user = auth.currentUser
        val userEmail = user?.email ?: "unknown"

        val scoresCollection = firestore.collection("MemoryScan4")
        val timestampFormat = SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z", Locale.US)
        val timestamp = timestampFormat.format(Date())

        val scoreData = hashMapOf(
            "userEmail" to userEmail,
            "score" to correctAnswers-incorrectAnswers,
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
