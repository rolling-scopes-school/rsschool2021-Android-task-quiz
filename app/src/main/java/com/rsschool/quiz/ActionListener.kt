package com.rsschool.quiz

interface ActionListener {
    fun currentFragment(): Int
    fun nextFragment()
    fun backFragment()
    fun runResultFragment()
    fun addAnswer(numberQuest: Int, numberAnswer: Int)
}