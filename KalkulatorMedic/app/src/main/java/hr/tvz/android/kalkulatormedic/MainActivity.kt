package hr.tvz.android.kalkulatormedic

import android.icu.text.NumberFormat
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import hr.tvz.android.kalkulatormedic.databinding.ActivityMainBinding
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.calculateButton.setOnClickListener {calculateTip()}


        val texts = listOf(binding.bill, binding.serviceQuestion, binding.tenPercent,
            binding.sevenPercent, binding.fivePercent, binding.roundedTip,
            binding.calculateButton, binding.result)

        binding.fontSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val textSize = progress.coerceIn(10, 30).toFloat()
                texts.forEach { it.textSize = textSize }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

    }

    private fun calculateTip() {
        val bill = binding.bill.text.toString().toDouble()
        val selectedID = binding.tipOptions.checkedRadioButtonId
        val percentage = when(selectedID){
            R.id.tenPercent -> 0.1
            R.id.sevenPercent -> 0.07
            else -> 0.05
        }

        var tip = bill * percentage
        val roundedTip = binding.roundedTip.isChecked

        if (roundedTip){
            tip = ceil(tip)
        }

        val finalTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.result.text = getString(R.string.tip_amount, finalTip)
    }
}