import android.content.Context
import com.rsschool.quiz.data.Question
import com.rsschool.quiz.data.loadQuestions
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class QuizLoader(
    private val context: Context,
    private val  dispatcher: CoroutineDispatcher
) : IQuizLoader {

    override suspend fun getQuesitons(): List<Question> {
        return withContext(dispatcher) {
            loadQuestions(context)
        }
    }

}

