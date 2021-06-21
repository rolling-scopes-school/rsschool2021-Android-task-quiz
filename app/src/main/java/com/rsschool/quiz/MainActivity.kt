package com.rsschool.quiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.rsschool.quiz.data.Question
import com.rsschool.quiz.presentation.QuizFragment
import com.rsschool.quiz.presentation.QuizListViewModel

class MainActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var viewModel: QuizListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {

            viewModel = (applicationContext as QuizApplication)
                .appContainer.getQuizListViewModel(this)
            viewModel.loadQuestions()
            viewModel.questionList.observe(this, this::openQuizFragment)
        }
    }

    private fun openQuizFragment(list: List<Question>) {
        if (!list.isNullOrEmpty())
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, QuizFragment.newInstance(list.toTypedArray()))
                .addToBackStack(null)
                .commit()
    }
}