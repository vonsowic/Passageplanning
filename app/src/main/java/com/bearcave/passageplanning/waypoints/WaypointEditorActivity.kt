package com.bearcave.passageplanning.waypoints

import android.support.v7.widget.PopupMenu
import android.view.Menu
import android.view.View
import android.widget.TextView
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseEditorActivity
import com.bearcave.passageplanning.tides.utils.Gauge
import com.bearcave.passageplanning.tides.utils.TideCurrent
import com.bearcave.passageplanning.waypoints.database.Waypoint
import com.bearcave.passageplanning.waypoints.position_view.LatitudeFragment
import com.bearcave.passageplanning.waypoints.position_view.LongitudeFragment
import kotlinx.android.synthetic.main.content_waypoint_editor.*

class WaypointEditorActivity : BaseEditorActivity<Waypoint>() {

    private var id: Int = -2

    private var longitude: LongitudeFragment? = null
    private var latitude: LatitudeFragment? = null

    override fun findViews() {
        super.findViews()
        gauge.text = Gauge.SOUTHEND.humanCode
        optionalGauge.text = Gauge.SOUTHEND.humanCode
        tideCurrent.text = TideCurrent.GRAVESEND_REACH.tideCurrentStation

        latitude = supportFragmentManager.findFragmentById(R.id.latitude) as LatitudeFragment?
        longitude = supportFragmentManager.findFragmentById(R.id.longitude) as LongitudeFragment?

        gaugeMenuOpener.setOnClickListener { this.showGaugeMenu(gauge)  }
        optionalGaugeMenuOpener.setOnClickListener { if(optionalGaugeChecker.isChecked) this.showGaugeMenu(optionalGauge)  }
        tideCurrentMenuOpener.setOnClickListener { this.showTideCurrentMenu(it)  }
    }


    override fun setViewsContent(`object`: Waypoint) {
        id = `object`.id
        name.setText(`object`.name)
        note.setText(`object`.note)
        characteristic.setText(`object`.characteristic)
        ukc.setText(`object`.ukc.toString())
        latitude?.position = `object`.latitude
        longitude?.position = `object`.longitude
        gauge.text = `object`.gauge.humanCode
        optionalGauge.text = `object`.optionalGauge.humanCode
        optionalGaugeChecker.isChecked = `object`.gauge.id != `object`.optionalGauge.id
    }

    override val contentLayoutId: Int
        get() = R.layout.content_waypoint_editor


    override val isAllFilled: Boolean
        get() = !(name!!.text.isEmpty()
                    || ukc!!.text.isEmpty())


    override val filledDAO: Waypoint
        get() = Waypoint(
                id,
                name.text.toString(),
                note.text.toString(),
                characteristic.text.toString(),
                ukc.text.toString().toFloat(),
                latitude!!.position,
                longitude!!.position,
                Gauge.getByName(gauge.text.toString()),
                if(optionalGaugeChecker.isChecked)  Gauge.getByName(optionalGauge.text.toString())
                else                                Gauge.getByName(gauge.text.toString()),
                TideCurrent.getByName(tideCurrent!!.text.toString())
        )

    private fun showGaugeMenu(anchor: TextView) {
        val menu = PopupMenu(this, anchor)

        for (gauge in Gauge.values()) {
            menu.menu.add(Menu.NONE, gauge.id, Menu.NONE, gauge.humanCode)
        }

        menu.setOnMenuItemClickListener { item ->
            anchor.text = Gauge.getById(item.itemId).humanCode
            true
        }

        menu.show()
    }

    private fun showTideCurrentMenu(anchor: View){
        val menu = PopupMenu(this, anchor)
        for (tide in TideCurrent.values()) {
            menu.menu.add(Menu.NONE, tide.id, Menu.NONE, tide.tideCurrentStation)
        }

        menu.setOnMenuItemClickListener { item ->
            tideCurrent.text = TideCurrent.getById(item.itemId).tideCurrentStation
            true
        }

        menu.show()
    }
}
