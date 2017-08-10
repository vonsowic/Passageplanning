package com.bearcave.passageplanning.waypoints.position_view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater!!.inflate(R.layout.fragment_position, container, false)

        degrees = ButterKnife.findById<EditText>(view, R.id.degree)
        minutes = ButterKnife.findById<EditText>(view, R.id.minutes)

        val switcher = ButterKnife.findById<TextView>(view, R.id.hemisphere_chooser)
        setHemisphereSymbol(switcher)
        switcher.setOnClickListener {
            setHemisphereSymbol(switcher)
        }

        return view
    }


    var position: Double
        get() {
            val degreesValue = degrees?.text.toString().toDouble()
            val minutesValue = minutes?.text.toString().toDouble() / 60
            var result = degreesValue + minutesValue
            if (signedSet){
                result *= -1
            }
            return result
        }

        set(value) {
            signedSet = value < 0
            val value = Math.abs(value)
            val degreesValue = value.toInt()
            val minutesValue = (60 * (value - degreesValue))

            degrees?.setText(degreesValue.toString(), TextView.BufferType.EDITABLE)
            minutes?.setText(minutesValue.toString(), TextView.BufferType.EDITABLE)
        }


    private fun setHemisphereSymbol(switcher: TextView) {
        if (signedSet) {
            signedSet = false
            switcher.setText(unsigned)
        } else {
            signedSet = true
            switcher.setText(signed)
        }
    }
}
