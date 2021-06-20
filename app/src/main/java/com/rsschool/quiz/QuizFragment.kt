package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.data.QuizGame
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private var question: Int = 0
    private var listener: AppNavigation? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        question = requireArguments().getInt(QUESTION_NUMBER)
        binding.toolbar.title = getString(R.string.question_label, (question+1).toString())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.nextButton.setOnClickListener {
            listener?.showNext()
        }

        binding.previousButton.setOnClickListener {
            listener?.showPrevious()
        }

        setQuestionOnFragment()
        setTheme()
    }

    private fun setTheme() {
        //
    }

    private fun setQuestionOnFragment() {
        val currentQuestion = QuizGame.questionsList[question]

        binding.question.text = currentQuestion.question
        binding.optionOne.text = currentQuestion.answers[0]
        binding.optionTwo.text = currentQuestion.answers[1]
        binding.optionThree.text = currentQuestion.answers[2]
        binding.optionFour.text = currentQuestion.answers[3]
        binding.optionFive.text = currentQuestion.answers[4]

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            currentQuestion.userChoice = (binding.radioGroup.findViewById(checkedId) as RadioButton).text.toString()
            binding.nextButton.isEnabled = true
        }

        if (currentQuestion.userChoice != ""){
            when (currentQuestion.answers.indexOf(currentQuestion.userChoice)){
                0 -> binding.optionOne.isChecked = true
                1 -> binding.optionTwo.isChecked = true
                2 -> binding.optionThree.isChecked = true
                3 -> binding.optionFour.isChecked = true
                4 -> binding.optionFive.isChecked = true
                else -> { }
            }

            binding.nextButton.isEnabled = true
        } else {
            binding.nextButton.isEnabled = false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? AppNavigation
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object{
        const val QUESTION_NUMBER  = "questionNumber"

        fun newInstance(question: Int) = QuizFragment().apply {
            arguments = bundleOf(QUESTION_NUMBER to question)
        }
    }
}