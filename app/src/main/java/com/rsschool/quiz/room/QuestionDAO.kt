package com.rsschool.quiz.room

import androidx.room.*

@Dao
interface QuestionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg question: Question)

    @Update
    suspend fun update(question: Question)

    @Query("SELECT * from question WHERE questionID = :key")
    suspend fun get(key: Int): Question?

    @Delete
    suspend fun delete(question: Question)

    @Query("DELETE FROM question")
    suspend fun clear()

    @Query("SELECT * FROM question")
    fun getAllQuestion(): List<Question>
}