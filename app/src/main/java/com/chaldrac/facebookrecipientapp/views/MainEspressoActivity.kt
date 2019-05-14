package com.chaldrac.facebookrecipientapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.chaldrac.facebookrecipientapp.R
import android.widget.TextView
import android.widget.EditText
import android.content.Intent
import android.view.View
import android.widget.Button


class MainEspressoActivity : AppCompatActivity(), View.OnClickListener {

    private var input_billAmount: EditText? = null
    private var buttonTip15: Button? = null
    private var buttonTip20: Button? = null
    private var totalAmount: TextView? = null
    private var viewTotalButton: Button? = null

    private var tipDoubleValue: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_espresso)

        var initialTotalValue = ""
        if (savedInstanceState != null) {
            initialTotalValue = savedInstanceState.getString("totalAmount")
        }

        input_billAmount = findViewById<EditText>(R.id.input_billAmount)
        buttonTip15 = findViewById<View>(R.id.button_tip_15) as Button
        buttonTip20 = findViewById<View>(R.id.button_tip_20) as Button
        totalAmount = findViewById(R.id.totalAmount)
        viewTotalButton = findViewById<View>(R.id.viewTotalButton) as Button

        buttonTip15!!.setOnClickListener(this)
        buttonTip20!!.setOnClickListener(this)

        totalAmount!!.text = initialTotalValue

        viewTotalButton!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_tip_15 -> calculateTip(0.15)
            R.id.button_tip_20 -> calculateTip(0.20)
            R.id.viewTotalButton -> goToViewTotal()
        }
    }

    private fun goToViewTotal() {
        val intent = Intent(this, TotalActivity::class.java)
        intent.putExtra("totalExtra", tipDoubleValue)

        startActivity(intent)
    }

    private fun calculateTip(tipValue: Double) {
        tipDoubleValue = parseTip(input_billAmount?.text.toString())
        tipDoubleValue += tipDoubleValue * tipValue
        totalAmount?.text = tipDoubleValue.toString()
    }

    private fun parseTip(tipValue: String): Double {
        return try {
            java.lang.Double.parseDouble(tipValue)
        } catch (e: NumberFormatException) {
           0.0
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("totalAmount", totalAmount?.text.toString())
        super.onSaveInstanceState(outState)
    }
}
