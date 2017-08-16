package com.bearcave.passageplanning.passages

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseManagerAdapterWithWaypoints
import com.bearcave.passageplanning.passages.database.Passage

/**
 *
 * @author Michał Wąsowicz
 * @since 27.05.17
 * @version 1.0
 */
class PassageManagerAdapter(parent: PassageManagerFragment, context: Context) : BaseManagerAdapterWithWaypoints<Passage, Int>(parent, context) {

    init {
        addOption("Start passage", { dao ->
            parent.startPassage(dao)
        })

        addOption(context.getString(R.string.action_edit), { dao ->
            parent.openEditor(dao)
        })

        addOption(context.getString(R.string.show_more), { dao ->
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setMessage("""
                |Draught: ${dao.draught}${context.getString(R.string.depth_unit)}
                |Speed: ${dao.speed}${context.getString(R.string.speed_unit)}
            """.trimMargin())
            alertDialog.show()
        })

        addOption(context.getString(R.string.action_delete), { dao ->
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setTitle("Are you sure you want to delete this?")
            alertDialog.setButton(
                    AlertDialog.BUTTON_POSITIVE,
                    "Yes",
                    {
                        _, _ ->
                        run {
                            parent.delete(dao)
                            container.remove(dao)
                            notifyDataSetChanged()
                        }
                    }
            )


            alertDialog.setButton(
                    AlertDialog.BUTTON_NEGATIVE,
                    "No",
                    { _, _ ->  }
            )

            alertDialog.show()
        })
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.passage_child_item, parent, false)

        val title = ButterKnife.findById<TextView>(view, R.id.waypoint)
        title.text = getWaypointById(
                container[groupPosition]
                        .route
                        .waypointsIds[childPosition]
        ).name

        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int = container[groupPosition].route.waypointsIds.size

}