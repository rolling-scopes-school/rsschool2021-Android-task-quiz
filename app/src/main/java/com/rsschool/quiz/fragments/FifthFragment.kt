package com.rsschool.quiz.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rsschool.quiz.FragmentInterface
import com.rsschool.quiz.MethodsFragment
import com.rsschool.quiz.QuestionObject.FifthFragment
import com.rsschool.quiz.R
import com.rsschool.quiz.R.layout.fragment_quiz
import com.rsschool.quiz.databinding.FragmentQuizBinding

class FifthFragment : Fragment(fragment_quiz) {
    private var listener: FragmentInterface? = null
    private val binding by viewBinding(FragmentQuizBinding::bind)
    private var radioButtonId = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FragmentInterface
        context.theme?.applyStyle(R.style.Theme_Quiz_Fifth, true)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(context, R.color.light_green_100_dark)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = "Вопрос 5"
        binding.nextButton.text = "Завершить"
        if (FifthFragment.checkedButtonId > 0) {
            view.findViewById<RadioButton>(FifthFragment.checkedButtonId).isChecked = true
        }

        val array = resources.getStringArray(R.array.answersFive)
        binding.question.text = resources.getString(R.string.questionFive)
        MethodsFragment().setRes(array, binding)
        nextButton()
        backButton()
    }

    private fun nextButton() {
        if (FifthFragment.checkedButtonId < 1) binding.nextButton.isEnabled = false
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            binding.nextButton.isEnabled = true
        }
        binding.nextButton.setOnClickListener {
            checkAnswer()
            listener?.resultFragmentInt()
        }
    }

    private fun backButton() {
        val backButton = binding.previousButton
        backButton.setOnClickListener {
            saveChoiceAndGoTo()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            saveChoiceAndGoTo()
        }
        binding.toolbar.setOnClickListener {
            saveChoiceAndGoTo()
        }
    }

    private fun checkAnswer() {
        radioButtonId = binding.radioGroup.checkedRadioButtonId
        FifthFragment.checkedButtonId = radioButtonId
        val radioButton = view?.findViewById<RadioButton>(radioButtonId)
        val selectedAnswer = radioButton?.text.toString()
        val array = resources.getStringArray(R.array.answersRight)
        if (selectedAnswer == array[4]) FifthFragment.score = 1
        else FifthFragment.score = 0
        FifthFragment.checkedAnswer = selectedAnswer
    }
    private fun saveChoiceAndGoTo(){
        radioButtonId = binding.radioGroup.checkedRadioButtonId
        FifthFragment.checkedButtonId = radioButtonId
        listener?.fourthFragmentInt()
    }
}