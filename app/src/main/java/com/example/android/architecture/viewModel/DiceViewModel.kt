package com.example.android.architecture.viewModel

import android.app.Application
import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.android.architecture.R
import com.example.android.architecture.util.DiceHelper

class DiceViewModel(app: Application) : AndroidViewModel(app) {
    val headline = MutableLiveData<String>()
    val dice = MutableLiveData<IntArray?>()

    private lateinit var soundPool: SoundPool

    private var soundId = 0
    private var soundLoaded = false
    private var context: Context

    init {
//      Initialize properties
        headline.value = getApplication<Application>().getString(R.string.roll_em)
        dice.value = intArrayOf(5, 4, 3, 2, 1)
        context = app

//      Set up sound pool
        initSoundPool()
    }

    fun rollDice() {
        dice.value = DiceHelper.rollDice()
        headline.value = DiceHelper.evaluateDice(
            getApplication(), dice.value
        )
        if (soundLoaded) {
            soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
        }
    }

    private fun initSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            soundPool = SoundPool.Builder()
                .setMaxStreams(5)
                .build()
        } else {
            @Suppress("DEPRECATION")
            soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        }
        soundPool.setOnLoadCompleteListener { _, _, status ->
            soundLoaded = (status == 0)
        }
        soundId = soundPool.load(context, R.raw.roll_dice, 1)
    }
}