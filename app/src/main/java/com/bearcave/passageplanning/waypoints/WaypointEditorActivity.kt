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

class WaypointEditorActivity : BaseEditorActivity<Waypoint>() {

    private var name: TextInputEditText? = null
    private var note: TextInputEditText? = null
    private var ukc: TextInputEditText? = null
    private var longitude: TextInputEditText? = null
    private var latitude: TextInputEditText? = null
    private var characteristic: TextInputEditText? = null
    private var gauge: TextView? = null

    private var id: Int? = -2

    override fun findViews() {
        super.findViews()
        name = ButterKnife.findById<TextInputEditText>(this, R.id.name_text)
        note = ButterKnife.findById<TextInputEditText>(this, R.id.note_text)
        characteristic = ButterKnife.findById<TextInputEditText>(this, R.id.characteristic_text)
        ukc = ButterKnife.findById<TextInputEditText>(this, R.id.ukc_text)
        latitude = ButterKnife.findById<TextInputEditText>(this, R.id.latitude_text)
        longitude = ButterKnife.findById<TextInputEditText>(this, R.id.longitude_text)
        gauge = ButterKnife.findById<TextView>(this, R.id.gauge_name)
        gauge!!.text = Gauge.MARGATE.humanCode

        registerForContextMenu(gauge)
        gauge!!.setOnClickListener({ this.openContextMenu(it) })
    }

    override fun setViewsContent(waypoint: Waypoint) {
        id = waypoint.id
        name!!.setText(waypoint.name)
        note!!.setText(waypoint.note)
        characteristic!!.setText(waypoint.characteristic)
        ukc!!.setText(waypoint.ukc.toString())
        latitude!!.setText(waypoint.getLatitudeInSecondFormat())
        longitude!!.setText(waypoint.getLongitudeInSecondFormat())
        gauge!!.text = waypoint.gauge.humanCode
    }

    override val contentLayoutId: Int
        get() = R.layout.content_waypoint_editor


    override val isAllFilled: Boolean
        get() {
            return !(name!!.text.isEmpty()
                    || ukc!!.text.isEmpty()
                    || latitude!!.text.isEmpty()
                    || longitude!!.text.isEmpty())
        }

    override val filledDAO: Waypoint
        get() = Waypoint(
                id!!,
                name!!.text.toString(),
                note!!.text.toString(),
                characteristic!!.text.toString(),
                java.lang.Float.valueOf(ukc!!.text.toString())!!,
                latitude!!.text.toString(),
                longitude!!.text.toString(),
                Gauge.getByName(gauge!!.text.toString())
        )

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo) {
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
