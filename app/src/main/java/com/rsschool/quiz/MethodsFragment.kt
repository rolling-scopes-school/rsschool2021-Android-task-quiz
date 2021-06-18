package com.rsschool.quiz


import androidx.appcompat.app.AppCompatActivity
import com.rsschool.quiz.QuestionObject.FirstFragment
import com.rsschool.quiz.QuestionObject.SecondFragment
import com.rsschool.quiz.QuestionObject.ThirdFragment
import com.rsschool.quiz.QuestionObject.FourthFragment
import com.rsschool.quiz.QuestionObject.FifthFragment
import com.rsschool.quiz.databinding.FragmentQuizBinding
import java.util.*


class MethodsFragment: AppCompatActivity() {

    fun setRes(array: Array<String>,binding: FragmentQuizBinding){
        binding.optionOne.text = array[0]
        binding.optionTwo.text = array[1]
        binding.optionThree.text = array[2]
        binding.optionFour.text = array[3]
        binding.optionFive.text = array[4]
    }

    fun resetObject(){
        FirstFragment.score = 0
        FirstFragment.checkedAnswer = null
        FirstFragment.checkedButtonId = 0
        SecondFragment.score = 0
        SecondFragment.checkedAnswer = null
        SecondFragment.checkedButtonId = 0
        ThirdFragment.score = 0
        ThirdFragment.checkedAnswer = null
        ThirdFragment.checkedButtonId = 0
        FourthFragment.score = 0
        FourthFragment.checkedAnswer = null
        FourthFragment.checkedButtonId = 0
        FifthFragment.score = 0
        FifthFragment.checkedAnswer = null
        FifthFragment.checkedButtonId = 0
    }
}