package com.rsschool.quiz


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.rsschool.quiz.R.layout.activity_main


import com.rsschool.quiz.databinding.ActivityMainBinding
import com.rsschool.quiz.fragments.*


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