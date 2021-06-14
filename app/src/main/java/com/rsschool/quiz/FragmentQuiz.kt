package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentQuiz : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private var listener: ActionPerformedListener? = null
    private var pageIndex = 0
    private lateinit var question: List<Question>
    private lateinit var radioContent: ArrayList<RadioButton>
    private lateinit var radioGroup : RadioGroup
    private lateinit var txtQuestion : TextView
    private lateinit var toolbar: Toolbar
    private lateinit var btnNext: Button
    private lateinit var btnPrev: Button
    private lateinit var answers: MutableMap<Int,Int>
    //todo NAVIGATION BUTTON??????

    interface ActionPerformedListener {
        fun onActionPerformed (answers: Map<Int,Int>)
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
        _binding = FragmentQuizBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        question = ResRepo().getQuestions()             //get List of Questions
        answers = mutableMapOf()                        //init array for answers TODO may be better to use boolean??
        //bind views
        radioGroup  = binding.radioGroup                //radioGroup
        txtQuestion = binding.question                  //txtView. Displaying Question
        toolbar     = binding.toolbar                   //toolBar //TODO xz zachem poka..
        btnNext     = binding.nextButton                //btnNext
        btnPrev     = binding.previousButton            //btnPrev
        // creating an array with views of RadioButtons in RadioGroup
        radioContent = ArrayList()                      //binding radioButtons
        radioContent.add(binding.optionOne)
        radioContent.add(binding.optionTwo)
        radioContent.add(binding.optionThree)
        radioContent.add(binding.optionFour)
        radioContent.add(binding.optionFive)

        pageIndex = arguments?.getInt(ARG_PARAM1) ?: 0      //get number of page (Question) or 0
                                                            //todo Elvis?!?!?!?!
        drawFragment(pageIndex,radioContent)                //!!!draw Fragment
        radioGroup.setOnCheckedChangeListener {             //radioGroup listener
                group, checkedId -> btnNext.isEnabled = true }
        btnNext.setOnClickListener {                        //btnNext listener
            getAnswerAndDrop()
            nextQuestion()
        }
        btnPrev.setOnClickListener { previousQuestion() }   //btnPrev listener
    }

    //method for drawing Fragment_quiz
    private fun drawFragment(pageIndex: Int,radioContent: ArrayList<RadioButton>){
        //TODO CHECK ALL THIS METHOD
        //check answers for being answered
        if(answers[pageIndex] != null) {
            radioGroup.check(answers[pageIndex]!!)
            btnNext.isEnabled = true                        //if answered enable "next"
        } else btnNext.isEnabled = false                    //disable next before check radioGroup
        btnPrev.isVisible = pageIndex != 0                  //visibility of the Previous Button

        //if page = 5 rename button.
        if(pageIndex == 4) btnNext.text = "SUBMIT"

        fillQuestionAndAnswers(pageIndex,radioContent)      //Print question and answers
    }
    private fun getAnswerAndDrop (){                                //TODO Get Answer and drop radioGroup
        when (radioGroup.checkedRadioButtonId){
            binding.optionOne.id    -> answers[pageIndex] = binding.optionOne.id
            binding.optionTwo.id    -> answers[pageIndex] = binding.optionTwo.id
            binding.optionThree.id  -> answers[pageIndex] = binding.optionThree.id
            binding.optionFour.id   -> answers[pageIndex] = binding.optionFour.id
            binding.optionFive.id   -> answers[pageIndex] = binding.optionFive.id
        }
        radioGroup.clearCheck()
    }
    private fun nextQuestion(){                             //TODO check for null and 0
       if(pageIndex == 4)
           listener?.onActionPerformed(answers)
           else drawFragment(++pageIndex,radioContent)
    }
    private fun previousQuestion(){                         //todo same
        drawFragment(--pageIndex,radioContent)              //TODO why it is nullable?
        previousAnswer(pageIndex)
    }
    private fun previousAnswer(pageIngex: Int){
        radioGroup.check(answers[pageIndex]!!)
    }
    private fun fillQuestionAndAnswers(pageIndex: Int,radioContent: ArrayList<RadioButton>){
        //fill they text with answers
        for (index in radioContent.indices){
            radioContent[index].text =
                question[pageIndex].answers[index]
        }
        //Print text of question
        txtQuestion.text = question[pageIndex].question
        //toolbarText
        if(pageIndex >= 0 || pageIndex < 5)
        toolbar.title = "Question ${pageIndex+1}"           //Plus 1 because of pageIndex starts with '0'
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) :FragmentQuiz {
            val fragment = FragmentQuiz()                   //great fragment
            val args = Bundle()                             //args for parameters
            args.putInt(ARG_PARAM1, param1)                 //put parameters
            // putString(ARG_PARAM2, param2)
            return fragment                                 //return fragment into Main
        }
    }

}