package com.example.madlibs

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ItemTouchUIUtil
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class ActivityQA : AppCompatActivity() {
    private lateinit var title: String
    private var text = ""
    private lateinit var hint: String
    private val hints = ArrayList<String>()
    private var h = 0
    private val answers = ArrayList<String>()
    private lateinit var story: String
    private lateinit var storyTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qa)

        val actionBar = supportActionBar
        actionBar?.title = "Fill in the blanks"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent.hasExtra("extraText")) {
            title = intent.getStringExtra("extraText").toString()
        }
        findViewById<EditText>(R.id.input).addTextChangedListener() {
            findViewById<Button>(R.id.next).isEnabled = findViewById<EditText>(R.id.input).text.isNotEmpty()
        }
        readFile()
        findViewById<Button>(R.id.next).setOnClickListener {
            questions()
        }
        findViewById<EditText>(R.id.input).hint = hints[h]
    }

    private fun readFile() {
        val reader = Scanner(resources.openRawResource(R.raw.the_space_shuttle))
        storyTitle = reader.nextLine()
        while (reader.hasNextLine()) {
            val line = reader.nextLine() + " "
            text += line
            text = text.trim()
        }

        val tokenizer = StringTokenizer(text, ".,;?! ")
        var n = 0
        while (tokenizer.hasMoreTokens()) {
            hint = tokenizer.nextToken()
            val index0 = hint[0]
            val indexEnd = hint[hint.length-1]
            if (index0 == '(' && indexEnd == ')') {
                val replace = "replace$n"
                Log.i("index", replace)
                n++
                text = text.replaceFirst(hint, replace)
                hint = hint.substring(1, hint.length-1)
                hint = hint.replace('_', ' ', true)
                hints.add(hint)
            }
        }
        Log.i("title", storyTitle)
        Log.i("title", "story1 $text")
    }

    @SuppressLint("SetTextI18n")
    private fun questions() {
        answers.add(findViewById<EditText>(R.id.input).text.toString())
        when {
            h < hints.size-2 -> {
                h++
                findViewById<EditText>(R.id.input).hint = hints[h]
            }
            h == hints.size-2 -> {
                h++
                findViewById<Button>(R.id.next).text = "Create your story"
                findViewById<EditText>(R.id.input).hint = hints[h]
            }
            else -> {
                createStory()
                val intent = Intent(this, ActivityStory::class.java)
                intent.putExtra("extraTextTitle", storyTitle)
                intent.putExtra("extraText", story)
                startActivity(intent)
            }
        }
        findViewById<EditText>(R.id.input).text.clear()
    }

    private fun createStory() {
        var n = 0
        story = text;
        while (n < hints.size) {
            story = story.replaceFirst("replace$n", answers[n] , false)
            Log.i("index", "replace$n")
            Log.i("index-", answers[n])
            n++
        }
        Log.i("index", story)
    }
}