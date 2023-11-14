package com.example.openpaytest.ui.view.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.openpaytest.databinding.FragmentProfileBinding
import com.example.openpaytest.ui.view.MainViewModel
import com.example.openpaytest.ui.view.movies.MoviesFragmentDirections
import com.example.openpaytest.ui.view.movies.adapter.AdpLargeMovies
import com.example.openpaytest.utils.Constants
import com.example.openpaytest.utils.MethodsHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private var isBiographyVisible = false
    private val adpLargeMovies = AdpLargeMovies()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvMoviesActor.layoutManager = linearLayoutManager
        binding.rvMoviesActor.setHasFixedSize(true)

        mainViewModel.isLoading(true)

        viewModel.getMostPopularPerson(MethodsHandler.isInternetAvailable(requireContext()))
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

            adpLargeMovies.setList(popularPerson.movies)
            rvMoviesActor.adapter = adpLargeMovies
            mainViewModel.isLoading(false)
        }
    }

    private fun setUpListeners() = with(binding) {
        mtSeeBiographyLabel.setOnClickListener {
            isBiographyVisible = !isBiographyVisible
            clAdditionalInfo.isVisible = isBiographyVisible
            mtSeeBiographyLabel.text =
                if (isBiographyVisible) "Ocultar biografía" else "Ver biografía"
        }

        adpLargeMovies.onClickOption = { userMovie ->
            findNavController().navigate(
                MoviesFragmentDirections.toBottomSheetDialogInfoMovie(
                    title = MethodsHandler.validateEmptyField(userMovie.title),
                    releaseDate = MethodsHandler.validateEmptyField(userMovie.releaseDate),
                    overview = MethodsHandler.validateEmptyField(userMovie.review),
                    score = userMovie.score?.toFloat() ?: 0.0f,
                    posterPath = MethodsHandler.validateEmptyField(userMovie.posterPath),
                )
            )
        }
    }

}