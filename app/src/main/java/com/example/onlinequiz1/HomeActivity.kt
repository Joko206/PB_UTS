package com.example.onlinequiz1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var createQuestionButton: Button
    private lateinit var answerQuestionButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        createQuestionButton = findViewById(R.id.createQuestionButton)
        answerQuestionButton = findViewById(R.id.answerQuestionButton)

        createQuestionButton.setOnClickListener {
            val intent = Intent(this, AddQuestionActivity::class.java)
            startActivity(intent)
        }

        answerQuestionButton.setOnClickListener {
            val intent = Intent(this, QuestionListActivity::class.java)
            startActivity(intent)
        }
    }
}
