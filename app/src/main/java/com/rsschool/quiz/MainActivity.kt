package com.rsschool.quiz


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rsschool.quiz.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), Quiz.ActionPerformedListener, Result.ActionPerformedListener {

    private lateinit var fragment:      Fragment
    private lateinit var transaction:   FragmentTransaction
    private lateinit var binding:       ActivityMainBinding
    private          var navigation =   0 // number of active page
    private lateinit var answers:       MutableMap<Int,Int>
    // MutableMap<Int,MutableMap<Int,Boolean>>  //TODO try to sparseArray
                                        //<#question(navigation),<answer,isCorrect>
    //private lateinit var isCorrect:     MutableMap<Int,Boolean>// todo noneed?
    //private lateinit var keys:          MutableMap<Int,Int>
                                        //<navi,answer>

    private lateinit var questionList: List<Question>

    override fun nextQuestion(answer: Int) {//todo accept answer: Int, question: Question
        /*isCorrect[navigation]   = correct
        keys[navigation]        = answer*/
        answers[navigation]     = answer                                 // Save Question, answer and isCorrect
        answers.forEach { Log.d("myLogs","$it") }//todo!!!!!!
        if(navigation < 4) {
            openQuestion(answers[++navigation],navigation)
            setStatusBarColor()
        } else {
            navigation++
            setStatusBarColor()
            openResult()
        }
    }

    override fun previousQuestion(answer: Int) {
        answers[navigation]        = answer
        openQuestion(answers[--navigation],navigation)
        setStatusBarColor()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding     = ActivityMainBinding.inflate(layoutInflater)
        answers     = mutableMapOf()                       //init array for answers
        questionList    = ResRepo().getQuestions()      //get list of questions
        setContentView(binding.root)
        openQuestion(null,navigation)
    }
    private fun openQuestion (prevAnswer: Int?,question: Int){         //start with 0
        fragment    = Quiz.newInstance(prevAnswer,question)
        transaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,fragment) .commit()
    }
    private fun openResult(){
        val resultQuiz = "You result is: ${correctAnswersCount()}/5"
        val result:     Fragment             = Result.newInstance(resultQuiz)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,result).commit()
    }
    private fun clear(){
        navigation = 0
        answers.clear()
        /*isCorrect.clear()
        keys.clear()*/
        setStatusBarColor()
    }
    private fun setStatusBarColor(){
        when(navigation){
            0       -> window.statusBarColor = this.resources.getColor(R.color.deep_orange_100_dark)
            1       -> window.statusBarColor = this.resources.getColor(R.color.yellow_100_dark)
            2       -> window.statusBarColor = this.resources.getColor(R.color.red_primary_dark)
            3       -> window.statusBarColor = this.resources.getColor(R.color.blue_primary_dark)
            4       -> window.statusBarColor = this.resources.getColor(R.color.grey_primary_dark)
            else    -> window.statusBarColor = this.resources.getColor(R.color.deep_orange_100_dark)
        }
    }
    private fun correctAnswersCount(): Int{
        var count = 0
        for (i in answers.keys){
            if (answers[i] == questionList[i].correct)
                count++
        }
        return count
    }

    override fun startNewQuiz() {
        clear()                                 //todo theme change on result to default
        openQuestion(null,navigation)

    }

    override fun onCloseButton() {
        this.finish()
    }

    override fun share() {
        Log.d("myLogs",composeMessage())
        //val intent = Intent(this,Intent.)
        //TODO share result using intent, compose result text
    }
    private fun composeMessage(): String {
        val str = StringBuffer("\nYou result is: ${correctAnswersCount()}/5")
        //todo str compose
        for(i in answers.keys){
            str.append("\n")
            str.append("\n ${questionList[i].question}")
            str.append("\n Your answer is: ${questionList[i].answers[answers[i] ?: -1]}")
        }
        return str.toString()
    }
}