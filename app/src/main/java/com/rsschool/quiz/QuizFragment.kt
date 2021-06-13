package com.rsschool.quiz

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private lateinit var questions: MutableList<Question>
    private var questionNumber = 0
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        val args = QuizFragmentArgs.fromBundle(requireArguments())
        questions = args.questions.toMutableList()
        questionNumber = args.questionNumber

        context?.theme?.applyStyle(setTheme(), true)
        visibleButton()
        setQuestionFragment(questions[questionNumber].answer)

        //Системная кнопка назад
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            lastQuestion()
        }

        binding.apply {

            // Меню в toolbar
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.item_test -> {
                        view?.findNavController()
                            ?.navigate(QuizFragmentDirections.actionQuizFragmentToStartFragment())

                        true
                    }
                    else -> true
                }
            }

            // кнопка Вверх на toolbar
            toolbar.setNavigationOnClickListener {
                lastQuestion()
            }

            //Если выбран вариант ответа в RadioGroup
            radioGroup.setOnCheckedChangeListener { _, _ ->
                binding.nextButton.visibility = View.VISIBLE
            }

            //  Кнопка Previous
            previousButton.setOnClickListener {
                lastQuestion()
            }

            // Кнопка next
            nextButton.setOnClickListener {
                if (questionNumber == questions.size - 1) {
                    addAnswer()
                    view?.findNavController()?.navigate(
                        QuizFragmentDirections.actionQuizFragmentToResultFragment(
                            questions.toTypedArray()
                        )
                    )
                } else {
                    addAnswer()
                    questionNumber++
                    view?.findNavController()?.navigate(
                        QuizFragmentDirections.actionQuizFragmentSelf(
                            questions.toTypedArray(), questionNumber
                        )
                    )
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Заполнение фрагмента данными
    private fun setQuestionFragment(answer: Int) {
        binding.apply {
            radioGroup.clearCheck()
            toolbar.title = "Question ${questionNumber + 1} / ${questions.size}"

            question.text = questions[questionNumber].question
            optionOne.text = questions[questionNumber].answers?.get(0) ?: ""
            optionTwo.text = questions[questionNumber].answers?.get(1) ?: ""
            optionThree.text = questions[questionNumber].answers?.get(2) ?: ""
            optionFour.text = questions[questionNumber].answers?.get(3) ?: ""
            optionFive.text = "Пропустить"

            when (answer) {
                0 -> radioGroup.check(R.id.option_one)
                1 -> radioGroup.check(R.id.option_two)
                2 -> radioGroup.check(R.id.option_three)
                3 -> radioGroup.check(R.id.option_four)
                4 -> radioGroup.check(R.id.option_five)
            }
        }
    }

    //Возврат на предыдущий вопрос
    private fun lastQuestion() {
        if (questionNumber != 0) {
            questionNumber--
            view?.findNavController()?.navigate(
                QuizFragmentDirections.actionQuizFragmentSelf(
                    questions.toTypedArray(), questionNumber
                )
            )
        }
    }

    //добавление варианта ответа
    private fun addAnswer() {
        questions[questionNumber].answer = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.option_one -> 0
            R.id.option_two -> 1
            R.id.option_three -> 2
            R.id.option_four -> 3
            R.id.option_five -> 4
            else -> -1
        }

    }

    //выбор темы
    private fun setTheme(): Int {
        return R.style.Theme_Quiz_Three
//        return when (questionNumber) {
//            0 -> R.style.Theme_Quiz_First
//            1 -> R.style.Theme_Quiz_Second
//            2 -> R.style.Theme_Quiz_Three
//            else -> R.style.Theme_Quiz_Second
//        }
    }

    //видимость кнопок
    private fun visibleButton() {
        if (questionNumber == 0) {
            binding.toolbar.navigationIcon = null
            binding.previousButton.visibility = View.INVISIBLE
        }
        if (questions[questionNumber].answer != -1)
            binding.nextButton.visibility = View.VISIBLE
        if (questionNumber == questions.size - 1)
            binding.nextButton.text = getString(R.string.button_next2)
    }
}

