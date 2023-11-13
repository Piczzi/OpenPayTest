package com.example.openpaytest.ui.view.profile

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.openpaytest.R
import com.example.openpaytest.databinding.FragmentDialogMovieReviewBinding

class MovieReviewFragmentDialog : DialogFragment() {

    private lateinit var binding: FragmentDialogMovieReviewBinding
    private val args: MovieReviewFragmentDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogMovieReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setUpView()
        setUpListeners()
    }

    private fun setUpView() = with(binding) {
        mtBody.text = args.review
    }

    private fun setUpListeners() = with(binding) {
        btnAcceptHollow.setOnClickListener { dismiss() }
    }

}