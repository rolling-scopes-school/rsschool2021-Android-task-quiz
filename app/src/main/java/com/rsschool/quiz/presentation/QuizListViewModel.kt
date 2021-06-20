package com.rsschool.quiz.presentation

import androidx.lifecycle.ViewModel
import com.rsschool.quiz.data.providers.IQuizLoader
import com.rsschool.quiz.data.providers.QuizLoader

class QuizListViewModel(
    private val quizLoader: IQuizLoader
) : ViewModel(){

}
