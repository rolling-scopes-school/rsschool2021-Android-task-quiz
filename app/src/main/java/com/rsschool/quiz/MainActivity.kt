package com.rsschool.quiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openQuiz()
    }
    private fun openQuiz (){
        val fragment : Fragment = FragmentQuiz()
        val transaction :FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(binding.Container.id,fragment)
        transaction.commit()
    }
}