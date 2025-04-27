package com.example.onlinequiz1

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class AddQuestionActivity : AppCompatActivity() {

    private lateinit var questionEditText: EditText
    private lateinit var option1EditText: EditText
    private lateinit var option2EditText: EditText
    private lateinit var option3EditText: EditText
    private lateinit var option4EditText: EditText
    private lateinit var correctAnswerEditText: EditText
    private lateinit var titleEditText: EditText // For the title of the question
    private lateinit var submitButton: Button
    private lateinit var backToHomeButton: Button
    private val database = FirebaseDatabase.getInstance().reference.child("questions")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)

        // Initializing the UI components
        questionEditText = findViewById(R.id.questionEditText)
        option1EditText = findViewById(R.id.option1EditText)
        option2EditText = findViewById(R.id.option2EditText)
        option3EditText = findViewById(R.id.option3EditText)
        option4EditText = findViewById(R.id.option4EditText)
        correctAnswerEditText = findViewById(R.id.correctAnswerEditText)
        titleEditText = findViewById(R.id.titleEditText) // For the question title
        submitButton = findViewById(R.id.submitQuestionButton)
        backToHomeButton = findViewById(R.id.backToHomeButton)

        // Handle submit button click
        submitButton.setOnClickListener {
            addQuestion()
        }

        // Handle back to home button click
        backToHomeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()  // Close the current activity
        }
    }

    private fun addQuestion() {
        // Collecting the input values from the EditTexts
        val titleText = titleEditText.text.toString() // Title of the question
        val questionText = questionEditText.text.toString()
        val option1 = option1EditText.text.toString()
        val option2 = option2EditText.text.toString()
        val option3 = option3EditText.text.toString()
        val option4 = option4EditText.text.toString()
        val correctAnswer = correctAnswerEditText.text.toString()

        // Checking if all fields are filled
        if (titleText.isNotEmpty() && questionText.isNotEmpty() && option1.isNotEmpty() && option2.isNotEmpty() &&
            option3.isNotEmpty() && option4.isNotEmpty() && correctAnswer.isNotEmpty()) {

            // Create a unique ID for the question and push it to Firebase
            val questionId = database.push().key
            val question = Question(titleText, questionText, option1, option2, option3, option4, correctAnswer)

            questionId?.let {
                database.child(it).setValue(question)
                Toast.makeText(this, "Soal berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                finish()  // Close the activity and go back
            }

        } else {
            // If any field is empty, show a Toast message
            Toast.makeText(this, "Harap isi semua field", Toast.LENGTH_SHORT).show()
        }
    }

    // Data class for Question object
    data class Question(
        val title: String = "",  // Title of the question
        val question: String = "",
        val option1: String = "",
        val option2: String = "",
        val option3: String = "",
        val option4: String = "",
        val correctAnswer: String = ""
    )
}
