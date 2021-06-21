package com.rsschool.quiz.presentation

import android.util.Log
import androidx.lifecycle.*
import com.rsschool.quiz.data.Question
import kotlinx.coroutines.launch


class QuizViewModel : ViewModel() {

    private var _currentQuestion = 0
    private var quizListItems: Array<Question>? = null
    private var quizLength: Int = 0
    private var _currentItemId = 0

    private val _mutableQuestionUpdate = MutableLiveData(0)
    val questionUpdate: LiveData<Int> get() = _mutableQuestionUpdate

    private val _mutableEnableNext = MutableLiveData(false)
    val enableNext: LiveData<Boolean> get() = _mutableEnableNext

    private val _mutableEnablePrev = MutableLiveData(false)
    val enablePrev: LiveData<Boolean> get() = _mutableEnablePrev

    private val _mutableCurrentItemId = MutableLiveData(-1)
    val currentItemId: LiveData<Int> get() = _mutableCurrentItemId

    fun setQuizList(quizList: Array<Question>) {
        quizListItems = quizList
        quizLength = quizList.size
        _mutableEnableNext.value = false
//        onNextClicked()
    }

    fun setUserAnswer(answerId: Int) {
        try {
            quizListItems?.let { list->
                list[_currentQuestion].userAnswer == answerId
            }
        }catch (throwable: Throwable){
            Log.d(TAG, "Answer setting Error")
        }
    }

    fun getUserAnswer() : Int {
        var answerId = -1
        try {
            quizListItems?.let { list->
                answerId = list[_currentQuestion].userAnswer
            }
        }catch (throwable: Throwable){
            Log.d(TAG, "Answer getting Error")
        }
        return answerId
    }

    fun updateUI(){
        _mutableQuestionUpdate.value = _currentQuestion
    }

    fun onNextClick() {
        viewModelScope.launch {
            try {
                if (_currentQuestion < quizListItems?.size ?: 0) {
                    setUserAnswer(_currentItemId)
                    //_mutableEnableNext.value =
                    _mutableEnableNext.value = false
                    _mutableCurrentItemId.value = -1
                    _currentQuestion++
                    updateUI()
                }
                _mutableEnablePrev.value = _currentQuestion > 0
            }catch (throwable: Throwable){
                Log.d(TAG, "Next button setting Error")
            }
        }
    }

    fun onPreviousClick(){
        viewModelScope.launch {
            try {
                _mutableCurrentItemId.value = getUserAnswer()
                if (_currentQuestion > 0) {
                    _currentQuestion--
                    updateUI()
                }
                _mutableEnablePrev.value = _currentQuestion > 0
            }catch (throwable: Throwable){
                Log.d(TAG, "Previous button setting Error")
            }
        }
    }

    fun onCheckedChange(itemId: Int){
        _currentItemId = itemId
        if(itemId != -1)
            _mutableEnableNext.value = true
    }

    companion object {
        const val INVALID_QUESTION = -1
        private val TAG = QuizViewModel::class.java.simpleName
    }
}