package com.example.android.architecture

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.architecture.viewModel.DiceViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: DiceViewModel
    private val imageViews by lazy {
        arrayOf<ImageView>(die1, die2, die3, die4, die5)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViewModel()

        fab.setOnClickListener { viewModel.rollDice() }
    }

    /**
     * Initialize the viewModel
     */
    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this)
            .get(DiceViewModel::class.java)
        viewModel.headline.observe(this, Observer { headline.text = it })
        viewModel.dice.observe(this, Observer { updateDisplay(it) })
    }

    /**
     * Update the display of dice
     */
    private fun updateDisplay(dice: IntArray?) {
        for (i in 0 until imageViews.size) {
            val drawableId = when (dice?.get(i)) {
                1 -> R.drawable.die_1
                2 -> R.drawable.die_2
                3 -> R.drawable.die_3
                4 -> R.drawable.die_4
                5 -> R.drawable.die_5
                6 -> R.drawable.die_6
                else -> R.drawable.die_6
            }
            imageViews[i].setImageResource(drawableId)
        }
    }

}
