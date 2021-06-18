package com.rsschool.quiz.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rsschool.quiz.FragmentInterface
import com.rsschool.quiz.MethodsFragment
import com.rsschool.quiz.R.layout.fragment_result
import com.rsschool.quiz.databinding.FragmentResultBinding
import com.rsschool.quiz.QuestionObject.FirstFragment
import com.rsschool.quiz.QuestionObject.SecondFragment
import com.rsschool.quiz.QuestionObject.ThirdFragment
import com.rsschool.quiz.QuestionObject.FourthFragment
import com.rsschool.quiz.QuestionObject.FifthFragment
import com.rsschool.quiz.R
import kotlin.system.exitProcess

class ResultFragment : Fragment(fragment_result) {
    private var listener: FragmentInterface? = null
    private val binding by viewBinding(FragmentResultBinding::bind)
    private var stringResult: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FragmentInterface
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(context, R.color.black)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stringResult =
            (FirstFragment.score + SecondFragment.score +
                    ThirdFragment.score + FourthFragment.score +
                    FifthFragment.score).toString() + " из 5"
        binding.result.text = stringResult
        shareResult()
        backButton()
        closeApp()
    }

    private fun shareResult() {
        val resultForShare = "Твой результат: " +
                stringResult + "\n" + "1) " +
                resources.getString(R.string.questionOne) + "\n" + "Твой ответ: " +
                FirstFragment.checkedAnswer + "\n" + "2) " +
                resources.getString(R.string.questionTwo) + "\n" + "Твой ответ: " +
                SecondFragment.checkedAnswer + "\n" + "3) " +
                resources.getString(R.string.questionThree) + "\n" + "Твой ответ: " +
                ThirdFragment.checkedAnswer + "\n" + "4) " +
                resources.getString(R.string.questionFour)+ "\n" + "Твой ответ: " +
                FourthFragment.checkedAnswer + "\n" + "5) " +
                resources.getString(R.string.questionFive) + "\n" + "Твой ответ: " +
                FifthFragment.checkedAnswer

        binding.shareButton.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, resultForShare)
                putExtra(Intent.EXTRA_SUBJECT, "Результаты теста")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, "resultShare"))
        }
    }

    private fun backButton() {
        binding.restartButton.setOnClickListener {
            restartQuiz()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            restartQuiz()
        }
    }

    private fun restartQuiz() {
        MethodsFragment().resetObject()
        listener?.firstFragmentInt()

    }

    private fun closeApp() {
        binding.closeButton.setOnClickListener {
            activity?.finish()
            exitProcess(0)
        }
    }
}