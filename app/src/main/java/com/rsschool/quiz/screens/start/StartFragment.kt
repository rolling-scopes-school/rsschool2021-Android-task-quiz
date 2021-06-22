package com.rsschool.quiz.screens.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.rsschool.quiz.databinding.FragmentStartBinding
import com.rsschool.quiz.room.Question
import com.rsschool.quiz.room.QuestionDB
import com.rsschool.quiz.room.QuestionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class StartFragment : Fragment() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { QuestionDB.getInstance(requireActivity(), applicationScope) }
    private val repository by lazy { QuestionRepository(database.questionDAO()) }
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private lateinit var questions: List<Question>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val application = requireNotNull(this).activity?.application
        val viewModelFactory = application?.let { StartFactory(repository, it) }
        val mainViewModel =
            viewModelFactory?.let { ViewModelProvider(this, it) }?.get(StartViewModel::class.java)
        mainViewModel?.questions?.observe(viewLifecycleOwner, Observer {
            questions = it
        })

        //Системная кнопка назад
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            ActivityCompat.finishAffinity(requireActivity())
        }


        binding.buttonStart.setOnClickListener() {
            view?.findNavController()
                ?.navigate(
                    StartFragmentDirections.actionStartFragmentToQuizFragment(
                         questions.shuffled().take(5).toTypedArray(),
                        0
                    )
                )
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}