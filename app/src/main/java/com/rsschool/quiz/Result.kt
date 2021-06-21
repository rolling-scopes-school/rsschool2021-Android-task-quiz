package com.rsschool.quiz

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.rsschool.quiz.databinding.FragmentResultBinding

private const val ARG_PARAM1 = "param1"

class Result : Fragment() {
    private var listener: ActionPerformedListener?  = null
    private var _binding: FragmentResultBinding?    = null
    private val binding   get()                     = requireNotNull(_binding)

    interface ActionPerformedListener {
        fun onBackButton ()
        fun onCloseButton()
        fun onShareButton()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ActionPerformedListener
    }

    private var param1 = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1) ?: ""
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            resultText.text = param1

            btnBack.setOnClickListener {
                listener?.onBackButton()
            }

            btnClose.setOnClickListener {
            listener?.onCloseButton()
            }

            btnShare.setOnClickListener {
                listener?.onShareButton()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newInstance(param1: String): Fragment {
            return Result().apply {
                arguments = bundleOf().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
        }
    }
}