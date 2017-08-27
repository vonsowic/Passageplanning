package com.bearcave.passageplanning.routes

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseManagerAdapterWithWaypoints
import com.bearcave.passageplanning.routes.database.Route


class RouteManagerAdapter(parent: RouteManagerFragment, context: Context) : BaseManagerAdapterWithWaypoints<Route, Int>(parent, context) {


    init {
         addOption(context.getString(R.string.action_edit), { dao ->
             parent.openEditor(dao)
         })
    }


    private fun getWaypointFromList(group: Int, child: Int) = getWaypointById(
            container[group]
                    .waypointsIds[child]
        )

    override fun getChildrenCount(groupPosition: Int) = container[groupPosition].waypointsIds.size

    override fun getChild(groupPosition: Int, childPosition: Int) = getWaypointFromList(groupPosition, childPosition)

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.passage_manager_child_item, parent, false)

        val (_, name) = getWaypointFromList(groupPosition, childPosition)

        val title = ButterKnife.findById<TextView>(view!!, R.id.name_placeholder)
        title.text = name

        val up = ButterKnife.findById<ImageView>(view, R.id.up)
        up.setOnClickListener {
            if (childPosition > 0) {
                val tmpWpts = container[groupPosition].waypointsIds
                val tmpId = tmpWpts[childPosition - 1]
                tmpWpts[childPosition - 1] = tmpWpts[childPosition]
                tmpWpts[childPosition] = tmpId
                notifyDataSetChanged()
                database.update(container[groupPosition])
            }
        }

        val down = ButterKnife.findById<ImageView>(view, R.id.down)
        down.setOnClickListener {
            if (childPosition < getChildrenCount(groupPosition) - 1) {
                val tmpWpts = container[groupPosition].waypointsIds
                val tmpId = tmpWpts[childPosition + 1]
                tmpWpts[childPosition + 1] = tmpWpts[childPosition]
                tmpWpts[childPosition] = tmpId
                notifyDataSetChanged()
                database.update(container[groupPosition])
            }
        }

        return view
    }
}
