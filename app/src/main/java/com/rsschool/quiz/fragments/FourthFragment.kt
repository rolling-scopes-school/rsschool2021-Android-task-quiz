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
import com.rsschool.quiz.QuestionObject.FourthFragment
import com.rsschool.quiz.R
import com.rsschool.quiz.R.layout.fragment_quiz
import com.rsschool.quiz.databinding.FragmentQuizBinding

class FourthFragment : Fragment(fragment_quiz) {
    private var listener: FragmentInterface? = null
    private val binding by viewBinding(FragmentQuizBinding::bind)
    private var radioButtonId = -1

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? FragmentInterface
        context.theme?.applyStyle(R.style.Theme_Quiz_Fourth, true)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(context, R.color.deep_purple_100_dark)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = "Вопрос 4"
        if (FourthFragment.checkedButtonId > 0) {
            view.findViewById<RadioButton>(FourthFragment.checkedButtonId).isChecked = true
        }
        val array = resources.getStringArray(R.array.answersFour)
        binding.question.text = resources.getString(R.string.questionFour)
        MethodsFragment().setRes(array, binding)
        nextButton()
        backButton()
    }

    private fun nextButton() {
        if (FourthFragment.checkedButtonId < 1) binding.nextButton.isEnabled = false
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            binding.nextButton.isEnabled = true
        }
        binding.nextButton.setOnClickListener {
            checkAnswer()
            listener?.fifthFragmentInt()
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
        FourthFragment.checkedAnswer = selectedAnswer
        if (selectedAnswer == array[3]) FourthFragment.score = 1
        else FourthFragment.score = 0
        FourthFragment.checkedButtonId = radioButtonId
    }
    private fun saveChoiceAndGoTo(){
        radioButtonId = binding.radioGroup.checkedRadioButtonId
        FourthFragment.checkedButtonId = radioButtonId
        listener?.thirdFragmentInt()
    }
}