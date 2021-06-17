package com.rsschool.quiz.presentation

// TODO: import androidx.lifecycle.LifecycleObserver
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import com.rsschool.quiz.R
import com.rsschool.quiz.QuizApplication
import com.rsschool.quiz.data.Question
import com.rsschool.quiz.databinding.FragmentQuizBinding
import com.rsschool.quiz.presentation.QuizViewModel.Companion.INVALID_QUESTION

class QuizFragment: Fragment(R.layout.fragment_quiz)/*TODO: , LifecycleObserver*/ {

    private lateinit var viewModel: QuizViewModel
    private var _binding: FragmentQuizBinding? = null
    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private var currentQuestion: Question? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        // TODO: Use the ViewModel
        viewModel = (requireActivity().application as QuizApplication)
            .appContainer.getQuizViewModel(this)
        initView()
        viewModel.loadQuestions()
        viewModel.questionList.observe(viewLifecycleOwner, this::loadQuestionList)
//        viewModel.question.observe(viewLifecycleOwner, this::loadQuestion)
    }


    private fun loadQuestionList(list: List<Question>) {
        if (!list.isNullOrEmpty())
            viewModel.getQuestionById(0)
    }

        private fun loadQuestion(question: Question) {
        if (question.id != INVALID_QUESTION){
            bindQuestion(question)
        }
    }

    private fun bindQuestion(question: Question){
        binding.question.text = question.text
        binding.radioGroup.forEachIndexed { index, rbView ->
            (rbView as RadioButton).text = question.choose[index]
        }
    }

    private fun initView() {

        viewModel.getPreviousQuestion()
        viewModel.getQuestionById(0)

        binding.nextButton.setOnClickListener{
            viewModel.checkAnswer(0, binding.radioGroup.checkedRadioButtonId)
            viewModel.getNextQuestion()
        }

        binding.previousButton.setOnClickListener{
            viewModel.getPreviousQuestion()
        }
    }

    private fun setQuestionList(list: List<Question>){
        // TODO: Use the Quiz
        Log.d("LIST:", list.joinToString(" "))
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        @JvmStatic
        fun newInstance() = QuizFragment()

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}
