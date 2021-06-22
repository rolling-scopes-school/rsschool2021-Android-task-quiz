package com.rsschool.quiz.screens.result

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.findNavController
import com.rsschool.quiz.R
import com.rsschool.quiz.databinding.FragmentResultBinding
import com.rsschool.quiz.room.Question
import java.lang.StringBuilder

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var questions: MutableList<Question>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        val args = ResultFragmentArgs.fromBundle(requireArguments())
        questions = args.questions.toMutableList()

        addSmile()
        //Системная кнопка назад
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            view?.findNavController()
                ?.navigate(ResultFragmentDirections.actionResultFragmentToStartFragment())
        }

        binding.apply {

            // Показать результат
            "${rightAnswers()} / ${questions.size} ".also { textViewResult.text = it }

            buttonBack.setOnClickListener {
                view?.findNavController()
                    ?.navigate(ResultFragmentDirections.actionResultFragmentToStartFragment())
            }

            buttonExit.setOnClickListener {
                finishAffinity(requireActivity())
            }

            buttonShare.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/plain")
                    .putExtra(Intent.EXTRA_SUBJECT,"Quiz results")
                    .putExtra(Intent.EXTRA_TEXT, createMessage())
                startActivity(intent)
            }

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createMessage(): String {
        var count = 1
        val stringBuilder = StringBuilder("")
        return stringBuilder.apply {
            append("Your result: ${rightAnswers()} из ${questions.size} \n\n")
            for (question in questions) {
                append(
                    "${count++}) ${question.question}\n" +
                            "Your answer: ${question.answers?.get(question.answer)} \n\n"
                )
            }
        }.toString()


    }

    private fun rightAnswers(): Int {
        return questions.filter { it.rightAnswer == it.answers?.get(it.answer) }.size
    }

    


    private fun addSmile() {
//        val background: Int
        val smile: Int = when ((rightAnswers() / ((questions.size).toDouble() / 100)).toInt()) {
            100 -> {
                R.mipmap.ic_smile1
    //                background = android.R.color.holo_green_dark
            }
            in 99 downTo 65 -> {
                R.mipmap.ic_smile2
    //                background = android.R.color.holo_blue_light
            }
            else -> {
                R.mipmap.ic_smile3_foreground
    //                background = android.R.color.holo_red_light
            }
        }
        binding.imageViewResult.setImageResource(smile)
//        binding.frameLayoutResult.setBackgroundColor(background)
    }

}