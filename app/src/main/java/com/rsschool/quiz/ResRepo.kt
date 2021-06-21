package com.rsschool.quiz

//create data class, contains question, answers, and correct answer
data class Question(val question: String, val answers: ArrayList<String>, val correct: Int)

//repository of the ALL Questions
class ResRepo {
    fun getQuestions(): List<Question>{
        return listOf(
            Question("The capital of Great Britain:",
                arrayListOf("London","Paris","Moscow","Minsk","Kiev"),
            0),
            Question("Who is the president of Venezuela?",
                arrayListOf("Volodimir Zelenskiy","Alexander Lukashenko","Joseph Biden","Juan Guaidó","Nicolás Maduro"),
            4),
            Question("Who was Tamerlane?",
                arrayListOf("President of Uzbekistan","Philosopher","One of the greatest conqueror","Writer","Architecture"),
                2),
            Question("Who was Lao Tzu?",
                arrayListOf("WuShu master","Philosopher and Poet","Monk from Tibet","Chinese Emperor","Glorious General"),
                1),
            Question("Fidel Castro was:",
                arrayListOf("Cinema Star","Pirate","America founder","Banker","Revolutionary"),
                4)
        )
    }
}