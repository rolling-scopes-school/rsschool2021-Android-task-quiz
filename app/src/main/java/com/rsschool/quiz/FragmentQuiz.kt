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
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentQuiz : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private var listener: ActionPerformedListener? = null
    private val question = ResRepo().getQuestions()
    private var page : Int? = null
    private lateinit var radioContent: ArrayList<RadioButton>
    private lateinit var radioGroup : RadioGroup
    private lateinit var txtQuestion : TextView
    private lateinit var toolbar: Toolbar
    private lateinit var btnNext: Button
    private lateinit var btnPrev: Button
    //todo NAVIGATION BUTTON??????

    interface ActionPerformedListener {
        fun onActionPerformed (result: Array<Int>)
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

        radioGroup  = binding.radioGroup                 //bind views
        txtQuestion = binding.question
        toolbar     = binding.toolbar
        btnNext     = binding.nextButton
        btnPrev     = binding.previousButton
        page = arguments?.getInt(ARG_PARAM1)  ?: 0      //get # of page (Question)

        radioContent = ArrayList<RadioButton>()     //binding radioButtons
        radioContent.add(binding.optionOne)
        radioContent.add(binding.optionTwo)
        radioContent.add(binding.optionThree)
        radioContent.add(binding.optionFour)
        radioContent.add(binding.optionFive)

                                                        //todo Elvis?!?!?!?!
        fillFragment(page!!,radioContent)                            //fill fragment with question and answers
        btnNext.setOnClickListener { nextQuestion() }
        btnPrev.setOnClickListener { previousQuestion() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //method for filling Fragment_quiz with information
    private fun fillFragment(_Question: Int,radioContent: ArrayList<RadioButton>){
        // creating an array with views of RadioButtons in RadioGroup

        //TODO CHECK ALL THIS METHOD
        //fill they text with answers
        for (i in radioContent.indices){
            radioContent[i].text = question[_Question].answers[i]
        }
        //Print text of question
        txtQuestion.text = question[_Question].question
        //toolbarText
        toolbar.title = "Question ${_Question+1}"
    }
    private fun nextQuestion(){                             //TODO check for null and 0
        fillFragment(page!!.inc(),radioContent)
    }
    private fun previousQuestion(){                         //todo same
        fillFragment(page!!.dec(),radioContent)
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