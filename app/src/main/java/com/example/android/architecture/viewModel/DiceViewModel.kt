package com.example.android.architecture.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.android.architecture.LOG_TAG

class DiceViewModel(app: Application): AndroidViewModel(app) {

    init {
        Log.i(LOG_TAG, "View model created")
    }
}