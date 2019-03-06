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

    val imageViews by lazy {
        arrayOf<ImageView>(die1, die2, die3, die4, die5)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        viewModel = ViewModelProviders.of(this).get(DiceViewModel::class.java)

        fab.setOnClickListener { viewModel.rollDice() }

        viewModel = ViewModelProviders.of(this).get(DiceViewModel::class.java)
        viewModel.headline.observe(this, Observer {
            headline.text = it
        })
        viewModel.dice.observe(this, Observer {
            updateDisplay(it)
        })
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
