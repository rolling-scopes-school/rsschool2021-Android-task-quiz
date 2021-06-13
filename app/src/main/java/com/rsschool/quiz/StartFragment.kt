package com.rsschool.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.rsschool.quiz.StartFragmentDirections.ActionStartFragmentToQuizFragment
import com.rsschool.quiz.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)

        val questions = mutableListOf(
            Question(
                question = "Какое расширение имеет файл Android приложения?",
                answers = arrayListOf(".zip", ".jar", ".apk", ".exe", ""),
                rightAnswer = ".apk"
            ),
            Question(
                question = "Какое утверждение о git fetch или/и git pull ложно?",
                answers = arrayListOf(
                    "git pull выполняет git fetch, а затем - git merge",
                    "git pull позволяет залить изменения из локальной ветки (local), на удаленную (remote)",
                    "git fetch получает изменения изменения с удаленных веток, не сливая изменения с текущей локальной веткой",
                    "Утверждения 1, 2 и 3 верны", ""
                ),
                rightAnswer = "git pull позволяет залить изменения из локальной ветки (local), на удаленную (remote)"
            ),
            Question(
                question = "Вы выполнили git add имя_файла случайно и хотите отменить добавление файла. Если коммит ещё не был сделан, то поможет:",
                answers = arrayListOf(
                    "git reset имя_файла",
                    "git revert",
                    "git revert --soft",
                    "Утверждения 1, 2 и 3 верны", ""
                ),
                rightAnswer = "git reset имя_файла"
            ),
            Question(
                question = "Если в Kotlin для приведения типов мы используем оператор \"as?\" и тип не удается привести, то:",
                answers = arrayListOf(
                    "Компилятор вернет null",
                    "Компилятор вернет platform type",
                    "Компилятор кинет ClassCastException",
                    "Компилятор кинет kotlin.TypeCastException", ""
                ),
                rightAnswer = "Компилятор вернет null"
            ),
            Question(
                question = "Коллекции хранят:",
                answers = arrayListOf(
                    "Объекты указанного типа",
                    "Примитивные типы",
                    "Данные типа String",
                    "Данные типа Collection", ""
                ),
                rightAnswer = "Объекты указанного типа"
            ),
            Question(
                question = "Какое утверждение ошибочно относительно Java?",
                answers = arrayListOf(
                    "Ключевое слово continue в циклах for позволяет пропустить итерацию цикла",
                    "Ключевое слово break в циклах for позволяет прервать выполнение цикла",
                    "Ключевое слово foreach позволяет проитерировать список",
                    "Конструкция do-while позволяет выполнять некоторый блок кода в do пока верно условие в while." +
                            " Причем, сначала выполняется блок кода в do и только затем проверяется условие в while.",
                    ""
                ),
                rightAnswer = "Ключевое слово foreach позволяет проитерировать список"
            ),
            Question(
                question = "Какой интерфейс гарантирует отсутствие дубликатов и доступ к элементам в натуральном порядке?",
                answers = arrayListOf(
                    "List",
                    "Set",
                    "Deque",
                    "Map", ""
                ),
                "Set"
            ),
            Question(
                question = "Что из перечисленного не является правдой по отношению к ArrayList?",
                answers = arrayListOf(
                    "Наследует AbstractList и реализует интерфейс List",
                    "Поддерживает динамическй рост по мере добавления элементов",
                    "Является синхронизированным",
                    "Допускает наличие элементов и хранит их в порядке добавления", ""
                ),
                rightAnswer = "Является синхронизированным"
            ),
            Question(
                question = "Что верно относительно реализаций интерфейса Map?",
                answers = arrayListOf(
                    "Ключем может быть только примитив (int, long, ...) или String",
                    "Не может быть одинаковых ключей (key)",
                    "Не может быть одинаковых значений (value)",
                    "В реализации HashMap не допускается ключ равный null", ""
                ),
                rightAnswer = "Не может быть одинаковых ключей (key)"
            ),
            Question(
                question = "Какие разрешения (permissions) необходимо указывать в файле AndroidManifest?",
                answers = arrayListOf(
                    "Обычные разрешения (Install-time permissions)",
                    "Разрешения, которые будут запрошены во время работы приложения (Runtime permissions)",
                    "Оба типа (Install-time & Runtime permissions)",
                    "Ничего из вышеперечисленного", ""
                ),
                rightAnswer = "Оба типа (Install-time & Runtime permissions)"
            )
        ).shuffled().take(5).toTypedArray()

        //Системная кнопка назад
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            ActivityCompat.finishAffinity(requireActivity())
        }


        binding.buttonStart.setOnClickListener() {
            view?.findNavController()
                ?.navigate(StartFragmentDirections.actionStartFragmentToQuizFragment(questions, 0))
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}