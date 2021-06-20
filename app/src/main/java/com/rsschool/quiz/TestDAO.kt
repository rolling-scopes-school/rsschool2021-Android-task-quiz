package com.rsschool.quiz

    fun main(args: Array<String>){
        println("Hello Kotlin")
        val dataQuiz = DataObjectAccess()
        dataQuiz.addAnswer(1, 1)
        dataQuiz.addAnswer(2, 2)
        dataQuiz.addAnswer(3, 3)
        dataQuiz.addAnswer(4, 2)
        dataQuiz.addAnswer(5, 2)

        val result = dataQuiz.getResultMessage()
        println(result.email)
        println(result.resultMessage)
        println(result.resultQuiz)
    }

/*
            println("Вопрос: ${quizGames[x].question}")
            println("numAnswers: ${numAnswers[x+1]}")
            var y = numAnswers[x+1]!!
            println("textAnswers: ${quizGames[x].answers[y - 1]}")
            println("CorrectAnswers: ${quizGames[x].numberCorrectAnswer}")
            println("textCorrecAnswers: ${quizGames[x].answers[quizGames[x].numberCorrectAnswer - 1]}")
            println()
 */