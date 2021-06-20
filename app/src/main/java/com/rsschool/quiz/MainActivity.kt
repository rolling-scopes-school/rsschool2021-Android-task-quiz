package com.rsschool.quiz

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.rsschool.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AppNavigation {

    private lateinit var binding: ActivityMainBinding
    var current = 0
    val max = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        startQuiz()
    }

    override fun startQuiz() {
        current = 0

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, QuizFragment.newInstance(current))
            .commit()
    }

    override fun showNext() {
        if (current == max){
            showResult()
        } else {
            current++

            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, QuizFragment.newInstance(current))
                .addToBackStack("")
                .commit()
        }
    }

    override fun showPrevious() {
        if (current > 0) {
            current--
            supportFragmentManager.popBackStack()
        }
    }

    override fun showResult() {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, ResultFragment.newInstance())
            .commit()
    }

    override fun sendResult() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_TEXT, "some result")
            type = "text/plain"
        }

        startActivity(Intent.createChooser(intent, null))
    }

    override fun closeQuiz(){
        finish()
    }
}