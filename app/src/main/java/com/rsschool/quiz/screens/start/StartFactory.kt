package com.rsschool.quiz.screens.start

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rsschool.quiz.room.QuestionRepository

class StartFactory (
    private val repository: QuestionRepository,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        Log.i("MyLog", "MainActivityFactory")
        if (modelClass.isAssignableFrom(StartViewModel::class.java)) {
            return StartViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}