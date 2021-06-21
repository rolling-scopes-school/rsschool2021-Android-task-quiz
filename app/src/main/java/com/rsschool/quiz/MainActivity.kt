package com.rsschool.quiz

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.rsschool.quiz.databinding.ActivityMainBinding

const val EXTRA_MESSAGE = "com.rsschool.quiz.MESSAGE"

// MainActivity Унаследуем от FragmentActivity.
public class MainActivity : FragmentActivity(), ActionListener {
    // Объявим переменные для адаптера и вьюпейджера.
    private lateinit var adapter: NumberAdapter
    private lateinit var viewPager: ViewPager2

    // Ресурсы
    private lateinit var binding: ActivityMainBinding
    private val dataQuiz = DataObjectAccess()
    // private val position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Чтобы создать объект класса ResultProfileBinding, надо вызвать статический метод inflate()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // В методе onCreate инициализируем адаптер, передав ему текущее активити как владелец жизненного цикла
        adapter = NumberAdapter(this)
        // находим вьюпейджер по идентификатору и передаем ему адаптер.

        viewPager = binding.pager
        // viewPager = findViewById(R.id.pager)
        viewPager.adapter = adapter
    }

    override fun currentFragment(): Int {
        return viewPager.currentItem
    }

    override fun nextFragment() {
        ++viewPager.currentItem
        // viewPager.isUserInputEnabled = false
    }

    override fun backFragment() {
        --viewPager.currentItem
    }

    override fun runResultFragment() {

        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, dataQuiz.getResultMessage())
        }
        startActivity(intent)
        finish()
    }

    override fun addAnswer(numberQuest: Int, numberAnswer: Int) {
        dataQuiz.addAnswer(numberQuest, numberAnswer)
    }

}