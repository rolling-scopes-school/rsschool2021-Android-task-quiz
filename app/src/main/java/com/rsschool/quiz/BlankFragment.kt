package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding


// Вне тела класса создаем константу для ключа аргумента, который будем передавать в каждый новый экземпляр фрагмента.
const val ARG_OBJECT = "object"

// Этот фрагмент будет использоваться для каждого нового экрана в приложении, мы будем создавать новый его экземпляр и передавать туда его порядковый номер.
class BlankFragment : Fragment() {

    // Крутая штука, что бы словить null и получить исключение именно здесь, а не в методах класса
    private var _listener: ActionListener? = null
    private val listener get() = _listener!!
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    private var currentFragment = 0

    // Добавляем сслыку на MainActivity через контекст
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _listener = context as ActionListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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

            // Для условий
            currentFragment = quizObject.numberQuestion

            // Заполняем текстовые формы
            binding.toolbar.title = "Question ${quizObject.numberQuestion}"
            binding.question.text = quizObject.question
            binding.optionOne.text = quizObject.answers[0]
            binding.optionTwo.text = quizObject.answers[1]
            binding.optionThree.text = quizObject.answers[2]
            binding.optionFour.text = quizObject.answers[3]
            binding.optionFive.text = quizObject.answers[4]

            // Блокируем кнопку до выбора ответа
            binding.nextButton.isEnabled = false
            // Блокируем кнопки первого фрагмента
            if (currentFragment < 2) {
                binding.previousButton.isEnabled = false
                binding.toolbar.navigationIcon = null
            }

            // Меняем надпись на последнем фрагменте
            if (currentFragment > 4) binding.nextButton.text = "Submit"
        }

        binding.nextButton.setOnClickListener {
            if (listener.checkAnswersCount()) listener.runResultFragment()
                else listener.nextFragment()
        }

        binding.previousButton.setOnClickListener {
            listener.backFragment()
        }

        binding.toolbar.setOnClickListener {
            listener.backFragment()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, checkId ->
            when (checkId) {
                binding.optionOne.id -> listener.addAnswer(currentFragment, 1)
                binding.optionTwo.id -> listener.addAnswer(currentFragment, 2)
                binding.optionThree.id -> listener.addAnswer(currentFragment, 3)
                binding.optionFour.id -> listener.addAnswer(currentFragment, 4)
                binding.optionFive.id -> listener.addAnswer(currentFragment, 5)
            }
            // Когда выбор ответа сделан - освобождаем кнопку
            binding.nextButton.isEnabled = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}