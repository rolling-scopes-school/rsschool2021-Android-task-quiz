package com.rsschool.quiz

data class UserObject(val name: String,
                      val numAnswers: HashMap<Int, Int>
) {
}