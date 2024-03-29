package com.bearcave.passageplanning.passagemonitor


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passages.planner.PlanGetter
import com.bearcave.passageplanning.utils.convertFromKtsToMs
import com.bearcave.passageplanning.utils.convertFromMToMm
import com.bearcave.passageplanning.utils.convertFromMsToKts
import com.bearcave.passageplanning.utils.round
import org.joda.time.DateTime


class FootFragment : Fragment() {

    private val plan
        get() = (context as PlanGetter).getPlan()

    private var listener: FootListener? = null

    private var toGoView: TextView? = null
    private var courseView: TextView? = null
    private var etaView: TextView? = null

    private var speedSlide: SeekBar? = null
    private var speedValueView: TextView? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        listener = context as FootListener
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_foot, container, false)

        ButterKnife.findById<TextView>(view, R.id.last_waypoint)
                .text = arguments.getString(WAYPOINT_KEY)

        toGoView = ButterKnife.findById(view, R.id.to_go)
        courseView = ButterKnife.findById(view, R.id.course)
        etaView = ButterKnife.findById(view, R.id.eta)


        speedSlide = ButterKnife.findById(view, R.id.speed)
        speedValueView = ButterKnife.findById(view, R.id.speed_value)
        initializeSlide(speedSlide!!, speedValueView!!)

        speedSlide!!.progress = (plan.passage.speed * 10).convertFromMsToKts().toInt()
        speedValueView!!.text = "${round(plan.passage.speed.convertFromMsToKts(), 1)}"

        ButterKnife.findById<Switch>(view, R.id.current_direction)
                .setOnCheckedChangeListener { _, isChecked ->
                    listener?.setChecked(isChecked)
                }

        return view
    }

    fun setToGo(toGo: Float) {
        toGoView?.text = "${round(toGo.convertFromMToMm())}"
    }

    fun setCourse(course: Float) {
        courseView?.text = "$course"
    }

    fun setEta(eta: DateTime?){
        etaView?.text = eta?.toString("HH:mm") ?: "Tide not available"
    }

    private fun initializeSlide(view: SeekBar, label: TextView){
        view.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                label.text = "${seekBar!!.progress.toFloat() / 10}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                listener?.onSpeedChangedLister((seekBar!!.progress / 10).toFloat().convertFromKtsToMs() )
            }
        })

        label.text = 0f.toString()
    }


    interface FootListener {
        fun onSpeedChangedLister(speed: Float)
        fun setChecked(isChecked: Boolean)
    }


    companion object {
        val WAYPOINT_KEY = "last_waypoint"
    }
}
