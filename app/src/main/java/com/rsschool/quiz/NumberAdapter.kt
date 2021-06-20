package com.rsschool.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

// Адаптер - специальный компонент, который связывает источник данных с виджетом списка.
// Создадим адаптер, задачей которого будет предоставление фрагментов для слайдера:
// Как понятно из названия, FragmentStateAdapter работает с фрагментами.
// В зависимости от релизации, в конструктор адаптера нужно передать экземпляр или класса Fragment, или класса FragmentActivity.
class NumberAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {

    // набор данных, которые свяжем со списком
    private val dao = DataObjectAccess()

    // Требуется переопределить метод getItemCount, возвращающий общее количество элементов списка.
    override fun getItemCount(): Int = dao.getSize()

    // Переопределим метод createFragment, возвращающий фрагмент для каждого элемента слайдера.
    override fun createFragment(position: Int): Fragment {
        val fragment = BlankFragment()

        fragment.arguments = Bundle().apply {
            // В бланкФрагмент задан ключ для обращения к данным из Бандла countries[position]
            putSerializable(ARG_OBJECT, dao.getQuizObject(position))
        }
        return fragment
    }

}