package com.example.android.architecture

import android.media.SoundPool
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.architecture.viewModel.DiceViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.media.AudioManager
import android.os.Build
import android.util.Log


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: DiceViewModel

    private lateinit var soundPool: SoundPool
    private var soundId: Int = 0
    private var soundLoaded = false

    val imageViews by lazy {
        arrayOf<ImageView>(die1, die2, die3, die4, die5)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(DiceViewModel::class.java)
        viewModel.headline.observe(this, Observer {
            headline.text = it
        })
        viewModel.dice.observe(this, Observer {
            updateDisplay(it)
        })

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
        soundId = soundPool.load(this, R.raw.roll_dice, 1)

        fab.setOnClickListener {
            viewModel.rollDice()
            if (soundLoaded) {
                soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
            }
        }
    }

    private fun updateDisplay(dice: IntArray?) {
        for (i in 0 until imageViews.size) {
            val drawableId = resources.getIdentifier(
                "die_${dice?.get(i)}", "drawable", packageName
            )
            imageViews[i].setImageResource(drawableId)
        }
    }

}
