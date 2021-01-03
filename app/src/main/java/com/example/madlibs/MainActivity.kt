package com.example.madlibs

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    //val extraText = "com.example.madlibs.EXTRA_TEXT"
    private var r = Random(0)
    private var color = Color.WHITE

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ConstraintLayout>(R.id.layout).setOnClickListener {
            color = Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256))
            findViewById<ConstraintLayout>(R.id.layout).setBackgroundColor(color)
            findViewById<Button>(R.id.button).setTextColor(color)
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            val chosenTopic = findViewById<Spinner>(R.id.spinner).selectedItem.toString()
            val intent = Intent(this, ActivityQA::class.java)
            intent.putExtra("extraText", chosenTopic)
            Log.i("elene", chosenTopic)
            startActivity(intent)
        }

        val spinner = findViewById<Spinner>(R.id.spinner)
        val adapter = ArrayAdapter.createFromResource(this, R.array.topics, android.R.layout.simple_spinner_dropdown_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}