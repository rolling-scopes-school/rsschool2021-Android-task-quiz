package com.rsschool.quiz

interface ActionListener {
    fun nextFragment()
    fun backFragment()
    fun runResultFragment()
    fun addAnswer(numberQuest: Int, numberAnswer: Int)
    fun checkAnswersCount(): Boolean
}