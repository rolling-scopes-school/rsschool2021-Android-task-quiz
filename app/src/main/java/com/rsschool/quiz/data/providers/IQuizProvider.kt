
import com.rsschool.quiz.data.Question

interface IQuizProvider {

    suspend fun loadQuestions(): List<Question>

}

