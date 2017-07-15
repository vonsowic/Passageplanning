package com.bearcave.passageplanning.waypoints.position_view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextSwitcher
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

    private var signedSet = true

    var degrees: EditText? = null
    var minutes: EditText? = null
    var seconds: EditText? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater!!.inflate(R.layout.fragment_position, container, false)

        degrees = ButterKnife.findById<EditText>(view, R.id.degree)
        minutes = ButterKnife.findById<EditText>(view, R.id.minutes)
        seconds = ButterKnife.findById<EditText>(view, R.id.seconds)

        ButterKnife.findById<TextSwitcher>(view, R.id.hemisphere_chooser).run {
            setText(signed)

            setOnClickListener {
                if (signedSet) {
                    signedSet = false
                    setText(unsigned)
                } else {
                    signedSet = true
                    setText(signed)
                }
            }
        }

        return view
    }


    var position: Double
        get() {
            val degreesValue = degrees!!.text.toString().toDouble() * 3600
            val minutesValue = minutes!!.text.toString().toDouble() * 60
            val secondsValue = seconds!!.text.toString().toDouble()
            var result = degreesValue + minutesValue +secondsValue
            if (signedSet){
                result *= -1
            }
            return result
        }

        set(value) {
            signedSet = value < 0
        }

}