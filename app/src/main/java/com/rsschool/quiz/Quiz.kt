package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.Fragment1QuizBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Quiz : Fragment() {

    private var _binding:               Fragment1QuizBinding? = null
    private val binding get()               = requireNotNull (_binding)
    private var listener:               ActionPerformedListener? = null
    private var answer:                 Int = -1                            // for answers (radioButton)
    private var previousAnswer:         Int = -1                            // answer to previous question
    private var questionNumber:         Int = -1                            // current question number
    private lateinit var questionList:  List<Question>                      // list of questions
    private lateinit var radioContent:  ArrayList<RadioButton>              // arrayList of radioButtons
    private lateinit var outViews:      ArrayList<View>                     // array for view of toolbar with describing

    interface ActionPerformedListener {
        fun onNextButton (answer: Int)
        fun onPreviousButton (answer: Int)
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
            previousAnswer = it.getInt(ARG_PARAM1)                          // get from mainActivity
            questionNumber = it.getInt(ARG_PARAM2)
        }                                                                   // apply theme
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
        outViews        = arrayListOf()                                     // init
        questionList    = ResRepo().getQuestions()                          // get List of Questions

        with (binding){
            radioContent = ArrayList()                                      // collection of radioButtons
                    radioContent.add(optionOne)
                    radioContent.add(optionTwo)
                    radioContent.add(optionThree)
                    radioContent.add(optionFour)
                    radioContent.add(optionFive)

            toolbar.findViewsWithText(                                      // find back Button and send to the arrayList
                outViews,"Back to the previous question",               //TODO Rework this method to valid method
                View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)

            radioGroup.setOnCheckedChangeListener {                         //radioGroup listener
                    group, checkedId ->
                answer = group.indexOfChild(group.findViewById(checkedId))
                nextButton.isEnabled = true
            }
           
            nextButton.setOnClickListener {                                 // nextButton listener
                listener?.onNextButton(answer)
            }

            previousButton.setOnClickListener {                             // previousButton listener
                listener?.onPreviousButton(answer)
            }

            toolbar.setNavigationOnClickListener {                          // Navigation listener
                listener?.onPreviousButton(answer)
            }

        }
        drawFragment()                                                      // draw Fragment

    }

    private fun drawFragment(){                                             // method for drawing Fragment_quiz
        buttonsDraw()                                                       // Visible/Invisible Buttons and Text of nextButton
        fillQuestionAndAnswers()                                            // Print question and answers
    }

    private fun buttonsDraw (){
        with(binding){
            nextButton.isEnabled = isChecked(radioContent)                  // disable nextButton if !checked radioGroup

            if(questionNumber == 0) {                                       // if first question:
                previousButton.isVisible = false                            // visibility of the Previous Button
                outViews[0].visibility   = View.INVISIBLE                     // visibility of the toolbarPrev Button
            }
            else {                                                          // else both - visible
                previousButton.isVisible = true
                outViews[0].visibility   = View.VISIBLE
            }
                                                                            // if question number = 5 rename nextButton
            if(questionNumber == 4) nextButton.text = getString(R.string.submit)
            else                    nextButton.text = getString(R.string.next)
        }

    }
    private fun isChecked(content: ArrayList<RadioButton>): Boolean{
        content.forEach { if(it.isChecked) return true }                     // return true if any of radioButtons = checked
        return false                                                         // else
    }
    private fun fillQuestionAndAnswers(){
        for (index in radioContent.indices){                                 // fill radioButton's text with possible answers
            radioContent[index].text = questionList[questionNumber].answers[index]
        }

        with(binding){                                                       // print text of question into toolBar
            question.text = questionList[questionNumber].question
            if(questionNumber >= 0 || questionNumber < 4)
                toolbar.title = "Question ${questionNumber+1}"               // plus 1 because of pageIndex starts with '0'

            if(previousAnswer != -1)                                         // check radioButton with previous answer
                (radioGroup[previousAnswer] as RadioButton).isChecked = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int?,param2: Int) :Quiz {
            return Quiz().apply {
                arguments = bundleOf().apply {
                    putInt(ARG_PARAM1, param1 ?: -1)
                    putInt(ARG_PARAM2, param2)
                }
            }
        }
    }
}