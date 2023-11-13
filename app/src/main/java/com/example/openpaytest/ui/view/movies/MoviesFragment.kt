package com.example.openpaytest.ui.view.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openpaytest.R
import com.example.openpaytest.databinding.FragmentMoviesBinding
import com.example.openpaytest.ui.view.movies.adapter.AdpLargeMovies
import com.example.openpaytest.ui.view.movies.adapter.AdpSmallMovies
import com.example.openpaytest.utils.MethodsHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()
    private val adpPopularMovies = AdpLargeMovies()
    private val adpBestRatedMovies = AdpSmallMovies()
    private val adpUpcomingMovies = AdpSmallMovies()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclers()

        viewModel.getMostPopularMovies()
        viewModel.getBestRatedMovies()
        viewModel.getUpcomingMovies()

        setUpObservers()
        setUpListeners()
    }

    private fun setUpRecyclers() = with(binding) {
        val horizontalLayoutManager1 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val horizontalLayoutManager2 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val horizontalLayoutManager3 =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvPopularMovies.layoutManager = horizontalLayoutManager1
        rvPopularMovies.setHasFixedSize(true)
        rvBestRatedMovies.layoutManager = horizontalLayoutManager2
        rvBestRatedMovies.setHasFixedSize(true)
        rvUpcomingMovies.layoutManager = horizontalLayoutManager3
        rvUpcomingMovies.setHasFixedSize(true)
    }

    private fun setUpObservers() = with(binding) {
        viewModel.popularMovies.observe(viewLifecycleOwner) { popularMovies ->
            adpPopularMovies.setList(popularMovies)
            rvPopularMovies.adapter = adpPopularMovies
        }
        viewModel.bestRatedMovies.observe(viewLifecycleOwner) { bestRatedMovies ->
            adpBestRatedMovies.setList(bestRatedMovies)
            rvBestRatedMovies.adapter = adpBestRatedMovies
        }
        viewModel.upcomingMovies.observe(viewLifecycleOwner) { upcomingMovies ->
            adpUpcomingMovies.setList(upcomingMovies)
            rvUpcomingMovies.adapter = adpUpcomingMovies
        }
    }

    private fun setUpListeners() = with(binding) {
        adpPopularMovies.onClickOption = { movie ->
            findNavController().navigate(
                MoviesFragmentDirections.toBottomSheetDialogInfoMovie(
                    title = MethodsHandler.validateEmptyField(movie.title),
                    releaseDate = MethodsHandler.validateEmptyField(movie.releaseDate),
                    overview = MethodsHandler.validateEmptyField(movie.review),
                    score = movie.score?.toFloat() ?: 0.0f,
                    posterPath = MethodsHandler.validateEmptyField(movie.posterPath),
                )
            )
        }

        adpBestRatedMovies.onClickOption = { movie ->
            findNavController().navigate(
                MoviesFragmentDirections.toBottomSheetDialogInfoMovie(
                    title = MethodsHandler.validateEmptyField(movie.title),
                    releaseDate = MethodsHandler.validateEmptyField(movie.releaseDate),
                    overview = MethodsHandler.validateEmptyField(movie.review),
                    score = movie.score?.toFloat() ?: 0.0f,
                    posterPath = MethodsHandler.validateEmptyField(movie.posterPath),
                )
            )
        }

        adpUpcomingMovies.onClickOption = { movie ->
            findNavController().navigate(
                MoviesFragmentDirections.toBottomSheetDialogInfoMovie(
                    title = MethodsHandler.validateEmptyField(movie.title),
                    releaseDate = MethodsHandler.validateEmptyField(movie.releaseDate),
                    overview = MethodsHandler.validateEmptyField(movie.review),
                    score = movie.score?.toFloat() ?: 0.0f,
                    posterPath = MethodsHandler.validateEmptyField(movie.posterPath),
                )
            )
        }
    }

}