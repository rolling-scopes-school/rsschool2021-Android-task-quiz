package com.rsschool.quiz.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Question(
    val id: Int,
    val text: String,
    val choose: List<String>,
    val correctAnswer: Int,
    var userAnswer: Int = -1,
) : Parcelable