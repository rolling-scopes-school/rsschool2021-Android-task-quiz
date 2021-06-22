package com.rsschool.quiz.screens.start

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rsschool.quiz.room.Question
import com.rsschool.quiz.room.QuestionRepository
import kotlinx.coroutines.launch

class StartViewModel (
    private val repository: QuestionRepository,
    application: Application
) : AndroidViewModel(application) {

    val questions: LiveData<List<Question>> = repository.allQuestions.asLiveData()

    fun insert(question: Question) = viewModelScope.launch {
        repository.insert(question)
    }

}