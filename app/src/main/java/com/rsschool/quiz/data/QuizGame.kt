package com.rsschool.quiz.data

// only for this app, don't blame
object QuizGame {
    val questionsList = listOf(
        Question(
            id = 1,
            question = "Какая игра не является игрой из серии Assassin’s Creed?",
            answers = arrayListOf(
                "Assassin’s Creed Syndicate",
                "Assassin’s Creed Rogue",
                "Assassin’s Creed Origins",
                "Assassin’s Creed Valhalla",
                "Assassin’s Creed Chinese Raven"
            ),
            rightAnswer = "Assassin’s Creed Chinese Raven",
            userChoice = ""
        ),
        Question(
            id = 2,
            question = "Какая из этих игр не была разработана Bioware?",
            answers = arrayListOf(
                "Mass Effect",
                "Dragon Age",
                "Jade Empire",
                "Anthem",
                "Stellaris"
            ),
            rightAnswer = "Stellaris",
            userChoice = ""
        ),
        Question(
            id = 3,
            question = "Какая игра не является представителем жанра RPG?",
            answers = arrayListOf(
                "Neverwinter Nights",
                "The Elder Scrolls III: Morrowind",
                "Grim Dawn",
                "Baldur’s Gate",
                "Doom"
            ),
            rightAnswer = "Doom",
            userChoice = ""
        ),
        Question(
            id = 4,
            question = "Игра в сеттинге киберпанк от CD Projekt RED?",
            answers = arrayListOf(
                "Witcher",
                "X-COM",
                "Sims",
                "Hearthstone",
                "Cyberpunk 2077"
            ),
            rightAnswer = "Cyberpunk 2077",
            userChoice = ""
        ),
        Question(
            id = 5,
            question = "Серия игр, которая не является стратегией",
            answers = arrayListOf(
                "Civilization",
                "Age of Empires",
                "Starcraft",
                "Crusader Kings",
                "Portal"
            ),
            rightAnswer = "Portal",
            userChoice = ""
        )
    )

    fun calcResult(): Int {
        val step = 20
        var result = 0
        questionsList.forEach { question ->
            if (question.userChoice == question.rightAnswer){
                result += step
            }
        }
        return result
    }
}
