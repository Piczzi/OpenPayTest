package com.example.openpaytest.ui.view.movies

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openpaytest.R
import com.example.openpaytest.databinding.FragmentMoviesBinding
import com.example.openpaytest.ui.view.MainViewModel
import com.example.openpaytest.ui.view.maps.MapsFragment
import com.example.openpaytest.ui.view.movies.adapter.AdpLargeMovies
import com.example.openpaytest.ui.view.movies.adapter.AdpSmallMovies
import com.example.openpaytest.utils.MethodsHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
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

        viewModel.getMostPopularMovies(MethodsHandler.isInternetAvailable(requireContext()))
        viewModel.getBestRatedMovies(MethodsHandler.isInternetAvailable(requireContext()))
        viewModel.getUpcomingMovies(MethodsHandler.isInternetAvailable(requireContext()))

        mainViewModel.isLoading(true)

        setUpObservers()
        setUpListeners()
        enableLocation()
    }

    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation() {
        if (isLocationPermissionGranted()) {
            Log.i("SMM_DEBUG", "Permisos concedidos.")
        } else {
            requestLocaltionPermission()
        }
    }

    private fun requestLocaltionPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(
                requireContext(),
                "Activa los permisos en ajustes",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MapsFragment.REQ_CODE_LOCATION
            )
        }
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
            if (popularMovies.isNullOrEmpty()) return@observe
            adpPopularMovies.setList(popularMovies)
            rvPopularMovies.adapter = adpPopularMovies
        }
        viewModel.bestRatedMovies.observe(viewLifecycleOwner) { bestRatedMovies ->
            if (bestRatedMovies.isNullOrEmpty()) return@observe
            adpBestRatedMovies.setList(bestRatedMovies)
            rvBestRatedMovies.adapter = adpBestRatedMovies
        }
        viewModel.upcomingMovies.observe(viewLifecycleOwner) { upcomingMovies ->
            if (upcomingMovies.isNullOrEmpty()) return@observe
            adpUpcomingMovies.setList(upcomingMovies)
            rvUpcomingMovies.adapter = adpUpcomingMovies
            mainViewModel.isLoading(false)
        }
        viewModel.showError.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isBlank()) return@observe
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
            mainViewModel.isLoading(false)
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