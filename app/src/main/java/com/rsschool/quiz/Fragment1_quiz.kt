package com.rsschool.quiz

import android.app.ActionBar
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
import com.rsschool.quiz.databinding.Fragment1QuizBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Fragment1_quiz : Fragment() {

    private var _binding: Fragment1QuizBinding? = null
    private val binding get() = _binding!!
    private var listener: ActionPerformedListener? = null
    private var previousAnswer: Int? = null
    private var questionNumber: Int? = null
    private lateinit var question: List<Question>
    private lateinit var radioContent: ArrayList<RadioButton>
    /*private lateinit var radioGroup : RadioGroup
    private lateinit var txtQuestion : TextView
    private lateinit var toolbar: Toolbar
    private lateinit var btnNext: Button
    private lateinit var btnPrev: Button*/
    private var answer: Int? =null
    private val outViews: ArrayList<View> = arrayListOf<View>()    //array for view of toolbar with describing
    //!!!!!!!!!!!!!!!!*/
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
        _binding = Fragment1QuizBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        question = ResRepo().getQuestions()             //get List of Questions
        /*answers = mutableMapOf()                        //init array for answers TODO may be better to use boolean??
        //bind views TODO Delete unneedable values and use binding.View
        radioGroup  = binding.radioGroup                //radioGroup
        txtQuestion = binding.question                  //txtView. Displaying Question
        toolbar     = binding.toolbar                   //toolBar
        btnNext     = binding.nextButton                //btnNext
        btnPrev     = binding.previousButton            //btnPrev
        */

        // creating an array with views of RadioButtons in RadioGroup

        radioContent = ArrayList()                      //binding radioButtons
        radioContent.add(binding.optionOne)
        radioContent.add(binding.optionTwo)
        radioContent.add(binding.optionThree)
        radioContent.add(binding.optionFour)
        radioContent.add(binding.optionFive)


                                                            //TODO Elvis needed??
        previousAnswer = arguments?.getInt(ARG_PARAM1) ?: 0      //get previous answer
        questionNumber = arguments?.getInt(ARG_PARAM2) ?: 0      //get number of page (Question) or 0

        binding.toolbar.findViewsWithText(
            outViews,"Back to the previous question",   //TODO KOSTIL'
            View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION)       //Find back Button and send to the arrayList

        drawFragment(questionNumber!!,radioContent)                //!!!draw Fragment

        binding.radioGroup.setOnCheckedChangeListener {             //radioGroup listener
                group, checkedId ->
            answer = group.indexOfChild(group.findViewById(checkedId))
            //binding.nextButton.isEnabled = true
            }

        binding.nextButton.setOnClickListener {                        //btnNext listener

           // nextQuestion() TODO OUT TO MAIN_ACTIVITY WITH ANSWER
        }
        //TODO SAVE RASIOGROUP CHECK BEFORE PUSH "NEXT" to able to see your potential answer when "btnPrev -> btnNext"
        binding.previousButton.setOnClickListener {
            //previousQuestion() TODO If pageIndex ==0 ....
        }   //btnPrev listener

        binding.toolbar.setNavigationOnClickListener {              //Navigation listener
            //previousQuestion() TODO If pageIndex ==0 ....
        }
    }
    //TODO CHECK FOR NEED PAGEINDEX IN METHODS PARAMS
    //method for drawing Fragment_quiz
    private fun drawFragment(questionNumber: Int,radioContent: ArrayList<RadioButton>){
        buttonsDraw(questionNumber)                              //Visible/Invisible Buttons and Text of btnNext
        fillQuestionAndAnswers(questionNumber,radioContent)      //Print question and answers
    }
   /* private fun getAnswerAndDrop (){                                //TODO Get Answer and drop radioGroup
        when (binding.radioGroup.checkedRadioButtonId){
            binding.optionOne.id    -> answers[pageIndex] = binding.optionOne.id
            binding.optionTwo.id    -> answers[pageIndex] = binding.optionTwo.id
            binding.optionThree.id  -> answers[pageIndex] = binding.optionThree.id
            binding.optionFour.id   -> answers[pageIndex] = binding.optionFour.id
            binding.optionFive.id   -> answers[pageIndex] = binding.optionFive.id
        }
        binding.radioGroup.clearCheck()
    }*/

    /*
    private fun nextQuestion(){                             //TODO check for null and 0
       if(pageIndex == 4)
           listener?.onActionPerformed(answers)
           else drawFragment(++pageIndex,radioContent)
    }
    private fun previousQuestion(){                         //todo same
        drawFragment(--pageIndex,radioContent)              //TODO why it is nullable?
        previousAnswer(pageIndex)
    }
    private fun previousAnswer(pageIndex: Int){
        radioGroup.check(answers[pageIndex]!!)
    }*/
    private fun buttonsDraw (questionNumber: Int){
        //check answers for being answered
        if(answer != null) {
            binding.radioGroup.check(previousAnswer!!)
            binding.nextButton.isEnabled = true                        //if answered enable "next"
        } else binding.nextButton.isEnabled = false                    //disable next before check radioGroup

        if(questionNumber==0) {
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
                question[questionNumber].answers[index]
        }
        //Print text of question
        binding.question.text = question[questionNumber].question
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
        fun newInstance(param1: Int,param2: Int) :Fragment1_quiz {
            val fragment = Fragment1_quiz()                   //great fragment
            val args = Bundle()                             //args for parameters
            args.putInt(ARG_PARAM1, param1)                 //put parameters
            args.putInt(ARG_PARAM2, param2)
            return fragment                                 //return fragment into Main
        }
    }

}