package com.bearcave.passageplanning.base


import android.content.Context
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.database.withcustomkey.CRUDWithCustomKey
import com.bearcave.passageplanning.base.database.withcustomkey.DatabaseElementWithCustomKey
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener

/**
 * Shows DAOs from database using adapter extending BaseManagerAdapter.
 *
 * @see BaseManagerAdapter
 * @version 1.0
 * @author Michał Wąsowicz
 */
abstract class BasePoorManagerFragment<DAO : DatabaseElementWithCustomKey<T>, T>
    : BaseFragment(),
        CRUDWithCustomKey<DAO, T> {

    protected var adapter: BaseManagerAdapter<DAO, T>? = null
    protected var listView: ExpandableListView? = null

    protected abstract val databaseKey: Int

    protected var database: CRUDWithCustomKey<DAO, T>? = null

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val databaseHolder = context as OnDatabaseRequestedListener
        saveDatabaseHolder(databaseHolder)
        database = databaseHolder.onGetTableListener(databaseKey) as CRUDWithCustomKey<DAO, T>
    }

    protected open fun saveDatabaseHolder(holder: OnDatabaseRequestedListener){
        // to override
    }

    override fun layoutId() = R.layout.fragment_base_poor_manager

    protected abstract fun createAdapter(): BaseManagerAdapter<DAO, T>

    override fun findViews(parent: View) {
        super.findViews(parent)
        listView = ButterKnife.findById<ExpandableListView>(parent, R.id.list_view)
        adapter = createAdapter()
        listView!!.setAdapter(adapter)
    }

    override fun onDetach() {
        super.onDetach()
        listView!!.removeAllViewsInLayout() // detach view
    }

    override fun insert(element: DAO) = database!!.insert(element)

    override fun read(id: T) = database!!.read(id)

    override fun readAll() = database!!.readAll()

    override fun update(element: DAO) = database!!.update(element)

    override fun delete(element: DAO) = database!!.delete(element)
}
