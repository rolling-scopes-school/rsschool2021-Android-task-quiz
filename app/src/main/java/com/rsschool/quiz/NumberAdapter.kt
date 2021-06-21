package com.rsschool.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Адаптер - специальный компонент, который связывает источник данных с виджетом списка.
// Создадим адаптер, задачей которого будет предоставление фрагментов для слайдера:
class NumberAdapter(private val fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    // Набор данных, которые свяжем со списком
    private val dao = DataObjectAccess()

    // Требуется переопределить метод getItemCount, возвращающий общее количество элементов списка.
    override fun getItemCount(): Int = dao.getSize()

    // Переопределим метод createFragment, возвращающий фрагмент для каждого элемента слайдера.
    override fun createFragment(position: Int): Fragment {

        // Перед созданием фрагмента устанавливаем тему приложения
        when (position) {
            0 ->  fragment.theme?.applyStyle(R.style.Theme_Quiz_Purple, true)
            1 ->  fragment.theme?.applyStyle(R.style.Theme_Quiz_Blue, true)
            2 ->  fragment.theme?.applyStyle(R.style.Theme_Quiz_Green, true)
            3 ->  fragment.theme?.applyStyle(R.style.Theme_Quiz_Yellow, true)
            4 ->  fragment.theme?.applyStyle(R.style.Theme_Quiz_Orange, true)
            else -> fragment.theme?.applyStyle(R.style.Theme_Quiz_First, true)
        }

        val fragment = BlankFragment()
        fragment.arguments = Bundle().apply {
            // Передаем данные в созданный фрагмент
            putSerializable(ARG_OBJECT, dao.getQuizObject(position))
        }
        return fragment
    }

}