package com.rsschool.quiz.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rsschool.quiz.FragmentInterface
import com.rsschool.quiz.MethodsFragment
import com.rsschool.quiz.QuestionObject.FirstFragment
import com.rsschool.quiz.R
import com.rsschool.quiz.R.layout.fragment_quiz
import com.rsschool.quiz.databinding.FragmentQuizBinding


class FirstFragment : Fragment(fragment_quiz) {
    private var listener: FragmentInterface? = null
    private val binding by viewBinding(FragmentQuizBinding::bind)
    private var radioButtonId = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FragmentInterface
        context.theme?.applyStyle(R.style.Theme_Quiz_First, true)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(context, R.color.deep_orange_100_dark)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = "Вопрос 1"
        binding.toolbar.navigationIcon = null
        binding.previousButton.visibility = View.INVISIBLE
        if (FirstFragment.checkedButtonId > 0) {
            view.findViewById<RadioButton>(FirstFragment.checkedButtonId).isChecked = true
        }
        val array = resources.getStringArray(R.array.answersOne)
        binding.question.text = resources.getString(R.string.questionOne)
        MethodsFragment().setRes(array, binding)
        nextButton()
    }

    private fun nextButton() {
        if (FirstFragment.checkedButtonId < 1) binding.nextButton.isEnabled = false
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            binding.nextButton.isEnabled = true
        }

        binding.nextButton.setOnClickListener {
            checkAnswer()
            listener?.secondFragmentInt()
        }
    }

    private fun checkAnswer() {
        radioButtonId = binding.radioGroup.checkedRadioButtonId
        val radioButton = view?.findViewById<RadioButton>(radioButtonId)
        val selectedAnswer = radioButton?.text.toString()
        FirstFragment.checkedAnswer = selectedAnswer
        val array = resources.getStringArray(R.array.answersRight)
        if (selectedAnswer == array[0]) FirstFragment.score = 1 else FirstFragment.score = 0
        FirstFragment.checkedButtonId = radioButtonId
    }


}