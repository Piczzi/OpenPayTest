package com.example.openpaytest.ui.view.pictures

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PicturesViewModel @Inject constructor(
    private val storageRef: StorageReference,
): ViewModel() {

    private val _imageUri = MutableLiveData<Uri>(null)
    val imageUri: LiveData<Uri> = _imageUri

    private val _uploadedWasSuccessfully = MutableLiveData<Boolean>(null)
    val uploadedWasSuccessfully: LiveData<Boolean> = _uploadedWasSuccessfully

    private val _imagesFromStorage = MutableLiveData<MutableList<String>>(null)
    val imagesFromStorage: LiveData<MutableList<String>> = _imagesFromStorage

    private val _messageError = MutableLiveData<String>("")
    val messageError: LiveData<String> = _messageError

    fun updateImageUri(uri: Uri) = _imageUri.postValue(uri)

    fun uploadImageToFireStorage(uri: Uri?) {
        if (uri == null) return
        try {
            val imageRef = storageRef.child("images/${uri.lastPathSegment}${UUID.randomUUID()}")
            val uploadTask = imageRef.putFile(uri)
            uploadTask.addOnSuccessListener {
                _uploadedWasSuccessfully.postValue(true)
            }.addOnFailureListener {
                _uploadedWasSuccessfully.postValue(false)
            }
        } catch (e: Exception) {
            _uploadedWasSuccessfully.postValue(false)
            e.printStackTrace()
            Log.e("SMM_DEBUG", "Error al obtener la imagen. Message: ${e.message}")
        }
    }

    fun getImagesFromStorage() {
        val ref = storageRef.child("images/")
        val imageUrls = mutableListOf<String>()

        ref.listAll().addOnSuccessListener { listResult ->
            var successCounter = 0
            listResult.items.forEach { item ->
                item.downloadUrl.addOnSuccessListener { url ->
                    Log.i("SMM_DEBUG", "url from db: $url")
                    imageUrls.add(url.toString())
                    successCounter++
                    if (successCounter == listResult.items.size) {
                        _imagesFromStorage.postValue(imageUrls)
                    }
                }
            }
        }.addOnFailureListener { _messageError.postValue("Error: ${it.message}") }
    }

}