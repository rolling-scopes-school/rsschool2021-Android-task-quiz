package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.Fragment1QuizBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
//TODO TRY ON API =16!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
class Fragment1_quiz : Fragment() {

    private var _binding: Fragment1QuizBinding? = null
    private val binding get() = requireNotNull (_binding)
    private var listener: ActionPerformedListener? = null
    private var previousAnswer: Int? = null
    private var questionNumber: Int? = null
    private lateinit var questionList: List<Question>
    private lateinit var radioContent: ArrayList<RadioButton>
    private var answer: Int? =null
    private val outViews: ArrayList<View> = arrayListOf()    //array for view of toolbar with describing

    interface ActionPerformedListener {
        fun nextQuestion (answer: Int, correct: Boolean)
        fun previousQuestion (previousAnswer: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionPerformedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO Elvis needed??
        arguments?.let{
            Log.d("myLogs","inter here ${it.getInt(ARG_PARAM2)}")
            previousAnswer = it.getInt(ARG_PARAM1)
            questionNumber = it.getInt(ARG_PARAM2)
        }
        Log.d("myLogs","$previousAnswer , $questionNumber")
        lateinit var contextThemeWrapper: ContextThemeWrapper
        when (questionNumber){
            0 -> contextThemeWrapper = ContextThemeWrapper(activity,R.style.Theme_Quiz_First)
            1 -> contextThemeWrapper = ContextThemeWrapper(activity,R.style.Theme_Quiz_Second)
            2 -> contextThemeWrapper = ContextThemeWrapper(activity,R.style.Theme_Quiz_Third)
            3 -> contextThemeWrapper = ContextThemeWrapper(activity,R.style.Theme_Quiz_Fourth)
            4 -> contextThemeWrapper = ContextThemeWrapper(activity,R.style.Theme_Quiz_Fifth)
        }
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        _binding = Fragment1QuizBinding.inflate(localInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionList = ResRepo().getQuestions()             //get List of Questions

        // creating an array with views of RadioButtons in RadioGroup
        with (binding){
            radioContent = ArrayList()                      //binding radioButtons
                    radioContent.add(optionOne)
                    radioContent.add(optionTwo)
                    radioContent.add(optionThree)
                    radioContent.add(optionFour)
                    radioContent.add(optionFive)

            toolbar.findViewsWithText(
                outViews,"Back to the previous question",   //TODO KOSTIL'
                View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)       //Find back Button and send to the arrayList

            drawFragment(questionNumber!!,radioContent)                //!!!draw Fragment

            radioGroup.setOnCheckedChangeListener {             //radioGroup listener
                    group, checkedId ->
                answer = group.indexOfChild(group.findViewById(checkedId))
                nextButton.isEnabled = true
            }
            //TODO GO ON from HERE
            nextButton.setOnClickListener {                        //btnNext listener
                if(answer == questionList[questionNumber!!].correct)
                listener?.nextQuestion(answer!!, true)
                else listener?.nextQuestion(answer!!, false)
            }
            previousButton.setOnClickListener {
                listener?.previousQuestion(previousAnswer!!)

                //previousQuestion() TODO If pageIndex ==0 ....
            }   //btnPrev listener

            toolbar.setNavigationOnClickListener {              //Navigation listener
                //previousQuestion() TODO If pageIndex ==0 ....
                listener?.previousQuestion(previousAnswer!!)
            }

        }


    }
    //TODO CHECK FOR NEED PAGEINDEX IN METHODS PARAMS
    //method for drawing Fragment_quiz
    private fun drawFragment(questionNumber: Int,radioContent: ArrayList<RadioButton>){
        buttonsDraw(questionNumber)                              //Visible/Invisible Buttons and Text of btnNext
        fillQuestionAndAnswers(questionNumber,radioContent)      //Print question and answers
    }

    private fun buttonsDraw (questionNumber: Int){
        //check answers for being answered
        if(previousAnswer != null) {
            binding.radioGroup.check(previousAnswer!!)                  //TODO NULLL
            binding.nextButton.isEnabled = true                        //if answered enable "next"
        } else binding.nextButton.isEnabled = false                    //disable next before check radioGroup

        if(questionNumber == 0) {
            binding.previousButton.isVisible = false                       //visibility of the Previous Button
            outViews[0].visibility = View.INVISIBLE         //visibility of the toolbarPrev Button
        }
        else {
            binding.previousButton.isVisible = true
            outViews[0].visibility = View.VISIBLE
        }

        //if page = 5 rename button.
        if(questionNumber == 4) binding.nextButton.text = getString(R.string.submit)
        else binding.nextButton.text = getString(R.string.next)
    }
    private fun fillQuestionAndAnswers(questionNumber: Int,radioContent: ArrayList<RadioButton>){
        //fill they text with answers
        for (index in radioContent.indices){
            radioContent[index].text =
                questionList[questionNumber].answers[index]
        }
        //Print text of question
        binding.question.text = questionList[questionNumber].question
        //toolbarText
        if(questionNumber >= 0 || questionNumber < 5)
            binding.toolbar.title = "Question ${questionNumber+1}"           //Plus 1 because of pageIndex starts with '0'
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int?,param2: Int) :Fragment1_quiz {
            val fragment = Fragment1_quiz()                   //great fragment
            val args = Bundle()                             //args for parameters
            if(param1 != null) {
                args.putInt(ARG_PARAM1, param1)
            }                 //put parameters
            args.putInt(ARG_PARAM2, param2)
            fragment.arguments = args
            Log.d("myLogs","$param1, $param2 bundle param2 ${args.getInt(ARG_PARAM2)}")
            return fragment                                 //return fragment into Main
        }
    }

}