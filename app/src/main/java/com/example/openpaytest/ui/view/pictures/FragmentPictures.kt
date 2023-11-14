package com.example.openpaytest.ui.view.pictures

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openpaytest.R.string
import com.example.openpaytest.databinding.FragmentPicturesBinding
import com.example.openpaytest.ui.view.MainViewModel
import com.example.openpaytest.ui.view.pictures.adapter.AdpSavedImages
import com.example.openpaytest.utils.MethodsHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentPictures : Fragment() {

    private lateinit var binding: FragmentPicturesBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private val picturesViewModel: PicturesViewModel by viewModels()
    private val adpSavedImages = AdpSavedImages()

    private val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            picturesViewModel.updateImageUri(uri)
            binding.ivImageToSend.setImageURI(uri)
            binding.ivImageToSend.background
        } else {
            Log.i("SMM_DEBUG", "No se ha seleccionado una imagen!")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPicturesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvPreviouslyImages.layoutManager = linearLayoutManager
        binding.rvPreviouslyImages.setHasFixedSize(true)

        if (MethodsHandler.isInternetAvailable(requireContext())) mainViewModel.isLoading(true)
        picturesViewModel.getImagesFromStorage()
        setUpObservers()
        setUpListeners()
    }

    private fun setUpObservers() {
        picturesViewModel.uploadedWasSuccessfully.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            mainViewModel.isLoading(false)
            if (it) showMessage("¡Imagen guardada con éxito!")
        }

        picturesViewModel.imagesFromStorage.observe(viewLifecycleOwner) { imageList ->
            if (imageList == null) return@observe
            mainViewModel.isLoading(false)
            adpSavedImages.setList(imageList)
            binding.rvPreviouslyImages.adapter = adpSavedImages
        }

        picturesViewModel.messageError.observe(viewLifecycleOwner) { showMessage(it) }
    }

    private fun setUpListeners() {
        binding.btnChooseImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        binding.btnSendImage.setOnClickListener {
            if (!MethodsHandler.isInternetAvailable(requireContext())) {
                showMessage("Verifica tu conexión a internet.")
                return@setOnClickListener
            }
            picturesViewModel.imageUri.value?.let {
                mainViewModel.isLoading(true)
                picturesViewModel.uploadImageToFireStorage(it)
            } ?: run { showMessage(getString(string.you_most_select_an_image_first)) }
        }
    }

    private fun showMessage(message: String?) {
        if (message.isNullOrBlank()) return
        mainViewModel.isLoading(false)
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

}