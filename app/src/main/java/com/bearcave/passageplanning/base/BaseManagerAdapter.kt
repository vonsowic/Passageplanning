package com.bearcave.passageplanning.base

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.database.withcustomkey.CRUDWithCustomKey
import com.bearcave.passageplanning.base.database.withcustomkey.DatabaseElementWithCustomKey
import java.util.*

/**
 * All ExpandableListViews used in BasePoorManager should extend this class.
 *
 * @see BasePoorManagerFragment
 * @author Michał Wąsowicz
 * @version 1.0
 * @since 20.05.17
 */
abstract class BaseManagerAdapter<Dao : DatabaseElementWithCustomKey<T>, T>(parent: BasePoorManagerFragment<*, *>, protected val context: Context) : BaseExpandableListAdapter() {

    /**
     * This container has all DAOs shown in ExpandableListView.
     */
    protected val container: ArrayList<Dao>


    protected val inflater: LayoutInflater

    @Suppress("UNCHECKED_CAST")
    protected val database: CRUDWithCustomKey<Dao, T> = parent as CRUDWithCustomKey<Dao, T>
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
     * @param toBeUpdated - sent from external class to be updated in list view.
     */
    fun update(toBeUpdated: Dao) {
        for ((i, element) in container.withIndex()) {
            if (element.id == toBeUpdated.id) {
                container[ i ] = element
                notifyDataSetChanged()
                return
            }
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
        val view = convertView ?: inflater.inflate(R.layout.manager_group_item, parent, false)

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
            commands[item.itemId].command(selected)
            true
        }

        menu.show()
    }

    var INDEX = 0 // used only in addOption for options indexing.


    protected fun addOption(title: String, option: (Dao)->Unit) {
        commands.add(Command(INDEX++, title, option))
    }


    private inner class Command(val i: Int, val name: String, val command: (Dao)->Unit)
}
