package com.example.android.architecture

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.android.architecture.viewModel.DiceViewModel
import kotlinx.android.synthetic.main.activity_dice.*

class DiceActivity : AppCompatActivity() {

    private lateinit var viewModel : DiceViewModel

    private val imageViews by lazy {
        arrayOf<ImageView>(
            findViewById(R.id.die1),
            findViewById(R.id.die2),
            findViewById(R.id.die3),
            findViewById(R.id.die4),
            findViewById(R.id.die5)
        )
    }
    private val headline by lazy {
        findViewById<TextView>(R.id.headline)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_check)

        viewModel = ViewModelProviders.of(this)
            .get(DiceViewModel::class.java)
        viewModel.headline.observe(this, Observer {
            headline.text = it
        })
        viewModel.dice.observe(this, Observer {
            updateDisplay(it)
        })

        lifecycle.addObserver(MyLifecycleObserver())

        val configChange = savedInstanceState?.getBoolean(CONFIG_CHANGE)
                ?: false
        if (configChange.not()) viewModel.rollDice()

    }

    private fun updateDisplay(dice: IntArray) {
        for (i in 0 until imageViews.size) {
            val drawableId = when (dice[i]) {
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

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putBoolean(CONFIG_CHANGE, true)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dice, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_share -> shareResult()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareResult(): Boolean {

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT,
                "I rolled the dice: ${viewModel.headline.value}")
            type = "text/plain"
        }
        startActivity(intent)
        return true
    }

}
