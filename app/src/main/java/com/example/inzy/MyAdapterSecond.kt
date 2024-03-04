package com.example.inzy

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapterSecond(private val context: Context, private val itemList: List<String>) :
    RecyclerView.Adapter<MyAdapterSecond.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_second, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.textViewItem.text = item

        holder.itemView.setOnClickListener {
            if (position == 0) {
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultTime::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu zwinności") // Use item as the string for the text view
                intent.putExtra("collectionName", "CircleClickerTest") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position == 1){
                val intent = Intent(context, GraphResultScore::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Stroop'a") // Use item as the string for the text view
                intent.putExtra("collectionName", "scoresStroopTest") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position == 2) {
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultTimeN::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Reitana") // Use item as the string for the text view
                intent.putExtra("collectionName", "ReitanTest") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position == 3) {
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultTimeN::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Zmiennej Ścieżki") // Use item as the string for the text view
                intent.putExtra("collectionName", "SwitchTrailTest") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if(position == 4){
                val intent = Intent(context, GraphResultScore::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Skanu Pamięci (Łatwego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "MemoryScan4") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position==5){
                val intent = Intent(context, GraphResultScore::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Skanu Pamięci (Średniego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "MemoryScan6") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position==6){
                val intent = Intent(context, GraphResultScore::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Skanu Pamięci (Trudnego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "MemoryScan10") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position == 7) {
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultTimeN::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Szyfrowania (łatwego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "DigitalSub10") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position == 8) {
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultTimeN::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Szyfrowania (średniego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "DigitalSub20") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position == 9) {
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultTimeN::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Szyfrowania (trudnego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "DigitalSub30") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if(position==10){
                val intent = Intent(context, GraphResultScore::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Anulowania Liter (Łatwego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "LetterCancel5Score") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position==11){
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultScore::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Anulowania Liter (średniego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "LetterCancel8Score") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position==12){
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultScore::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Anulowania Liter (trudnego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "LetterCancel10Score") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position == 13) {
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultTimeN::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Anulowania Liter (łatwego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "LetterCancel5Score") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position == 14) {
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultTimeN::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Anulowania Liter (średniego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "LetterCancel8Score") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position == 15) {
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultTimeN::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Anulowania Liter (trudnego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "LetterCancel10Score") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position == 16) {
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultTogether::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Anulowania Liter (łatwego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "LetterCancel5Score") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position == 17) {
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultTogether::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Anulowania Liter (łatwego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "LetterCancel8Score") // Replace with your actual collection name

                context.startActivity(intent)
            }
            if (position == 18) {
                // Open GraphResultTime activity
                val intent = Intent(context, GraphResultTimeN::class.java)

                intent.putExtra("text", "Twoje Wyniki Testu Anulowania Liter (trudnego)") // Use item as the string for the text view
                intent.putExtra("collectionName", "LetterCancel10Score") // Replace with your actual collection name

                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItem: TextView = itemView.findViewById(R.id.textViewItemSecond)
    }
}
