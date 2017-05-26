package com.bearcave.passageplanning.base

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView

import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.data.database.tables.base.CRUD
import com.bearcave.passageplanning.data.database.tables.base.withcustomkey.DatabaseElementWithCustomKey

import java.util.ArrayList

import butterknife.ButterKnife

/**
 * All ExpandableListViews used in BasePoorManager should extend this class.
 *
 * @see BasePoorManagerFragment
 * @author Michał Wąsowicz
 * @version 1.0
 * @since 20.05.17
 */
abstract class BaseManagerAdapter<Dao : DatabaseElementWithCustomKey<T>, T>(parent: BaseManagerFragment<*, *>, private val context: Context) : BaseExpandableListAdapter() {

    /**
     * This container has all DAOs shown in ExpandableListView.
     */
    protected val container: ArrayList<Dao>


    protected val inflater: LayoutInflater
    protected val database: CRUD<Dao> = parent as CRUD<Dao>
    private val commands = ArrayList<Command>()


    init {
        container = database.readAll() as ArrayList<Dao>
        inflater = LayoutInflater.from(context)
    }

    /**
     * @param element - send from external class to be added in list view.
     */
    fun add(element: Dao) {
        container.add(element)
        notifyDataSetChanged()
    }

    /**
     * @param toBeUpdated - send from external class to be updated in list view.
     */
    fun update(toBeUpdated: Dao) {
        for (element in container) {
            var i = 0
            if (element.id === toBeUpdated.id) {
                container.removeAt(i)
                container.add(i, element)
                notifyDataSetChanged()
            }
            i++
        }
    }

    override fun getGroupCount(): Int {
        return container.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return container[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any? {
        return null
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupId(groupPosition: Int): Long {
        return 0
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return 0
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        val view = inflater.inflate(R.layout.manager_group_item, parent, false)

        val title = ButterKnife.findById<TextView>(view, R.id.name)
        title.text = container[groupPosition].name

        val option = ButterKnife.findById<ImageView>(view, R.id.options_button)
        option.setOnClickListener { v -> showPopupMenu(v, container[groupPosition]) }

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    private fun showPopupMenu(anchor: View, selected: Dao) {
        val menu = PopupMenu(context, anchor)
        for (command in commands){
              menu.menu.add(Menu.NONE, command.i, Menu.NONE, command.name)
        }

        menu.setOnMenuItemClickListener { item ->
            commands[item.itemId].command.exec(selected)
            true
        }

        menu.show()
    }

    var INDEX = 0 // used only in addOption for options indexing.


    protected fun addOption(title: String, option: OnMenuItemSelectedAction<Dao>) {
        commands.add(Command(INDEX++, title, option))
    }



    @FunctionalInterface
    protected interface OnMenuItemSelectedAction<in Dao> {
        fun exec(element: Dao)
    }

    private inner class Command(val i: Int, val name: String, val command: OnMenuItemSelectedAction<Dao>)
}
