package com.rsschool.quiz

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.rsschool.quiz.databinding.FragmentResultBinding
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
                intent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, createMessage())
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
            append("Результат: ${rightAnswers()} из ${questions.size} \n\n")
            for (question in questions) {
                append(
                    "Вопрос ${count++}:\n ${question.question}\n" +
                            "Ответ:\n ${question.answers?.get(question.answer)} \n\n"
                )
            }
        }.toString()


    }

    private fun rightAnswers(): Int {
        return questions.filter { it.rightAnswer == it.answers?.get(it.answer) }.size
    }

}