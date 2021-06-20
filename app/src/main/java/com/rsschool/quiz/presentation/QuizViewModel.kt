package com.rsschool.quiz.presentation


import com.rsschool.quiz.data.providers.IQuizLoader
import android.util.Log
import androidx.lifecycle.*
import com.rsschool.quiz.data.Question
import kotlinx.coroutines.launch


class QuizViewModel(
    private val quizLoader: IQuizLoader
) : ViewModel() {

    private val _mutableQuestion = MutableLiveData(
        Question(-1,"", emptyList(), 0, 0)
    )
//    private val _mutableQuestion = MutableLiveData<Question>()
    val question: LiveData<Question> get() = _mutableQuestion

    private val _mutableQuestionList = MutableLiveData<List<Question>>(emptyList())
    val questionList: LiveData<List<Question>> get() = _mutableQuestionList
    private var _questionList : List<Question>? = null
    private var _currentQuestion = 0
    //private val _currentQuestion = MutableLiveData(0)

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

    fun checkAnswer(questionId: Int, answerId: Int) {
        if (_mutableQuestionList.value?.isNullOrEmpty() == false) {
            viewModelScope.launch {
                try {
                    _mutableQuestionList.value!![questionId].selection = answerId
                }catch (throwable: Throwable){
                    Log.d(TAG, "Answer setting Error")
                }
            }
        }
    }

    fun getQuestionById(questionId: Int) {
//        if (_mutableQuestionList.value?.isNullOrEmpty() == false) {
//            viewModelScope.launch {
//                return@launch try {
//                    _mutableQuestionList.value!![questionId]
//                } catch (throwable: Throwable) {
//                    Log.d(TAG, "Answer getting Error")
//                } as Unit
//            }
//        }
        if (!_questionList.isNullOrEmpty() && questionId >= 0) {
            viewModelScope.launch {
                try {
                    _mutableQuestion.postValue(_questionList!![questionId])
                } catch (throwable: Throwable) {
                    Log.d(TAG, "Question getting Error")
                }
            }
        }
    }

    fun getNextQuestion() {
        getQuestionById(++_currentQuestion)
    }

    fun getPreviousQuestion(){
        getQuestionById(--_currentQuestion)
    }
    // TODO: Implement the ViewModel

    companion object {
        const val INVALID_QUESTION = -1
        private val TAG = QuizViewModel::class.java.simpleName
    }
}