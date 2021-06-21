package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rsschool.quiz.databinding.FragmentResultBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2" ///todo needed??

class Result : Fragment() {
    private var listener: ActionPerformedListener? = null
    private var _binding: FragmentResultBinding? = null
    private val binding get() = requireNotNull(_binding)

    interface ActionPerformedListener {
        fun onBackButton ()
        fun onCloseButton()
        fun onShareButton()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Result.ActionPerformedListener
    }

    // TODO: Rename and change types of parameters
    private var param1: String = ""
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1) ?: ""
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            resultText.text = param1
            btnBack.setOnClickListener {
                listener?.startNewQuiz()
            }
            btnClose.setOnClickListener {
            listener?.onCloseButton()
            }
            btnShare.setOnClickListener {
                listener?.share()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        // TODO: Rename and change types and number of parameters !~!!!!!!!!!!!!!!!!!
        @JvmStatic
        fun newInstance(param1: String): Fragment {
            return Result().apply {
                arguments = Bundle().apply {
                    //todo array to params
                    putString(ARG_PARAM1, param1)
                    //putString(ARG_PARAM2, param2)
                }
            }
        }
    }
}