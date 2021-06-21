package com.rsschool.quiz.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private val jsonFormat = Json { ignoreUnknownKeys = true }

@Serializable
private class JsonQuestion(
    val id: Int,
    @SerialName("text")
    val text: String,
    @SerialName("choose")
    val choose: List<String>,
    @SerialName("answer")
    val answer: Int,
)

private fun readAssetFileToString(
    context: Context,
    @Suppress("SameParameterValue")
    fileName: String
): String {
    val stream = context.assets.open(fileName)
    return stream.bufferedReader().readText()
}

@Suppress("unused")
internal suspend fun loadQuestions(context: Context): List<Question> = withContext(Dispatchers.IO) {
    val data = readAssetFileToString(context, "quiz.json")
    parseQuestions(data)
}

internal fun parseQuestions(
    data: String,
): List<Question> {
    val jQuestions = jsonFormat.decodeFromString<List<JsonQuestion>>(data)
    return jQuestions.map { jQuestion ->
        @Suppress("unused")
        Question(
            id = jQuestion.id,
            text = jQuestion.text,
            choose = jQuestion.choose,
            correctAnswer = jQuestion.answer,
            userAnswer = -1
        )
    }
}
