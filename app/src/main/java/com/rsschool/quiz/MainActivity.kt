package com.rsschool.quiz

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.rsschool.quiz.databinding.ActivityMainBinding


// MainActivity Унаследуем от FragmentActivity.
public class MainActivity : FragmentActivity(), ActionListener {
    // Объявим переменные для адаптера и вьюпейджера.
    private lateinit var adapter: NumberAdapter
    private lateinit var viewPager: ViewPager2
    // private lateinit var resultFragment: ResultFragment

    // Ресурсы
    private lateinit var binding: ActivityMainBinding
    private val user: UserObject = UserObject( "Jesus", hashMapOf())
    private val dataQuiz = DaoArrayList()
    private var messageResult = "No result"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Чтобы создать объект класса ResultProfileBinding, надо вызвать статический метод inflate()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // В методе onCreate инициализируем адаптер, передав ему текущее активити как владелец жизненного цикла
        adapter = NumberAdapter(this)
        // находим вьюпейджер по идентификатору и передаем ему адаптер.
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = adapter
    }

    override fun currentFragment(): Int {
        return viewPager.currentItem
    }

    override fun nextFragment() {
        ++viewPager.currentItem
    }

    override fun backFragment() {
        --viewPager.currentItem
    }

    override fun runResultActivity() {

        var result = 0
        for (x in 0..4) {
            if (dataQuiz.getQuizCorrectAnswer(x) == user.numAnswers[x+1]) ++result
        }
        messageResult = "Результат квиза: $result из 5."

//        resultFragment = ResultFragment.newInstance(messageResult, "test")
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.add(R.id.container, resultFragment)
//        transaction.commit()
    }

    override fun addAnswer(numberQuest: Int, numberAnswer: Int) {
        user.numAnswers[numberQuest] = numberAnswer
    }

}