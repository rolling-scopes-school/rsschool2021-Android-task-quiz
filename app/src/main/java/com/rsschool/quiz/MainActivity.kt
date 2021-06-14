package com.rsschool.quiz

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), FragmentQuiz.ActionPerformedListener {


    private lateinit var binding: ActivityMainBinding
    override fun onActionPerformed(result: Map<Int,Int>) {
        openResult(result)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openQuiz(0)
    }
    private fun openQuiz (pageNumber: Int){         //start with 0
        val fragment : Fragment = FragmentQuiz.newInstance(pageNumber)
        val transaction :FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,fragment) .commit()
    }
    private fun openResult(result : Map<Int,Int>){
        val result: Fragment = Result.newInstance(result)
        val transaction: FragmentTransaction= supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,result).commit()
    }


}