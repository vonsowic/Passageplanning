package com.bearcave.passageplanning.waypoints

import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseEditorActivity
import com.bearcave.passageplanning.tides.utils.Gauge
import com.bearcave.passageplanning.waypoints.database.Waypoint
import com.bearcave.passageplanning.waypoints.position_view.LatitudeFragment
import com.bearcave.passageplanning.waypoints.position_view.LongitudeFragment
import kotlinx.android.synthetic.main.content_waypoint_editor.*
import kotlinx.android.synthetic.main.passage_item.*

class WaypointEditorActivity : BaseEditorActivity<Waypoint>() {

    private var id: Int = -2

    private var longitude: LongitudeFragment? = null
    private var latitude: LatitudeFragment? = null

    override fun findViews() {
        super.findViews()
        gauge.text = Gauge.MARGATE.humanCode

        val gaugeMenuOpener = ButterKnife.findById<View>(this, R.id.gaugeMenuOpener)
        gaugeMenuOpener.setOnClickListener { this.openContextMenu(it)  }
        registerForContextMenu(gaugeMenuOpener)
    }

    override fun setViewsContent(`object`: Waypoint) {
        id = `object`.id
        name.setText(`object`.name)
        note.setText(`object`.note)
        characteristic.setText(`object`.characteristic)
        ukc.setText(`object`.ukc.toString())
        latitude?.position = `object`.latitude
        longitude?.position = `object`.longitude
        gauge!!.text = `object`.gauge.humanCode
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
