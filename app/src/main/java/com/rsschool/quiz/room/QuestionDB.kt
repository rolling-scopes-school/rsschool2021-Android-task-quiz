package com.rsschool.quiz.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Question::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class QuestionDB : RoomDatabase() {

    abstract fun questionDAO(): QuestionDAO

    companion object {
        @Volatile
        private var INSTANCE: QuestionDB? = null

        fun getInstance(
            context: Context,
            scope: CoroutineScope
        ): QuestionDB {
            Log.i("MyLog", "getInstance")
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuestionDB::class.java,
                        "question"
                    )
                        .addCallback(QuestionDBCallback(scope))
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

    private class QuestionDBCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var questionDAO = database.questionDAO()
                    questionDAO.clear()
                    val questions = mutableListOf(
                        Question(
                            question = "Какое расширение имеет файл Android приложения?",
                            answers = arrayListOf(".zip", ".jar", ".apk", ".exe", "Нет ответа"),
                            rightAnswer = ".apk"
                        ),
                        Question(
                            question = "Какое утверждение о git fetch или/и git pull ложно?",
                            answers = arrayListOf(
                                "git pull выполняет git fetch, а затем - git merge",
                                "git pull позволяет залить изменения из локальной ветки (local), на удаленную (remote)",
                                "git fetch получает изменения изменения с удаленных веток, не сливая изменения с текущей локальной веткой",
                                "Утверждения 1, 2 и 3 верны",
                                "Нет ответа"
                            ),
                            rightAnswer = "git pull позволяет залить изменения из локальной ветки (local), на удаленную (remote)"
                        ),
                        Question(
                            question = "Вы выполнили git add имя_файла случайно и хотите отменить добавление файла. Если коммит ещё не был сделан, то поможет:",
                            answers = arrayListOf(
                                "git reset имя_файла",
                                "git revert",
                                "git revert --soft",
                                "Утверждения 1, 2 и 3 верны",
                                "Нет ответа"
                            ),
                            rightAnswer = "git reset имя_файла"
                        ),
                        Question(
                            question = "Если в Kotlin для приведения типов мы используем оператор \"as?\" и тип не удается привести, то:",
                            answers = arrayListOf(
                                "Компилятор вернет null",
                                "Компилятор вернет platform type",
                                "Компилятор кинет ClassCastException",
                                "Компилятор кинет kotlin.TypeCastException",
                                "Нет ответа"
                            ),
                            rightAnswer = "Компилятор вернет null"
                        ),
                        Question(
                            question = "Коллекции хранят:",
                            answers = arrayListOf(
                                "Объекты указанного типа",
                                "Примитивные типы",
                                "Данные типа String",
                                "Данные типа Collection",
                                "Нет ответа"
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
                                "Нет ответа"
                            ),
                            rightAnswer = "Ключевое слово foreach позволяет проитерировать список"
                        ),
                        Question(
                            question = "Какой интерфейс гарантирует отсутствие дубликатов и доступ к элементам в натуральном порядке?",
                            answers = arrayListOf(
                                "List",
                                "Set",
                                "Deque",
                                "Map",
                                "Нет ответа"
                            ),
                            rightAnswer = "Set"
                        ),
                        Question(
                            question = "Что из перечисленного не является правдой по отношению к ArrayList?",
                            answers = arrayListOf(
                                "Наследует AbstractList и реализует интерфейс List",
                                "Поддерживает динамическй рост по мере добавления элементов",
                                "Является синхронизированным",
                                "Допускает наличие элементов и хранит их в порядке добавления",
                                "Нет ответа"
                            ),
                            rightAnswer = "Является синхронизированным"
                        ),
                        Question(
                            question = "Что верно относительно реализаций интерфейса Map?",
                            answers = arrayListOf(
                                "Ключем может быть только примитив (int, long, ...) или String",
                                "Не может быть одинаковых ключей (key)",
                                "Не может быть одинаковых значений (value)",
                                "В реализации HashMap не допускается ключ равный null",
                                "Нет ответа"
                            ),
                            rightAnswer = "Не может быть одинаковых ключей (key)"
                        ),
                        Question(
                            question = "Какие разрешения (permissions) необходимо указывать в файле AndroidManifest?",
                            answers = arrayListOf(
                                "Обычные разрешения (Install-time permissions)",
                                "Разрешения, которые будут запрошены во время работы приложения (Runtime permissions)",
                                "Оба типа (Install-time & Runtime permissions)",
                                "Ничего из вышеперечисленного",
                                "Нет ответа"
                            ),
                            rightAnswer = "Оба типа (Install-time & Runtime permissions)"
                        ),
                        Question(
                            question = "Что такое \"to\" в  примере : val test = 33 to 42",
                            answers = arrayListOf(
                                "Инфиксная функция, создающая пару (33, 42)",
                                "Ключевое слово Kotlin для создания пары (33, 42)",
                                "Ключевое слово для создания диапазона от 33 до 42",
                                "Опечатка",
                                "Нет ответа"
                            ),
                            rightAnswer = "Инфиксная функция, создающая пару (33, 42)"
                        ),
                        Question(
                            question = "Что применимо для следующего объявления класса? \n" +
                                    "class Person(val name : String)",
                            answers = arrayListOf(
                                "Он package-private",
                                "Он может быть расширен другими классами",
                                "Он public",
                                "У него приватное свойство \"name\"",
                                "Нет ответа"
                            ),
                            rightAnswer = "Он public"
                        ),
                        Question(
                            question = "Какое выражение Kotlin эквивалентно данному из Java?\n" +
                                    "int x = a ? b : c ",
                            answers = arrayListOf(
                                "val x = a ?: b, c",
                                "val x = if (a) b : c",
                                "val x = a ? b : c",
                                "val x = if (a) b else c",
                                "Нет ответа"
                            ),
                            rightAnswer = "val x = if (a) b else c"
                        ),
                        Question(
                            question = "Какое из объявлений функций является валидным в Kotlin?",
                            answers = arrayListOf(
                                "int sum(int a, int b)",
                                "Int sum(a: Int, b: Int)",
                                "function sum(a: Int, b: Int): Int",
                                "fun sum(a: Int, b: Int): Int",
                                "Нет ответа"
                            ),
                            rightAnswer = "fun sum(a: Int, b: Int): Int"
                        ),
                        Question(
                            question = "Чего не предоставляет data class в Kotlin?",
                            answers = arrayListOf(
                                "Авто-генерируемый метод toString()",
                                "Метод copy(...), для создания копии экземпляров.",
                                "Автоматическое преобразование из/в JSON",
                                "Авто-генерируемые методы hashCode() и equals()",
                                "Нет ответа"
                            ),
                            rightAnswer = "Автоматическое преобразование из/в JSON"
                        ),
                        Question(
                            question = "Что выведет следующий код? \n " +
                                    "val listA = mutableListOf(1,2,3) \n " +
                                    "val listB = listA.add(4) \n " +
                                    "print(listB)",
                            answers = arrayListOf(
                                "[1, 2, 3, 4]",
                                "true",
                                "Ничего, тут ошибка компиляции",
                                "Unit",
                                "Нет ответа"
                            ),
                            rightAnswer = "true"
                        ),
                        Question(
                            question = "Как в Kotlin правильно объявить переменную целочисленного типа?",
                            answers = arrayListOf(
                                "var i : int = 42",
                                "let i = 42",
                                "int i = 42",
                                "var i : Int = 42",
                                "Нет ответа"
                            ),
                            rightAnswer = "var i : Int = 42"
                        ),
                        Question(
                            question = "В чем разница между val и var в Kotlin?",
                            answers = arrayListOf(
                                "Переменные, объявленные с помощью val, являются final, а переменные var – нет.",
                                "Переменные, объявленные с помощью val, имеют доступ только к const членам.",
                                "Переменные, объявленные с помощью var, являются final, а переменные val – нет.",
                                "ar ограничен видимостью ближайшего функционального блока, а у val видимость заканчивается на ({ }).",
                                "Нет ответа"
                            ),
                            rightAnswer = "Переменные, объявленные с помощью val, являются final, а переменные var – нет."
                        ),
                        Question(
                            question = "Отметьте ложное утверждение:",
                            answers = arrayListOf(
                                "В Kotlin аргументы со значением по умолчанию следует размещать последними в описании аргументов функции",
                                "В Kotlin мы можем явно указать имя параметра при вызове функции",
                                "В Kotlin smart cast не работает внутри операторов if",
                                "Extension functions в Kotlin реализованы как static функции в Java",
                                "Нет ответа"
                            ),
                            rightAnswer = "В Kotlin smart cast не работает внутри операторов if"
                        ),
                        Question(
                            question = "Какие утверждения верны для абстрактного класса и интерфейса (Java)?",
                            answers = arrayListOf(
                                "- Для абстрактного класса запрещено определение конструктора; - Абстрактный класс может расширить любой другой класс; - Вплоть до Java8 для интерфейсов разрешено только объявлять методы, но реализация их запрещена",
                                "- Для абстрактного класса разрешено определение конструктора; - Абстрактный класс может расширить любой другой не-final класс и реализовать не более 65535 интерфейсов; - Интерфейс может расширить не более 65535 интерфейсов",
                                "- Члены абстрактного класса могут иметь любой модификатор доступа: public, protected, private либо модификатор доступа по-умолчанию; - Абстрактный класс может расширить любой другой не-final класс и реализовать не более 65535 интерфейсов; - Любое поле интерфейса по-умолчанию является protected static final",
                                "- Члены абстрактного класса могут иметь любой модификатор доступа: public, protected, private, кроме модификатора доступа по-умолчанию; - Абстрактный класс может расширить любой другой не-final класс, но не может реализовывать интерфейсы; - Любое поле интерфейса по-умолчанию является public static final",
                                "Нет ответа"
                            ),
                            rightAnswer = "- Для абстрактного класса разрешено определение конструктора; - Абстрактный класс может расширить любой другой не-final класс и реализовать не более 65535 интерфейсов; - Интерфейс может расширить не более 65535 интерфейсов"
                        ),
                        Question(
                            question = "Какой класс в Kotlin можно назвать эквивалентом ключевого слова void в Java?",
                            answers = arrayListOf(
                                "Void()",
                                "Unit()",
                                "Nothing()",
                                "Ничего из вышеперечисленного",
                                "Нет ответа"
                            ),
                            rightAnswer = "Unit()"
                        )
                    )
                    questions.forEach { questionDAO.insert(it) }
                }
            }
        }
    }
}