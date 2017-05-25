package com.bearcave.passageplanning.base

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
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
 * Created by miwas on 20.05.17.
 */

abstract class BaseManagerAdapter<Dao : DatabaseElementWithCustomKey<T>, T>(parent: BaseManagerFragment<*, *>, private val context: Context) : BaseExpandableListAdapter() {

    protected val container: ArrayList<Dao>
    protected val inflater: LayoutInflater = LayoutInflater.from(context)
    private val menuActions = SparseArray<OnMenuItemSelectedAction<Dao>>()
    protected val database: CRUD<Dao> = parent as CRUD<Dao>


    init {
        container = database.readAll() as ArrayList<Dao>
    }

    fun add(element: Dao) {
        container.add(element)
        notifyDataSetChanged()
    }

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

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View, parent: ViewGroup): View {
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

    protected open fun createPopupMenu(anchor: View): PopupMenu {
        return PopupMenu(context, anchor)
    }

    private fun showPopupMenu(anchor: View, selected: Dao) {
        val menu = createPopupMenu(anchor)

        menu.setOnMenuItemClickListener { item ->
            menuActions.get(item.itemId).exec(selected)
            true
        }

        menu.show()
    }

    protected fun addOption(title: String, option: OnMenuItemSelectedAction<T>) {
        //menu.getMenu().add(Menu.NONE, EDIT_CODE, Menu.NONE, context.getString(R.string.action_edit));
    }



    @FunctionalInterface
    protected interface OnMenuItemSelectedAction<in Dao> {
        fun exec(element: Dao)
    }
}
