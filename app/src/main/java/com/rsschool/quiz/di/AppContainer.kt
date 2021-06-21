package com.rsschool.quiz.di

import com.rsschool.quiz.data.providers.QuizLoader
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.rsschool.quiz.MainActivity
import com.rsschool.quiz.presentation.QuizListViewModel
import com.rsschool.quiz.presentation.QuizListViewModelFactory
import com.rsschool.quiz.presentation.QuizViewModel
import com.rsschool.quiz.presentation.QuizViewModelFactory
import kotlinx.coroutines.Dispatchers

class AppContainer(applicationContext: Context) {

    private val quizLoader = QuizLoader(applicationContext,Dispatchers.IO)

    fun getQuizViewModel(fragment: Fragment): QuizViewModel =
        ViewModelProvider(fragment, QuizViewModelFactory()).get()

    fun getQuizListViewModel(activity: MainActivity): QuizListViewModel =
        ViewModelProvider(activity, QuizListViewModelFactory(quizLoader)).get()
}
