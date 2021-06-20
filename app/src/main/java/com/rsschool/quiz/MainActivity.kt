package com.rsschool.quiz


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rsschool.quiz.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), Quiz.ActionPerformedListener {

    private lateinit var fragment:      Fragment
    private lateinit var transaction:   FragmentTransaction
    private lateinit var binding:       ActivityMainBinding
    private          var navigation =   0 // number of active page
    private lateinit var answers:       MutableMap<Int,MutableMap<Int,Boolean>>  //TODO try to sparseArray
                                        //<#question(navigation),<answer,isCorrect>
    private lateinit var isCorrect:     MutableMap<Int,Boolean>
    private lateinit var keys:          MutableMap<Int,Int>
                                        //<navi,answer>
    override fun nextQuestion(answer: Int, correct: Boolean) {
        isCorrect[navigation]   = correct
        keys[navigation]        = answer
        answers[navigation]     = isCorrect                                 // Save Question, answer and isCorrect

        if(navigation < 4) {
            openQuestion(keys[++navigation],navigation)
            setStatusBarColor()
        } else openResult()
    }

    override fun previousQuestion(answer: Int) {
        keys[navigation]        = answer
        openQuestion(keys[--navigation],navigation)
        setStatusBarColor()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //setTheme(R.style.Theme_Quiz_Fourth)
        super.onCreate(savedInstanceState)
        binding     = ActivityMainBinding.inflate(layoutInflater)
        answers     = mutableMapOf()
        isCorrect   = mutableMapOf()
        keys        = mutableMapOf()
        setContentView(binding.root)
        openQuestion(null,navigation)
    }
    private fun openQuestion (prevAnswer: Int?,question: Int){         //start with 0
        fragment    = Quiz.newInstance(prevAnswer,question)
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,fragment) .commit()
    }
    private fun openResult(){                //todo parameters String with result "Your result: 15%" and, may be string Array with correct and user answers answers
        var correctAnswers = 0
        isCorrect.forEach { if(it.value )  ++correctAnswers }
        val resultQuiz = "You result is: $correctAnswers/5"
        val result:     Fragment             = Result.newInstance(resultQuiz)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,result).commit()
    }

    private fun setStatusBarColor(){
        when(navigation){
            0       -> window.statusBarColor = this.resources.getColor(R.color.deep_orange_100_dark)
            1       -> window.statusBarColor = this.resources.getColor(R.color.yellow_100_dark)
            2       -> window.statusBarColor = this.resources.getColor(R.color.red_primary_dark)
            3       -> window.statusBarColor = this.resources.getColor(R.color.blue_primary_dark)
            4       -> window.statusBarColor = this.resources.getColor(R.color.grey_primary_dark)
            else    -> window.statusBarColor = this.resources.getColor(R.color.deep_orange_100_dark)
        }
    }
}