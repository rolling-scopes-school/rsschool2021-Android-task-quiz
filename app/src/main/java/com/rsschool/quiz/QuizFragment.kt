package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.question.text = "question = $question"

        binding.nextButton.setOnClickListener {
            listener?.showNext()
        }

        binding.previousButton.setOnClickListener {
            listener?.showPrevious()
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