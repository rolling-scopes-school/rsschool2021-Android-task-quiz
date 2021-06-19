package com.rsschool.quiz

class DaoArrayList {

    private val quizGames: List<QuizObject> = mutableListOf(
        QuizObject("Вы готовы, дети?", listOf("Да, капитан!", "Да!", "Нет.", "Нет, капитан", "буль-буль-буль"), 1, 1),
        QuizObject("Ктоооооооо... Кто проживает на дне океана?", listOf("Рыбки", "Губка Боб Квадратные Штаны!", "Водолаз", "Никто не проживает", "Terror from the Deep"), 2,2),
        QuizObject("Жёлтая губка, малыш без изъяна?", listOf("С изъяном", "Не малышь", "Губка Боб Квадратные Штаны!", "Синяя губка", "Жёлтая подводная лодка"), 3,3),
        QuizObject("Кто побеждает всегда и везде?", listOf("Годзилла", "Рэмбо", "Капитан Америка", "Губка Боб Квадратные Штаны!", "Фиолетовый из Повер Рэнджерс"), 4,4),
        QuizObject("Кто также ловок, как рыба в воде?", listOf("Другая рыба", "Аквамен", "Глубина", "Тазик залитый бетоном", "Губка Боб Квадратные Штаны!"), 5,5),
        )

    private val nullQuizObject = QuizObject("nullQuizObject", listOf("nullQuizObject", "nullQuizObject", "nullQuizObject", "nullQuizObject", "nullQuizObject"), 1,1)

    fun getQuizObject(num: Int) : QuizObject {
        return if (num <= quizGames.lastIndex) quizGames[num] else nullQuizObject
    }

    fun getQuizQuestion(num: Int) : String {
        return if (num <= quizGames.lastIndex) quizGames[num].question else nullQuizObject.question
    }

    fun getQuizCorrectAnswer(num: Int) : Int {
        return if (num <= quizGames.lastIndex) quizGames[num].numberCorrectAnswer else nullQuizObject.numberCorrectAnswer
    }

    fun getQuizAnswers(num: Int) : List<String> {
        return if (num <= quizGames.lastIndex) quizGames[num].answers else nullQuizObject.answers
    }

    fun getSize() : Int = quizGames.lastIndex + 1

}