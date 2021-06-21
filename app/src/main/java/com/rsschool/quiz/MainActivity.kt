package com.rsschool.quiz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rsschool.quiz.data.QuizGame
import com.rsschool.quiz.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), AppNavigation {

    private lateinit var binding: ActivityMainBinding
    var current = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        startQuiz()
    }

    override fun startQuiz() {
        current = 0
        resetQuestions()
        changeTheme()

        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, QuizFragment.newInstance(current))
            .commit()
    }

    override fun showNext() {
        if (current == QuizGame.questionsList.size - 1) {
            showResult()
        } else {
            current++
            changeTheme()

            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, QuizFragment.newInstance(current))
                .addToBackStack("")
                .commit()
        }
    }

    private fun changeTheme() {
        setTheme(
                when (current) {
                    0 -> R.style.Theme_Quiz_First
                    1 -> R.style.Theme_Quiz_Second
                    2 -> R.style.Theme_Quiz_Third
                    3 -> R.style.Theme_Quiz_Forth
                    4 -> R.style.Theme_Quiz_Fifth
                    else -> R.style.Theme_Quiz_First
                }
        )

        changeStatusBarColor()
    }

    private fun changeStatusBarColor() {
        // don't know how do it from theme yet
        // and still had one bag
        window.statusBarColor = ContextCompat.getColor(
                baseContext, when (current) {
            0 -> R.color.deep_orange_100_dark
            1 -> R.color.yellow_100_dark
            2 -> R.color.light_green_100_dark
            3 -> R.color.cyan_100_dark
            4 -> R.color.deep_purple_100_dark
            else -> R.color.deep_orange_100_dark
        }
        )
    }


    override fun showPrevious() {
        if (current > 0) {
            current--
            changeTheme()
            supportFragmentManager.popBackStack()
        }
    }

    override fun showResult() {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, ResultFragment.newInstance())
            .commit()
    }

    override fun sendResult() {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_SUBJECT, "Quiz results")
            putExtra(Intent.EXTRA_TEXT, formResultText())
            selector = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
            }
        }

        startActivity(Intent.createChooser(intent, "Share result"))
    }

    private fun formResultText(): String {
        val result = StringBuilder()

        result.append("Your result: ${QuizGame.calcResult()}% \n\n")

        QuizGame.questionsList.forEach { question ->
            result.append(
                    "${question.id}) ${question.question}\n" +
                            "Your answer: ${question.userChoice}\n\n"
            )
        }
        return result.toString()
    }

    override fun closeQuiz() {
        finish()
    }

    private fun resetQuestions() {
        QuizGame.questionsList.forEach { question ->
            question.answers.shuffle()
            question.userChoice = ""
        }
    }
}