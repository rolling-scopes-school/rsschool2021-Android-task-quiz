package com.rsschool.quiz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.databinding.Fragment1QuizBinding

class MainActivity : AppCompatActivity(), Fragment1_quiz.ActionPerformedListener {


    private lateinit var binding: ActivityMainBinding
    override fun onActionPerformed(result: Map<Int,Int>) {
        openResult(result)
    }
    /*override fun onActionPerformed(question: Int) {
        openQuestion(question)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openQuestion(0,0)
    }
    private fun openQuestion (prevAnswer: Int,question: Int){         //start with 0
        val fragment : Fragment = Fragment1_quiz.newInstance(prevAnswer,question)
        val transaction :FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,fragment) .commit()
    }
    private fun openResult(result : Map<Int,Int>){
        val result: Fragment = Result.newInstance(result)
        val transaction: FragmentTransaction= supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,result).commit()
    }


}