package com.rsschool.quiz.room

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.ArrayList

@Entity(tableName = "question")
data class Question(
    @PrimaryKey(autoGenerate = true) var questionID: Int = 0,
    @ColumnInfo(name = "question") var question: String?,
    @ColumnInfo(name = "answers") var answers: ArrayList<String>?,
    @ColumnInfo(name = "right_answer") var rightAnswer: String?,
    @ColumnInfo(name = "answer") var answer: Int = -1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readInt()
    ) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(questionID)
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

