package com.example.android.architecture.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android.architecture.R
import com.example.android.architecture.util.DiceHelper

class DiceViewModel(app: Application) : AndroidViewModel(app) {
    val headline = MutableLiveData<String>()
    val dice = MutableLiveData<IntArray?>()

    init {
        headline.value = getApplication<Application>().getString(R.string.roll_em)
        dice.value = intArrayOf(6, 6, 6, 6, 6)
    }

    fun rollDice() {
        dice.value = DiceHelper.rollDice()
        headline.value = DiceHelper.evaluateDice(
            getApplication(), dice.value
        )
    }
}