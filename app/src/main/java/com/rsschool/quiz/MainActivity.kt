package com.rsschool.quiz


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rsschool.quiz.databinding.ActivityMainBinding

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), Quiz.ActionPerformedListener, Result.ActionPerformedListener {

    private lateinit var fragment:      Fragment
    private lateinit var transaction:   FragmentTransaction
    private lateinit var binding:       ActivityMainBinding
    private          var navigation =   0                               // number of active page
    private lateinit var answers:       MutableMap<Int,Int>             // collection for answers key = number of page/question, value = answer
    private lateinit var questionList:  List<Question>                  // collection of Questions & answers (see ResRepo.kt)

    override fun onNextButton(answer: Int) {
        answers[navigation]     = answer                                // save answer to collection
        if(navigation < 4) {                                            // if active page<4 theme
            openQuestion(answers[++navigation],navigation)              // open next question
            setStatusBarColor()                                         // and change statusBar color
        } else {                                                        // else (navigation == 4)
            navigation++                                                //
            setStatusBarColor()                                         // change statusBar color
            openResult()                                                // open result fragment
        }
    }

    override fun onPreviousButton(answer: Int) {
        answers[navigation]        = answer                             // save answer (before "Next" button) to collection
        openQuestion(answers[--navigation],navigation)                  // open previous question
        setStatusBarColor()                                             // and change statusBar color
    }

    override fun onBackButton() {
        clear()                                                         // reset application
        openQuestion(null,navigation)                          // open first question
    }

    override fun onCloseButton() {
        this.finish()                                                   // close application
    }

    override fun onShareButton() {                                      // Share.
        val subject     = "Quiz results"
        val messageBody = composeMessage()
        val intent      = Intent().apply {
            this.action = Intent.ACTION_SEND
            this.type   = "text/plain"
            this.putExtra(Intent.EXTRA_SUBJECT,subject)
            this.putExtra(Intent.EXTRA_TEXT,messageBody)
        }                                                               // Create intent with Extra
        startActivity(intent)                                           // Start activity
    }
    //todo refactor from here
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding         = ActivityMainBinding.inflate(layoutInflater)
        answers         = mutableMapOf()                                //init array for answers
        questionList    = ResRepo().getQuestions()                      //get list of questions
        setContentView(binding.root)
        openQuestion(null,navigation)
    }

    private fun openQuestion (prevAnswer: Int?,question: Int){          //start with 0
        fragment        = Quiz.newInstance(prevAnswer,question)
        transaction     = supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,fragment) .commit()
    }

    private fun openResult(){
        val resultQuiz  = "You result is: ${correctAnswersCount()}/5"
        val result:     Fragment             = Result.newInstance(resultQuiz)
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.Container.id,result).commit()
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

    private fun clear(){
        navigation = 0
        answers.clear()
        setStatusBarColor()
    }

    private fun composeMessage() = StringBuffer("You result is: ${correctAnswersCount()}/5").apply{
            for(i in answers.keys){
                this.append("\n")
                this.append("\n ${questionList[i].question}")
                this.append("\n Your answer is: ${questionList[i].answers[answers[i] ?: -1]}") //todo check "-1"
            }
    }.toString()
}