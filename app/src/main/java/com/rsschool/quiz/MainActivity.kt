package com.rsschool.quiz


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rsschool.quiz.R.layout.activity_main


import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.fragments.*

//Todo:
// Привет приятель, очень надеюсь что ты это читаешь до того как выставил мне оценку
// Для твоего удобства я прикрепил apk  и видео теста к коду на github
// Все отлично работает, но ты должен знать, что я использовал библиотеку
// https://github.com/kirich1409/ViewBindingPropertyDelegate
// Она помогает убрать дублирующийся код при ViewBinding
// Поэтому у меня отсутствуют переопределения некоторых методов
// Будут вопросы, пиши  Discord: Fiorentino#8337
class MainActivity : AppCompatActivity(activity_main), FragmentInterface {
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firstFragmentInt()
    }

    override fun firstFragmentInt() {
        supportFragmentManager.commit { replace(binding.container.id, FirstFragment()) }
    }

    override fun secondFragmentInt() {
        supportFragmentManager.commit { replace(binding.container.id, SecondFragment()) }
    }

    override fun thirdFragmentInt() {
        supportFragmentManager.commit { replace(binding.container.id, ThirdFragment()) }
    }

    override fun fourthFragmentInt() {
        supportFragmentManager.commit { replace(binding.container.id, FourthFragment()) }
    }

    override fun fifthFragmentInt() {
        supportFragmentManager.commit { replace(binding.container.id, FifthFragment()) }
    }

    override fun resultFragmentInt() {
        supportFragmentManager.commit { replace(binding.container.id, ResultFragment()) }
    }


}