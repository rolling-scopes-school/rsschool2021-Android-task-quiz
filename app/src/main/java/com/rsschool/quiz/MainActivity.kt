package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rsschool.quiz.presentation.QuizFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openQuizFragment()
        }
    }

    private fun openQuizFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, QuizFragment.newInstance())
            .commitNow()
    }
}