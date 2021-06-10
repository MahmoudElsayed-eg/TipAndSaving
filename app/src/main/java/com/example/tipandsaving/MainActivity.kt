package com.example.tipandsaving

import android.content.Intent
import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import androidx.core.widget.addTextChangedListener
import com.example.tipandsaving.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var billAmount = 0.0
    private var totalSalary = 0.0
    private var tipPercent = 0.25
    private var savingPercent = 0.25
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUi()
    }

    private fun setUi() {
        binding.apply {
            btnSecond.setOnClickListener {
                startActivity(Intent(this@MainActivity,SecondActivity::class.java))
            }
            txtTip.text = String.format("$%.2f",0f)
            txtSaving.text = String.format("$%.2f",0f)
            txtTotalTip.text = String.format("$%.2f",0f)
            seekbarTip.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        tipPercent = progress/100.0
                        calculateTip()
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
            seekbarSaving.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (fromUser) {
                        savingPercent = progress / 100.0
                        calculateSaving()
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
            edtSaving.addTextChangedListener (object :TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    try {
                        totalSalary = s.toString().toDouble() / 100.0
                        txtSavingEdt.text = String.format("$%.2f",totalSalary)
                    }catch (e:NumberFormatException) {
                        txtSavingEdt.text = ""
                        totalSalary = 0.0
                    }
                    calculateSaving()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
            edtTip.addTextChangedListener (object :TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    try {
                        billAmount = s.toString().toDouble() / 100.0
                        txtTipEdt.text = String.format("$%.2f",billAmount)
                    }catch (e:NumberFormatException) {
                        txtTipEdt.text = ""
                        billAmount = 0.0
                    }
                    calculateTip()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.preference) {
            val dialog = SettingDialog()
            dialog.show(supportFragmentManager,null)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun calculateTip() {
        val tipValue = billAmount * tipPercent
        val totalValue = tipValue + billAmount
        binding.apply {
            txtTip.text = String.format("$%.2f",tipValue)
            txtTotalTip.text = String.format("$%.2f",totalValue)
            txtPercentTip.text = String.format("%d%s",(tipPercent * 100).toInt(),"%")
        }
    }
    private fun calculateSaving() {
        binding.txtPercentSaving.text = String.format("%d%s",(savingPercent * 100).toInt(),"%")
        val savingValue = totalSalary * savingPercent
        binding.txtSaving.text = String.format("$%.2f",savingValue)
    }
}