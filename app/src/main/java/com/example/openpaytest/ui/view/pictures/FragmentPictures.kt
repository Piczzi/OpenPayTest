package com.example.openpaytest.ui.view.pictures

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import com.example.openpaytest.databinding.FragmentPicturesBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import javax.inject.Inject

class FragmentPictures : Fragment() {

    private lateinit var binding: FragmentPicturesBinding

    @Inject
    lateinit var storage: StorageReference


    private var imageUriChosen: String = ""
    val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if (uri != null) {
            binding.ivImageToSend.setImageURI(uri)
            binding.ivImageToSend.background
        } else {

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
        binding.btnSendImage.isActivated = false

        binding.btnChooseImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        binding.btnSendImage.setOnClickListener {
            if (imageUriChosen.isNotBlank()) {

            }
        }
    }

}