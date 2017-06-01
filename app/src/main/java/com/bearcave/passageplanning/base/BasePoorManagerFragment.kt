package com.bearcave.passageplanning.base


import android.content.Context
import android.view.View
import android.widget.ExpandableListView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.database.withcustomkey.CRUDWithCustomKey
import com.bearcave.passageplanning.base.database.withcustomkey.DatabaseElementWithCustomKey
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener

/**
 * Shows ExpandableListView using adapter, which extends BaseManagerAdapter.
 *
 * @see BaseManagerAdapter
 * @version 1.0
 * @author Michał Wąsowicz
 */
abstract class BasePoorManagerFragment<DAO : DatabaseElementWithCustomKey<T>, T>
    : BaseFragment(),
        CRUDWithCustomKey<DAO, T> {

    protected abstract var adapter: BaseManagerAdapter<DAO, T>

    protected abstract val databaseKey: Int

    protected var database: CRUDWithCustomKey<DAO, T>? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val databaseHolder = context as OnDatabaseRequestedListener
        database = databaseHolder.onGetTableListener(databaseKey) as CRUDWithCustomKey<DAO, T>
    }

    override fun layoutId(): Int {
        return R.layout.fragment_base_poor_manager
    }

    protected abstract fun createAdapter(): BaseManagerAdapter<DAO, T>

    override fun findViews(parent: View) {
        super.findViews(parent)
        val listView = ButterKnife.findById<ExpandableListView>(parent, R.id.list_view)
        adapter = createAdapter()
        listView.setAdapter(adapter)
    }

    override fun insert(element: DAO): Long = database!!.insert(element)

    override fun read(id: T): DAO = database!!.read(id)

    override fun readAll(): List<DAO> = database!!.readAll()

    override fun update(element: DAO): Int = database!!.update(element)

    override fun delete(element: DAO): Int = database!!.delete(element)
}
