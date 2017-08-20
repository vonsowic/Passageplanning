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
import com.bearcave.passageplanning.utils.round

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

    var switcher: TextView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater!!.inflate(R.layout.fragment_position, container, false)

        degrees = ButterKnife.findById<EditText>(view, R.id.degree)
        minutes = ButterKnife.findById<EditText>(view, R.id.minutes)

        switcher = ButterKnife.findById<TextView>(view, R.id.hemisphere_chooser)
        setHemisphereSymbol()
        switcher!!.setOnClickListener {
            switchHemisphereSymbol()
        }

        return view
    }


    var position: Double
        get() {
            var degreesValue = 0.0
            try {
                degreesValue = degrees?.text.toString().toDouble()
            } catch (_: NumberFormatException){ }

            var minutesValue = 0.0
            try {
                minutesValue = minutes?.text.toString().toDouble() / 60
            } catch (_: NumberFormatException){ }


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
            minutes?.setText(round(minutesValue).toString(), TextView.BufferType.EDITABLE)
            setHemisphereSymbol()
        }


    private fun setHemisphereSymbol() {
        if (signedSet) {
            switcher?.text = signed
        } else {
            switcher?.text = unsigned
        }
    }

    private fun switchHemisphereSymbol() {
        signedSet = !signedSet
        setHemisphereSymbol()
    }
}
