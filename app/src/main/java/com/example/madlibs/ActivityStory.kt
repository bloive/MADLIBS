package com.example.madlibs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.io.FileReader
import java.lang.Exception
import java.util.*

class ActivityStory : AppCompatActivity() {
    private lateinit var storyTitle: String
    private lateinit var story: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        if (intent.hasExtra("extraTextTitle")) {
            storyTitle = intent?.getStringExtra("extraTextTitle").toString()
        }
        if (intent.hasExtra("extraText")) {
            story = intent?.getStringExtra("extraText").toString()
        }
        findViewById<TextView>(R.id.storyTitle).text = storyTitle
        findViewById<TextView>(R.id.story).text = story
    }
}