import com.rsschool.quiz.data.Question

interface IQuizLoader {
    suspend fun getQuesitons(): List<Question>
}