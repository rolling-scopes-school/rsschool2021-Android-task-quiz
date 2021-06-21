package com.rsschool.quiz.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.forEachIndexed
import androidx.fragment.app.Fragment
import com.rsschool.quiz.QuizApplication
import com.rsschool.quiz.R
import com.rsschool.quiz.data.Question
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuizFragment: Fragment(R.layout.fragment_quiz) {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!! // Only valid between onCreateView and onDestroyView.
    private var quizList: Array<Question>? = null
    private lateinit var viewModel: QuizViewModel
    private var questionNumber = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loadSavedState()
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        viewModel = (requireActivity().application as QuizApplication)
            .appContainer.getQuizViewModel(this)
        quizList?.let { viewModel.setQuizList(it) }
        initView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {

        binding.radioGroup.setOnCheckedChangeListener{ _, itemId ->
            viewModel.onCheckedChange(itemId)
        }

        binding.nextButton.setOnClickListener{
            viewModel.onNextClick()
            val inst =  newInstance(quizList!!, questionNumber + 1)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, inst, "Question${0}")
                .addToBackStack(null)
                .commit()
        }

        binding.previousButton.setOnClickListener{
            onBackPressed()
        }

        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }

        viewModel.enableNext.observe(viewLifecycleOwner, this::setNextButtonState )
        viewModel.enablePrev.observe(viewLifecycleOwner, this::setPrevButtonState )
        viewModel.questionUpdate.observe(viewLifecycleOwner, this::update)
        viewModel.currentItemId.observe(viewLifecycleOwner, this::updateItemSelection)
    }

    fun onBackPressed() {
        with(requireActivity()){
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
            } else {
                onBackPressed()
            }
        }
    }

    private fun updateItemSelection(id : Int) {
        try {
            binding.radioGroup.check(id)
        } catch (throwable: Throwable){
            Log.d(TAG, "Question update Error")
        }
    }

    private fun bindQuestion(question: Question){
        binding.question.text = question.text
        binding.radioGroup.forEachIndexed { index, rbView ->
            (rbView as RadioButton).text = question.choose[index]
        }
    }

    private fun update(id : Int){
        try {
            val list = quizList!!
            if (id in list.indices)
                bindQuestion(list[id])
        } catch (throwable: Throwable){
            Log.d(TAG, "Question update Error")
        }
    }

    private fun setNextButtonState(state : Boolean) {
        binding.nextButton.isEnabled = state
    }

    private fun setPrevButtonState(state : Boolean) {
        binding.previousButton.isEnabled = state
    }

    private fun loadSavedState(){
        val parcelableArray = requireArguments().getParcelableArray(QUIZ_KEY)
        if (parcelableArray != null)
            quizList =  parcelableArray.map { it as Question }.toTypedArray()
        questionNumber = requireArguments().getInt(NUMBER_KEY)
    }

    companion object {

        const val QUIZ_KEY = "QUIZ"
        const val NUMBER_KEY = "NUMBER"
        @JvmStatic
        fun newInstance(list: Array<Question>): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putParcelableArray(QUIZ_KEY, list)
            args.putInt(NUMBER_KEY, 0)
            fragment.arguments = args
            return fragment
        }

        @JvmStatic
        fun newInstance(list: Array<Question>, number: Int): QuizFragment {
            val fragment = QuizFragment()
            val args = Bundle()
            args.putParcelableArray(QUIZ_KEY, list)
            args.putInt(NUMBER_KEY, number)
            fragment.arguments = args

            return fragment
        }

        @JvmStatic
        fun newInstance() = QuizFragment()

        private val TAG = QuizFragment::class.java.simpleName
    }
}
