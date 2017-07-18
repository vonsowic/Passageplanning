package com.bearcave.passageplanning.waypoints.position_view

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextSwitcher
import android.widget.TextView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R

/**
 *
 * @author Michał Wąsowicz
 * @since 15.07.17
 * @version 1.0
 */
abstract class PositionFragment : Fragment() {

    abstract val unsigned:  String
    abstract val signed:    String

    private var signedSet = false

    var degrees: EditText? = null
    var minutes: EditText? = null
    var seconds: EditText? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater!!.inflate(R.layout.fragment_position, container, false)

        degrees = ButterKnife.findById<EditText>(view, R.id.degree)
        minutes = ButterKnife.findById<EditText>(view, R.id.minutes)
        seconds = ButterKnife.findById<EditText>(view, R.id.seconds)

        val switcher = ButterKnife.findById<TextSwitcher>(view, R.id.hemisphere_chooser)
        switcher.setFactory {
            val switcherTextView = TextView(this.activity.applicationContext)
            switcherTextView.textSize = 20f
            switcherTextView.setTextColor(Color.BLACK)
            switcherTextView.text = if (signedSet) signed else unsigned
            switcherTextView.setPadding(8, 16, 8, 16)
            switcherTextView
        }

        switcher.setOnClickListener {
            setHemisphereSymbol(switcher)
        }

        return view
    }


    var position: Double
        get() {
            val degreesValue = degrees?.text.toString().toDouble() / 3600
            val minutesValue = minutes?.text.toString().toDouble() / 60
            val secondsValue = seconds?.text.toString().toDouble()
            var result = degreesValue + minutesValue +secondsValue
            if (signedSet){
                result *= -1
            }
            return result
        }

        set(value) {
            signedSet = value < 0
            val value = Math.abs(value)
            val degreesValue = value.toInt()
            val minutesValue = (60 * (value - degreesValue)).toInt()
            val secondsValue = (3600 * (value - degreesValue) - 60 * minutesValue).toInt()

            degrees?.setText(degreesValue.toString(), TextView.BufferType.EDITABLE)
            minutes?.setText(minutesValue.toString(), TextView.BufferType.EDITABLE)
            seconds?.setText(secondsValue.toString(), TextView.BufferType.EDITABLE)
        }


    private fun setHemisphereSymbol(switcher: TextSwitcher) {
        if (signedSet) {
            signedSet = false
            switcher.setText(unsigned)
        } else {
            signedSet = true
            switcher.setText(signed)
        }
    }
}
