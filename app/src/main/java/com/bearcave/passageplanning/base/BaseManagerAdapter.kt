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
import com.bearcave.passageplanning.tasks.BackgroundTask
import java.util.*

/**
 * All ExpandableListViews used in BasePoorManager should extend this class.
 *
 * @see BasePoorManagerFragment
 * @author Michał Wąsowicz
 * @version 1.0
 * @since 20.05.17
 */
abstract class BaseManagerAdapter<Dao : DatabaseElementWithCustomKey<T>, T>
    (parent: BasePoorManagerFragment<*, *>, protected val context: Context)
    : BaseExpandableListAdapter() {

    /**
     * This container has all DAOs shown in ExpandableListView.
     */
    protected var container = ArrayList<Dao>()


    protected val inflater: LayoutInflater
        get() = LayoutInflater.from(context)

    @Suppress("UNCHECKED_CAST")
    protected val database: CRUDWithCustomKey<Dao, T> = parent as CRUDWithCustomKey<Dao, T>
    private val commands = ArrayList<Command>()


    init {
        AdapterTask().execute({
            container = database.readAll() as ArrayList<Dao>
        })
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
                container[ i ] = toBeUpdated
                notifyDataSetChanged()
                return
            }
        }
    }

    override fun getGroupCount() = container.size

    override fun getGroup(groupPosition: Int): Any = container[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any? = null

    override fun hasStableIds() = false

    override fun getGroupId(groupPosition: Int): Long = 0

    override fun getChildId(groupPosition: Int, childPosition: Int): Long = 0

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.manager_group_item, parent, false)

        val title = ButterKnife.findById<TextView>(view, R.id.name_placeholder)
        title.text = container[groupPosition].name

        val option = ButterKnife.findById<ImageView>(view, R.id.options_button)
        option.setOnClickListener { v -> showPopupMenu(v, container[groupPosition]) }

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = false

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

    private inner class AdapterTask : BackgroundTask(context) {
        override fun onPostExecute(result: Int) {
            super.onPostExecute(result)
            notifyDataSetChanged()
        }
    }
}
