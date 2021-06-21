package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.Fragment1QuizBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
//TODO TRY ON API =16!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
class Quiz : Fragment() {

    private var _binding: Fragment1QuizBinding? = null
    private val binding get() = requireNotNull (_binding)
    private var listener: ActionPerformedListener? = null
    private var answer: Int = -1
    private var previousAnswer: Int = -1
    private var questionNumber: Int = -1
    private lateinit var questionList: List<Question>
    private lateinit var radioContent: ArrayList<RadioButton>
    private lateinit var outViews: ArrayList<View>    //array for view of toolbar with describing

    interface ActionPerformedListener {
        fun nextQuestion (answer: Int, correct: Boolean)
        fun previousQuestion (answer: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionPerformedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let{
            previousAnswer = it.getInt(ARG_PARAM1)
            questionNumber = it.getInt(ARG_PARAM2)
        }
        val contextThemeWrapper: ContextThemeWrapper = when (questionNumber){
            0       -> ContextThemeWrapper(activity,R.style.Theme_Quiz_First)
            1       -> ContextThemeWrapper(activity,R.style.Theme_Quiz_Second)
            2       -> ContextThemeWrapper(activity,R.style.Theme_Quiz_Third)
            3       -> ContextThemeWrapper(activity,R.style.Theme_Quiz_Fourth)
            4       -> ContextThemeWrapper(activity,R.style.Theme_Quiz_Fifth)
            else    -> ContextThemeWrapper(activity,R.style.Theme_Quiz)
        }
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        _binding = Fragment1QuizBinding.inflate(localInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        outViews        = arrayListOf()
        questionList    = ResRepo().getQuestions()             //get List of Questions

        // creating an array with views of RadioButtons in RadioGroup
        with (binding){
            radioContent = ArrayList()                      //binding radioButtons
                    radioContent.add(optionOne)
                    radioContent.add(optionTwo)
                    radioContent.add(optionThree)
                    radioContent.add(optionFour)
                    radioContent.add(optionFive)

            toolbar.findViewsWithText(
                outViews,"Back to the previous question",   //TODO Rework this method to valid method
                View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)       //Find back Button and send to the arrayList

            radioGroup.setOnCheckedChangeListener {             //radioGroup listener
                    group, checkedId ->
                answer = group.indexOfChild(group.findViewById(checkedId))
                nextButton.isEnabled = true
                //TODO REMEMBERING NOT SUBMITTED -Done!!!
            }
           
            nextButton.setOnClickListener {                        //btnNext listener
                if(answer == questionList[questionNumber].correct )
                    listener?.nextQuestion(answer, true)
                else listener?.nextQuestion(answer, false)
            }
            previousButton.setOnClickListener {
                listener?.previousQuestion(answer)
            }   //btnPrev listener

            toolbar.setNavigationOnClickListener {              //Navigation listener
                //previousQuestion() TODO If pageIndex ==0 ....
                listener?.previousQuestion(answer)
            }

        }
        drawFragment()                //!!!draw Fragment

    }

    //method for drawing Fragment_quiz
    private fun drawFragment(){
        buttonsDraw()                              //Visible/Invisible Buttons and Text of btnNext
        fillQuestionAndAnswers()      //Print question and answers
    }

    private fun buttonsDraw (){
        //check answers for being answered
        binding.nextButton.isEnabled = isChecked(radioContent)     //disable next before check radioGroup

        if(questionNumber == 0) {
            binding.previousButton.isVisible = false               //visibility of the Previous Button
            outViews[0].visibility = View.INVISIBLE                //visibility of the toolbarPrev Button
        }
        else {
            binding.previousButton.isVisible = true
            outViews[0].visibility = View.VISIBLE
        }

        //if page = 5 rename button.
        if(questionNumber == 4) binding.nextButton.text = getString(R.string.submit)
        else binding.nextButton.text = getString(R.string.next)
    }
    private fun isChecked(content: ArrayList<RadioButton>): Boolean{
        content.forEach { if(it.isChecked) return true }
        return false
    }
    private fun fillQuestionAndAnswers(){
        //fill they text with answers
        for (index in radioContent.indices){
            radioContent[index].text =
                questionList[questionNumber].answers[index]
        }
        //Print text of question
        binding.question.text = questionList[questionNumber].question
        //toolbarText
        if(questionNumber >= 0 || questionNumber < 4)
            binding.toolbar.title = "Question ${questionNumber+1}"           //Plus 1 because of pageIndex starts with '0'
        if(previousAnswer != -1)
            (binding.radioGroup[previousAnswer] as RadioButton).isChecked = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int?,param2: Int) :Quiz {
            return Quiz().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1 ?: -1)
                    putInt(ARG_PARAM2, param2)
                }
            }
        }
    }
}