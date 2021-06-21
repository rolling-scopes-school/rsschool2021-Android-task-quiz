package com.rsschool.quiz.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsschool.quiz.data.Question
import com.rsschool.quiz.data.providers.IQuizLoader
import kotlinx.coroutines.launch

class QuizListViewModel(
    private val quizLoader: IQuizLoader
) : ViewModel(){

    private val _mutableQuestionList = MutableLiveData<List<Question>>(emptyList())
    val questionList: LiveData<List<Question>> get() = _mutableQuestionList

    fun loadQuestions() {
        if (_mutableQuestionList.value?.isEmpty() == true) {
            viewModelScope.launch {
                try {
                    _mutableQuestionList.value = quizLoader.getQuesitons()
                }catch (throwable: Throwable){
                    Log.d(TAG, "Question List Loading Error")
                }
            }
        }
    }

    companion object {
        private val TAG = QuizListViewModel::class.java.simpleName
    }

}
