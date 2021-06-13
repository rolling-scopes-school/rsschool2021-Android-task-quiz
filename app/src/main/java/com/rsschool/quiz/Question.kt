package com.rsschool.quiz

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

data class Question(
    val question: String?,
    val answers: ArrayList<String>?,
    val rightAnswer: String?,
    var answer: Int = -1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(question)
        parcel.writeStringList(answers)
        parcel.writeString(rightAnswer)
        parcel.writeInt(answer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}

