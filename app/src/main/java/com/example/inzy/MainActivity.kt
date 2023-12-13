package com.example.inzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.inzy.databinding.ActivityLoginBinding
import com.example.inzy.databinding.ActivityMainBinding
import com.example.inzy.databinding.ActivitySecondActvityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentUser = FirebaseAuth.getInstance().currentUser

        checkIfTestDone("CircleClickTest", "CircleClickerTest", binding.AgilityTestCard, currentUser)
        checkIfTestDone("StroopTest", "scoresStroopTest", binding.StroopTestCard, currentUser)
        checkIfTestDone("ReitanTest", "ReitanTest", binding.ReitanTest, currentUser)
        checkIfTestDone("SwitchTrailTest", "SwitchTrailTest", binding.SwitchTrailTest, currentUser)
        checkIfTestDone("LetterCancelMTest", "LetterCancel8Score", binding.LetterCancellationTestMedium, currentUser)
        checkIfTestDone("LetterCancelETest", "LetterCancel5Score", binding.LetterCancellationTest, currentUser)
        checkIfTestDone("LetterCancelHTest", "LetterCancel10Score", binding.LetterCancellationTestHard, currentUser)
        checkIfTestDone("SubE", "DigitalSub10", binding.DigitSubTestCard, currentUser)
        checkIfTestDone("SubM", "DigitalSub20", binding.DigitSubTestCardMedium, currentUser)
        checkIfTestDone("SubH", "DigitalSub30", binding.DigitSubTestCardHard, currentUser)
        checkIfTestDone("ScanE", "MemoryScan4", binding.MemoryScanTest, currentUser)
        checkIfTestDone("ScanM", "MemoryScan6", binding.MemoryScanTestMedium, currentUser)
        checkIfTestDone("ScanH", "MemoryScan10", binding.MemoryScanTestHard, currentUser)


        binding.StroopTestCard.setOnClickListener{
            val stroopIntent = Intent(this, StroopTestWelcomeActivity::class.java)
            startActivity(stroopIntent)
        }

        binding.ReitanTest.setOnClickListener {
            val reitanIntent = Intent(this, ReitanWelcomeActivity::class.java)
            startActivity(reitanIntent)
        }

        binding.SwitchTrailTest.setOnClickListener {
            val switchIntent = Intent(this, SwitchTrailWelcome::class.java)
            startActivity(switchIntent)
        }

        binding.AgilityTestCard.setOnClickListener {
            val clickIntent = Intent(this, ClickTestWelcome::class.java)
            startActivity(clickIntent)
        }
        binding.LetterCancellationTest.setOnClickListener {
            val letterCancelIntent = Intent(this, LetterCancelWelcome::class.java)
            startActivity(letterCancelIntent)
        }
        binding.LetterCancellationTestMedium.setOnClickListener {
            val letterCancelMediumIntent = Intent(this, LetterCancelWelcome::class.java)
            startActivity(letterCancelMediumIntent)
        }
        binding.LetterCancellationTestHard.setOnClickListener {
            val letterCancelHardIntent = Intent(this, LetterCancelWelcome::class.java)
            startActivity(letterCancelHardIntent)
        }
        binding.MemoryScanTest.setOnClickListener {
            val memoryScanIntent = Intent(this, MemoryScanWelcome::class.java)
            startActivity(memoryScanIntent)
        }
        binding.MemoryScanTestMedium.setOnClickListener {
            val memoryScanMediumIntent = Intent(this,MemoryScanWelcome::class.java)
            startActivity(memoryScanMediumIntent)
        }
        binding.MemoryScanTestHard.setOnClickListener {
            val memoryScanHardIntent = Intent(this, MemoryScanWelcome::class.java)
            startActivity(memoryScanHardIntent)
        }
        binding.DigitSubTestCard.setOnClickListener {
            val digitSub = Intent(this, SubWelcome::class.java)
            startActivity(digitSub)
        }
        binding.DigitSubTestCardMedium.setOnClickListener {
            val digitSubMediumIntent = Intent(this, SubWelcome::class.java)
            startActivity(digitSubMediumIntent)
        }
        binding.DigitSubTestCardHard.setOnClickListener {
            val digitSubHardIntent = Intent(this, SubWelcome::class.java)
            startActivity(digitSubHardIntent)
        }

    }
    fun openDrawer(view: View) {
        // Toggle the drawer when the ImageView is clicked
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
    fun openStroopTestActivity(view: View) {
        val stroopIntent = Intent(this, StroopTestWelcomeActivity::class.java)
        startActivity(stroopIntent)
    }

    fun openReitanTestActivity(view: View) {
        val reitanIntent = Intent(this, ReitanWelcomeActivity::class.java)
        startActivity(reitanIntent)
    }
    fun openSwitchTestActivity(view: View){
        val switchIntent = Intent(this, SwitchTrailWelcome::class.java)
        startActivity(switchIntent)
    }
    fun openAgilityTestActivity(view: View){
        val agilityIntent = Intent(this,ClickTestWelcome::class.java)
        startActivity(agilityIntent)
    }
    fun openLetterTestActivity(view: View){
        val letterIntent = Intent(this,LetterCancelWelcome::class.java)
        startActivity(letterIntent)
    }
    fun openSubTestActivity(view: View){
        val subIntent = Intent(this,SubWelcome::class.java)
        startActivity(subIntent)
    }
    fun openScanTestActivity(view: View){
        val scanIntent = Intent(this,MemoryScanWelcome::class.java)
        startActivity(scanIntent)
    }
    fun openResults(view: View){
        val resultIntent = Intent(this, SecondActvity::class.java)
        startActivity(resultIntent)
    }

    private fun checkIfTestDone(testName: String, collectionName: String, card: View, currentUser: FirebaseUser?) {
        currentUser?.let { user ->
            val userEmail = user.email
            val currentDate = SimpleDateFormat("MMM dd yyyy", Locale.US).format(Date())

            if (userEmail != null) {
                firestore.collection(collectionName)
                    .whereEqualTo("userEmail", userEmail)
                    .get()
                    .addOnSuccessListener { snapshot ->
                        for (doc in snapshot) {
                            val testDate = doc.getString("timestamp")?.substring(4, 15)

                            if (testDate == currentDate) {
                                // User has done the test today
                                card.visibility = View.GONE
                            }
                        }
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error checking $testName: $e")
                    }
            }
        }
    }

}


