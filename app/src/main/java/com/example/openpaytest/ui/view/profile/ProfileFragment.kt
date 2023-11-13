package com.example.openpaytest.ui.view.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.openpaytest.R
import com.example.openpaytest.data.model.local.UserMovies
import com.example.openpaytest.databinding.FragmentProfileBinding
import com.example.openpaytest.ui.view.profile.adapter.AdpActorMovies
import com.example.openpaytest.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private var isBiographyVisible = false
    private val adpActorMovies = AdpActorMovies()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager: LinearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvMoviesActor.layoutManager = linearLayoutManager
        binding.rvMoviesActor.setHasFixedSize(true)

        viewModel.getMostPopularPerson()
        setUpObservers()
        setUpListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpObservers() = with(binding) {
        viewModel.popularPerson.observe(viewLifecycleOwner) { popularPerson ->
            mtGreetingName.text = "Hola, ${popularPerson.name}!"
            Glide
                .with(requireContext())
                .load("${Constants.URL_IMAGES}${popularPerson.profileImagePath}")
                .centerCrop()
                .into(ivProfilePicture)
            mtPopularityValue.text = popularPerson.popularity.toString()
            mtBirthdayValue.text = popularPerson.birthday
            mtDeathdayValue.text = popularPerson.deathday
            mtBirthPlaceValue.text = popularPerson.birthPlace
            mtBiographyValue.text = popularPerson.biography

            adpActorMovies.setList(popularPerson.movies)
            rvMoviesActor.adapter = adpActorMovies
        }
    }

    private fun setUpListeners() = with(binding) {
        mtSeeBiographyLabel.setOnClickListener {
            isBiographyVisible = !isBiographyVisible
            clAdditionalInfo.isVisible = isBiographyVisible
            mtSeeBiographyLabel.text =
                if (isBiographyVisible) "Ocultar biografía" else "Ver biografía"
        }

        adpActorMovies.onClickOption = { userMovie ->
            findNavController().navigate(
                ProfileFragmentDirections.toMovieReviewFragmentDialog(
                    if (userMovie.review.isNullOrBlank()) "Review no disponible." else userMovie.review
                )
            )
        }
    }

}