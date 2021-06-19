package com.rsschool.quiz

interface ActionListener {
    fun currentFragment(): Int
    fun nextFragment()
    fun backFragment()
    fun runResultActivity()
    fun addAnswer(numberQuest: Int, numberAnswer: Int)
}