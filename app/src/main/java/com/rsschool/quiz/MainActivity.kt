package com.rsschool.quiz

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.databinding.Fragment1QuizBinding

class MainActivity : AppCompatActivity(), Fragment1_quiz.ActionPerformedListener {

    lateinit var fragment : Fragment
    lateinit var transaction :FragmentTransaction
    private lateinit var binding: ActivityMainBinding
    /*override fun nextQuestion(answer:Int, correct: Boolean?) {
        Log.d("myLogs","$correct, $answer")

       // transaction.remove(fragment)
        fragment=Fragment1_quiz.newInstance(null,1)
        transaction=supportFragmentManager.beginTransaction()
                transaction.replace(binding.Container.id,fragment).commit()
       // openQuestion(null,2)
    }*/
    private var navigation =0 // number of active page
    private lateinit var answers: MutableMap<Int,MutableMap<Int,Boolean>>
                                    //<#question(navigation),<answer,isCorrect>
    private lateinit var isCorrect: MutableMap<Int,Boolean>
    override fun nextQuestion(answer: Int, correct: Boolean) {
        Log.d("myLogs","$correct, $answer")
        isCorrect[answer]   = correct
        answers[navigation] = isCorrect                                 // Save Question, answer and isCorrect
        if(navigation!=5) {
            openQuestion(answer,++navigation)
            /*transaction.remove(fragment)
            fragment = Fragment1_quiz.newInstance(answer, navigation)
            transaction = supportFragmentManager.beginTransaction()
            transaction.replace(binding.Container.id, fragment).commit()*/
            when(navigation){
                0 -> window.statusBarColor = this.resources.getColor(R.color.deep_orange_100_dark)
                1 -> window.statusBarColor = this.resources.getColor(R.color.yellow_100_dark)
                2 -> window.statusBarColor = this.resources.getColor(R.color.red_primary_dark)
                3 -> window.statusBarColor = this.resources.getColor(R.color.blue_primary_dark)
                4 -> window.statusBarColor = this.resources.getColor(R.color.grey_primary_dark)
            }
        } else openResult()
        //window.statusBarColor = resources.getColor(R.color.black) //TODO statusbarColor for all Questions
    }

    override fun previousQuestion(previousAnswer: Int) {
        //navigation--
        openQuestion(--navigation,previousAnswer)
        /*transaction.remove(fragment)
        fragment = Fragment1_quiz.newInstance(previousAnswer, navigation)
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id, fragment).commit()*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //setTheme(R.style.Theme_Quiz_Fourth)
        super.onCreate(savedInstanceState)
        binding     = ActivityMainBinding.inflate(layoutInflater)
        answers     = mutableMapOf()
        isCorrect   = mutableMapOf()
        setContentView(binding.root)
        openQuestion(null,navigation)
    }
    private fun openQuestion (prevAnswer: Int?,question: Int){         //start with 0
        fragment    = Fragment1_quiz.newInstance(prevAnswer,question)
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,fragment) .commit()
    }
    private fun openResult(){
        val result: Fragment = Result.newInstance()
        val transaction: FragmentTransaction= supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,result).commit()
    }
}