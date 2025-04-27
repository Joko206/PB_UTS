package com.example.onlinequiz1

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class AnswerQuestionActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var optionsRadioGroup: RadioGroup
    private lateinit var submitAnswerButton: Button
    private lateinit var database: DatabaseReference
    private lateinit var currentQuestion: Question  // Make sure this is of type Question

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_question)

        questionTextView = findViewById(R.id.questionTextView)
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup)
        submitAnswerButton = findViewById(R.id.submitAnswerButton)

        val questionTitle = intent.getStringExtra("questionTitle")
        database = FirebaseDatabase.getInstance().reference.child("questions")

        loadQuestion(questionTitle)

        submitAnswerButton.setOnClickListener {
            val selectedOptionId = optionsRadioGroup.checkedRadioButtonId
            val selectedAnswer = when (selectedOptionId) {
                R.id.option1 -> findViewById<RadioButton>(R.id.option1).text.toString()
                R.id.option2 -> findViewById<RadioButton>(R.id.option2).text.toString()
                R.id.option3 -> findViewById<RadioButton>(R.id.option3).text.toString()
                R.id.option4 -> findViewById<RadioButton>(R.id.option4).text.toString()
                else -> ""
            }

            if (selectedAnswer == currentQuestion.correctAnswer) {
                Toast.makeText(this, "Jawaban benar!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Jawaban salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadQuestion(title: String?) {
        val questionRef = database.orderByChild("title").equalTo(title)

        questionRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (questionSnapshot in snapshot.children) {
                        // Fetching the data and mapping it to the Question object
                        val question = questionSnapshot.getValue(Question::class.java)
                        question?.let {
                            currentQuestion = it
                            updateUI()
                        }
                    }
                } else {
                    Toast.makeText(this@AnswerQuestionActivity, "Question not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AnswerQuestionActivity, "Error loading question", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI() {
        // Update the UI with the question and options
        questionTextView.text = currentQuestion.question
        findViewById<RadioButton>(R.id.option1).text = currentQuestion.option1
        findViewById<RadioButton>(R.id.option2).text = currentQuestion.option2
        findViewById<RadioButton>(R.id.option3).text = currentQuestion.option3
        findViewById<RadioButton>(R.id.option4).text = currentQuestion.option4
    }

    data class Question(
        val question: String = "",
        val option1: String = "",
        val option2: String = "",
        val option3: String = "",
        val option4: String = "",
        val correctAnswer: String = ""
    )
}
