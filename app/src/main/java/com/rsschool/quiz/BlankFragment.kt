package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding
import kotlin.properties.Delegates


// Вне тела класса создаем константу для ключа аргумента, который будем передавать в каждый новый экземпляр фрагмента.
const val ARG_OBJECT = "object"

// Этот фрагмент будет использоваться для каждого нового экрана в приложении, мы будем создавать новый его экземпляр и передавать туда его порядковый номер.
class BlankFragment : Fragment() {

    private var listener: ActionListener? = null
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private var tempInt by Delegates.notNull<Int>()

    // Добавляем слушатель через контекст
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // activity?.theme?.applyStyle(R.style.Theme_Quiz_Purple, true)

        // return inflater.inflate(R.layout.fragment_blank, container, false)
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    // В переопределенном методе onViewCreated получаем аргумент, находим текстовое поле и передаем туда значение аргумента для отображения.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // Берем аргумент Bundle arguments, если он имеет ключ ARG_OBJECT и применяем лямбду
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {

            // Чтобы получить отправленные данные при загрузке из Bundle arguments, можно воспользоваться методом get(), в который передается ключ объекта:
            val quizObject: QuizObject = get(ARG_OBJECT) as QuizObject

            binding.toolbar.title = "Question ${quizObject.numberQuestion}"

            // Общее для всех функций свойство класса
            tempInt = quizObject.numberQuestion

            when (tempInt) {
                1 -> {
                    activity?.theme?.applyStyle(R.style.Theme_Quiz_Purple, true)
                    // activity?.window?.statusBarColor = resources.getColor(R.color.secondaryDarkColorPurple)
                }
                2 -> {
                    activity?.theme?.applyStyle(R.style.Theme_Quiz_Blue, true)
                    // activity?.window?.statusBarColor = resources.getColor(R.color.secondaryDarkColorBlue)
                }
                3 -> {
                    activity?.theme?.applyStyle(R.style.Theme_Quiz_Green, true)
                    // activity?.window?.statusBarColor = resources.getColor(R.color.secondaryDarkColorGreen)
                }
                4 -> {
                    activity?.theme?.applyStyle(R.style.Theme_Quiz_Yellow, true)
                    // activity?.window?.statusBarColor = resources.getColor(R.color.secondaryDarkColorYellow)
                }
                5 -> {
                    activity?.theme?.applyStyle(R.style.Theme_Quiz_Orange, true)
                    // activity?.window?.statusBarColor = resources.getColor(R.color.secondaryDarkColorOrange)
                }
            }

            binding.question.text = quizObject.question
            binding.optionOne.text = quizObject.answers[0]
            binding.optionTwo.text = quizObject.answers[1]
            binding.optionThree.text = quizObject.answers[2]
            binding.optionFour.text = quizObject.answers[3]
            binding.optionFive.text = quizObject.answers[4]

            binding.nextButton.isEnabled = false
            binding.previousButton.isEnabled = tempInt >= 2

            // Сделать проверку наличия ответов
            if (tempInt > 4) binding.nextButton.text = "Submit"
        }

        binding.nextButton.setOnClickListener {
            if (tempInt > 4) {
                listener?.runResultFragment()

            }
                else listener?.nextFragment()
        }

        binding.previousButton.setOnClickListener {
            listener?.backFragment()
        }

        binding.toolbar.setOnClickListener {
            listener?.backFragment()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                binding.optionOne.id -> listener?.addAnswer(tempInt, 1)
                binding.optionTwo.id -> listener?.addAnswer(tempInt, 2)
                binding.optionThree.id -> listener?.addAnswer(tempInt, 3)
                binding.optionFour.id -> listener?.addAnswer(tempInt, 4)
                binding.optionFive.id -> listener?.addAnswer(tempInt, 5)

            }
            binding.nextButton.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}