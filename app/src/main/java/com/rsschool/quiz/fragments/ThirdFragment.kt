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
import com.rsschool.quiz.QuestionObject.ThirdFragment
import com.rsschool.quiz.R
import com.rsschool.quiz.R.layout.fragment_quiz
import com.rsschool.quiz.databinding.FragmentQuizBinding

class ThirdFragment : Fragment(fragment_quiz) {
    private var listener: FragmentInterface? = null
    private val binding by viewBinding(FragmentQuizBinding::bind)
    private var radioButtonId = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FragmentInterface
        context.theme?.applyStyle(R.style.Theme_Quiz_Third, true)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(context, R.color.cyan_100_dark)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = "Вопрос 3"
        if (ThirdFragment.checkedButtonId > 0) {
            view.findViewById<RadioButton>(ThirdFragment.checkedButtonId).isChecked = true
        }
        val array = resources.getStringArray(R.array.answersThree)
        binding.question.text = resources.getString(R.string.questionThree)
        MethodsFragment().setRes(array, binding)
        nextButton()
        backButton()
    }

    private fun nextButton() {
        if (ThirdFragment.checkedButtonId < 1) binding.nextButton.isEnabled = false
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            binding.nextButton.isEnabled = true
        }
        binding.nextButton.setOnClickListener {
            checkAnswer()
            listener?.fourthFragmentInt()
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
        val radioButton = view?.findViewById<RadioButton>(radioButtonId)
        val selectedAnswer = radioButton?.text.toString()
        val array = resources.getStringArray(R.array.answersRight)
        ThirdFragment.checkedAnswer = selectedAnswer
        if (selectedAnswer == array[2]) ThirdFragment.score = 1
        else ThirdFragment.score = 0
        ThirdFragment.checkedButtonId = radioButtonId
    }
    private fun saveChoiceAndGoTo(){
        radioButtonId = binding.radioGroup.checkedRadioButtonId
        ThirdFragment.checkedButtonId = radioButtonId
        listener?.secondFragmentInt()
    }
}