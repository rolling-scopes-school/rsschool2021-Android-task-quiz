package com.rsschool.quiz

import android.util.SparseArray

//create data class, contains question, answers, and correct answer
data class Question(val question: String, val answers: ArrayList<String>, val correct: Int)
//repository of the ALL Questions
class ResRepo() {
    fun getQuestions(): List<Question>{
        return listOf<Question>(
            Question("Capital of the Great Britain is:",
                //SparseArray(),
                arrayListOf("London","Paris","Moscow","Minsk","Kiev"),
            0),
            Question("Who is president of Russia Federation?",
                arrayListOf("Volodimir Zelenskiy","Alexander Lukashenko","Joseph Biden","Vladimir Putin","Juan Guaidó"),
            3),
            Question("Who was Tamerlane?",
                arrayListOf("President of Uzbekistan","Philosopher","One of the greatest conqueror","Writer","Architecture"),
                2),
            Question("Lao Tzu is:",
                arrayListOf("WuShu master","Philosopher and Poet","Monk from Tibet","Chinese Emperor","Glorious General"),
                1),
            Question("Fidel Castro is:",
                arrayListOf("Cinema Star","Pirate","America founder","Banker","Revolutionary"),
                4)
        )
    }
}