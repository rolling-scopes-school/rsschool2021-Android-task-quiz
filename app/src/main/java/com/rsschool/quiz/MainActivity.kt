package com.rsschool.quiz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var answers: MutableMap<Int,Boolean>
    override fun nextQuestion(answer: Int, correct: Boolean) {
        Log.d("myLogs","$correct, $answer")
        answers[navigation] = correct     // pair question to correct answer?
        navigation++
        if(navigation!=5) {
            transaction.remove(fragment)
            fragment = Fragment1_quiz.newInstance(answer, navigation)
            transaction = supportFragmentManager.beginTransaction()
            transaction.replace(binding.Container.id, fragment).commit()
        } else openResult()
    }

    override fun previousQuestion(questionNumber: Int, previousAnswer: Int) {
        navigation--
        transaction.remove(fragment)
        fragment = Fragment1_quiz.newInstance(previousAnswer, navigation)
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id, fragment).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        answers= mutableMapOf()
        openQuestion(null,navigation)
    }
    private fun openQuestion (prevAnswer: Int?,question: Int){         //start with 0
        fragment = Fragment1_quiz.newInstance(prevAnswer,question)
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,fragment) .commit()
    }
    private fun openResult(){
        val result: Fragment = Result.newInstance()
        val transaction: FragmentTransaction= supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,result).commit()
    }




}