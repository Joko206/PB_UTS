package com.example.onlinequiz1

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class QuestionListActivity : AppCompatActivity() {

    private lateinit var questionListView: ListView
    private lateinit var database: DatabaseReference
    private lateinit var questionList: ArrayList<String>
    private lateinit var questionTitles: ArrayList<String> // To hold the question titles

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_list)

        questionListView = findViewById(R.id.questionListView)
        questionList = ArrayList()
        questionTitles = ArrayList()

        database = FirebaseDatabase.getInstance().reference.child("questions")
        loadQuestionTitles()

        questionListView.setOnItemClickListener { parent, view, position, id ->
            // Navigate to the AnswerQuestionActivity with the selected question title
            val intent = Intent(this, AnswerQuestionActivity::class.java)
            intent.putExtra("questionTitle", questionTitles[position])
            startActivity(intent)
        }
    }

    private fun loadQuestionTitles() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (questionSnapshot in snapshot.children) {
                    val title = questionSnapshot.child("title").value.toString()
                    questionTitles.add(title)
                    questionList.add(title)
                }
                val adapter = ArrayAdapter(this@QuestionListActivity, android.R.layout.simple_list_item_1, questionList)
                questionListView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@QuestionListActivity, "Error loading questions", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
