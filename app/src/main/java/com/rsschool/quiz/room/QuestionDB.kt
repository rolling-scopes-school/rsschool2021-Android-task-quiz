package com.rsschool.quiz.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Question::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class QuestionDB : RoomDatabase() {

    abstract val questionDao: QuestionDAO

    companion object {
        @Volatile
        private var INSTANCE: QuestionDB? = null

        fun getInstance(context: Context): QuestionDB {
            Log.i("MyLog", "getInstance")
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuestionDB::class.java,
                        "question"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}