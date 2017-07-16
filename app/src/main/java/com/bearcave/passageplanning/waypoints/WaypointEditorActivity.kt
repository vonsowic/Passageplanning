package com.bearcave.passageplanning.waypoints

import android.support.design.widget.TextInputEditText
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseEditorActivity
import com.bearcave.passageplanning.tides.web.configurationitems.Gauge
import com.bearcave.passageplanning.waypoints.database.Waypoint
import com.bearcave.passageplanning.waypoints.position_view.LatitudeFragment
import com.bearcave.passageplanning.waypoints.position_view.LongitudeFragment

class WaypointEditorActivity : BaseEditorActivity<Waypoint>() {

    private var name: TextInputEditText? = null
    private var note: TextInputEditText? = null
    private var ukc: TextInputEditText? = null
    private var longitude: LongitudeFragment? = null
    private var latitude: LatitudeFragment? = null
    private var characteristic: TextInputEditText? = null
    private var gauge: TextView? = null

    private var id: Int? = -2

    override fun findViews() {
        super.findViews()
        name = ButterKnife.findById<TextInputEditText>(this, R.id.name_text)
        note = ButterKnife.findById<TextInputEditText>(this, R.id.note_text)
        characteristic = ButterKnife.findById<TextInputEditText>(this, R.id.characteristic_text)
        ukc = ButterKnife.findById<TextInputEditText>(this, R.id.ukc_text)
        gauge = ButterKnife.findById<TextView>(this, R.id.gauge_name)
        gauge!!.text = Gauge.MARGATE.humanCode

        latitude = supportFragmentManager.findFragmentById(R.id.latitude) as LatitudeFragment?
        longitude = supportFragmentManager.findFragmentById(R.id.longitude) as LongitudeFragment?

        val gaugeMenuOpener = ButterKnife.findById<View>(this, R.id.gauge_chooser)
        gaugeMenuOpener.setOnClickListener { this.openContextMenu(it)  }
        registerForContextMenu(gaugeMenuOpener)
    }

    override fun setViewsContent(`object`: Waypoint) {
        id = `object`.id
        name!!.setText(`object`.name)
        note!!.setText(`object`.note)
        characteristic!!.setText(`object`.characteristic)
        ukc!!.setText(`object`.ukc.toString())
        latitude!!.position = `object`.latitude
        longitude!!.position = `object`.longitude
        gauge!!.text = `object`.gauge.humanCode
    }

    override val contentLayoutId: Int
        get() = R.layout.content_waypoint_editor


    override val isAllFilled: Boolean
        get() {
            return !(name!!.text.isEmpty()
                    || ukc!!.text.isEmpty())
        }

    override val filledDAO: Waypoint
        get() = Waypoint(
                id!!,
                name!!.text.toString(),
                note!!.text.toString(),
                characteristic!!.text.toString(),
                ukc!!.text.toString().toFloat(),
                latitude!!.position,
                longitude!!.position,
                Gauge.getByName(gauge!!.text.toString())
        )

    override fun onCreateContextMenu(menu: ContextMenu, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menu.setHeaderTitle(R.string.editor_gauge_chooser_title)

        for (gauge in Gauge.values()) {
            menu.add(Menu.NONE, gauge.id, Menu.NONE, gauge.humanCode)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        gauge!!.text = Gauge.getById(item.itemId).humanCode
        return super.onContextItemSelected(item)
    }
}
