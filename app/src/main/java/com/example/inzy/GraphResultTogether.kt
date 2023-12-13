package com.example.inzy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.service.autofill.UserData
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GraphResultTogether : AppCompatActivity() {
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph_result_together)

        val textToShow = intent.getStringExtra("text")

        val collectionName = intent.getStringExtra("collectionName")


        val graphView: GraphView = findViewById(R.id.graphView)
        val button: Button = findViewById(R.id.button)
        val textView: TextView = findViewById(R.id.textView)

        textView.text = textToShow

        button.setOnClickListener {
            val resultIntent = Intent(this, SecondActvity::class.java)
            startActivity(resultIntent)
        }

        // Replace this with your data fetching logic
        if (collectionName != null) {
            fetchData(collectionName)
                .addOnSuccessListener { querySnapshot ->
                    val userData = querySnapshot.documents.map { document ->
                        // Access individual fields directly from the Firestore document
                        val timestamp = document.getString("timestamp") ?: ""
                        val time = document.getDouble("time") ?: 0
                        val score = document.getDouble("score") ?: 0

                        val timeStampDate = convertStringToTimestamp(timestamp)

                        val calendar = Calendar.getInstance()
                        timeStampDate?.let { calendar.time = it }

                        val year = calendar.get(Calendar.YEAR)
                        val month = calendar.get(Calendar.MONTH) + 1 // Months are 0-based
                        val day = calendar.get(Calendar.DAY_OF_MONTH)
                        val hour = calendar.get(Calendar.HOUR_OF_DAY)
                        val minute = calendar.get(Calendar.MINUTE)
                        val second = calendar.get(Calendar.SECOND)

                        mapOf(
                            "timestamp" to timestamp,
                            "time" to time,
                            "score" to score,
                            "year" to year,
                            "month" to month,
                            "day" to day,
                            "hour" to hour,
                            "minute" to minute,
                            "second" to second
                        )

                        // Now you have access to the timestamp and time for each document
                        // Perform any processing or use this data as needed
                    }

                    val sortedUserData = userData.sortedWith(compareBy(
                        { (it["year"] as? Int) ?: 0 },    // Sort by year
                        { (it["month"] as? Int) ?: 0 },   // If years are equal, sort by month
                        { (it["day"] as? Int) ?: 0 }, // If months are equal, sort by day
                        { (it["hour"] as? Int) ?: 0 },
                        { (it["minute"] as? Int) ?: 0 },
                        { (it["second"] as? Int) ?: 0 },
                    ))
                    Log.d("sortedUserData", "$sortedUserData")

                    // Now userData is a List<String> where each string represents a document field
                    // Use it as needed, for example, to populate the graph
                    if (sortedUserData.isNotEmpty()) {

                        val graphView: GraphView = findViewById(R.id.graphView)
                        val series = LineGraphSeries<DataPoint>()

                        // Sort the data by the extracted timestamp components
                        val timelineSeries = LineGraphSeries<DataPoint>()
                        val timepointSeries = PointsGraphSeries<DataPoint>()
                        val scoreLineSeries = LineGraphSeries<DataPoint>()
                        val scorePointSeries = PointsGraphSeries<DataPoint>()

                        scoreLineSeries.color = Color.RED

                        scorePointSeries.color = Color.RED

                        for ((index, item) in sortedUserData.withIndex()) {
                            val xValue = convertStringToTimestamp(item["timestamp"] as String) // or use "time" if needed
                            val timeyValue = item["time"] as Double
                            val scoreyValue = item["score"] as Double

                            if (xValue != null) {
                                timelineSeries.appendData(DataPoint(xValue, timeyValue), true, index + 1)
                                timepointSeries.appendData(DataPoint(xValue, timeyValue), true, index + 1)

                                scoreLineSeries.appendData(DataPoint(xValue,scoreyValue),true,index + 1)
                                scorePointSeries.appendData(DataPoint(xValue,scoreyValue),true,index + 1)
                            } else {
                                // Handle the case where timestamp conversion fails
                            }
                        }
                        graphView.addSeries(timelineSeries)
                        graphView.addSeries(timepointSeries)

                        graphView.addSeries(scoreLineSeries)
                        graphView.addSeries(scorePointSeries)


                        graphView.addSeries(series)

                        graphView.gridLabelRenderer.isHorizontalLabelsVisible = false
                        graphView.viewport.isScalable = true
                        graphView.viewport.isScrollable = true
                        graphView.viewport.setScalableY(true)
                        graphView.viewport.setScrollableY(true)
                        graphView.gridLabelRenderer.labelFormatter = DateAsXAxisLabelFormatter(this)

                        timepointSeries.setOnDataPointTapListener() { series, dataPoint ->
                            val xValue = dataPoint.x
                            val yValue = dataPoint.y

                            val formattedDate = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.US).format(xValue)

                            val builder = AlertDialog.Builder(this@GraphResultTogether)
                            builder.setTitle("Detale Testu")
                                .setMessage("Data: [$formattedDate] \nCzas testu: [$yValue s]")
                                .setPositiveButton("Zamknij") { dialog, _ ->
                                    dialog.dismiss()
                                }

                            val dialog = builder.create()
                            dialog.show()
                        }
                        scorePointSeries.setOnDataPointTapListener { series, dataPoint ->
                            val xValue = dataPoint.x
                            val yValue = dataPoint.y

                            val formattedDate = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.US).format(xValue)

                            val builder = AlertDialog.Builder(this@GraphResultTogether)
                            builder.setTitle("Detale Testu")
                                .setMessage("Data: [$formattedDate] \nWynik testu: [$yValue]")  // Assuming the yValue represents the score
                                .setPositiveButton("Zamknij") { dialog, _ ->
                                    dialog.dismiss()
                                }

                            val dialog = builder.create()
                            dialog.show()
                        }
                    } else {
                        // Handle case where there is no data
                    }
                }
                .addOnFailureListener { exception ->
                    // Handle errors during data fetching
                    exception.printStackTrace()
                }
        }

    }

    private fun fetchData(name: String) = firestore.collection(name)
        .whereEqualTo("userEmail", auth.currentUser?.email)
        .get()

    // Function to convert timestamp string to Date object
    private fun convertStringToTimestamp(timestampString: String): Date? {
        val parts = timestampString.split(" ")

        // Extract relevant parts
        val day = parts[2]
        val month = parts[1]
        val year = parts[3]
        val time = parts[4]

        // Create a formatted date string
        val formattedDateString = "$day $month $year $time"

        // Define a date format pattern
        val dateFormat = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.US)

        try {
            val parsedDate = dateFormat.parse(formattedDateString)
            Log.d("ConvertedTimestamp", "Original: $timestampString, Parsed: $parsedDate")
            return parsedDate
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }
}
