package com.example.storyapp.view.upload

import androidx.lifecycle.ViewModel
import android.net.Uri

class UploadViewModel : ViewModel() {
    var currentImageUri: Uri? = null
    var description: String = ""
}
