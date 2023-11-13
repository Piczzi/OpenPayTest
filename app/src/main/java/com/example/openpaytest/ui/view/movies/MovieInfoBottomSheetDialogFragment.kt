package com.example.openpaytest.ui.view.movies

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.openpaytest.databinding.FragmentDialogMovieInfoBottomSheetBinding
import com.example.openpaytest.utils.Constants
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class MovieInfoBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDialogMovieInfoBottomSheetBinding
    private val args: MovieInfoBottomSheetDialogFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogMovieInfoBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setUpView()
        setUpListeners()
    }

    private fun setUpView() = with(binding) {
        mtMovieTitle.text = args.title
        mtMovieRelease.text = args.releaseDate
        mtMovieOverview.text = args.overview
        try {
            circleConsumeValue.text = "%.1f".format(args.score)
        } catch (_: Exception) { circleConsumeValue.text = args.score.toString() }
        progressScore.progress = args.score.roundToInt()
    }

    private fun setUpListeners() = with(binding) {
        btnClose.setOnClickListener {
            dismiss()
        }
    }

}