package com.rsschool.quiz

    fun main(args: Array<String>){
        println("Hello Kotlin")
        val dataQuiz = DataObjectAccess()

        dataQuiz.addAnswer(1, 1)
        dataQuiz.addAnswer(2, 2)
        dataQuiz.addAnswer(3, 3)
        dataQuiz.addAnswer(4, 2)
        dataQuiz.addAnswer(5, 2)

        //println(dataQuiz.numAnswers.toString())

        print("кол-во ответов: ")
        //println(dataQuiz.numAnswers.size)
        println(dataQuiz.checkAnswers())

        if (dataQuiz.checkAnswers()) {
            val result = dataQuiz.getResultMessage()
            println(result.email)
            println(result.resultMessage)
            println(result.resultQuiz)
        }
    }