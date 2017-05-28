package com.bearcave.passageplanning.passage


import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseEditorActivity
import com.bearcave.passageplanning.base.BaseManagerAdapter
import com.bearcave.passageplanning.base.BaseManagerFragment
import com.bearcave.passageplanning.base.database.withcustomkey.DatabaseElementWithCustomKey
import com.bearcave.passageplanning.data.database.OnDatabaseRequestedListener
import com.bearcave.passageplanning.passage.database.PassageCRUD
import com.bearcave.passageplanning.passage.database.PassageDao
import com.bearcave.passageplanning.passage.database.PassageTable
import com.bearcave.passageplanning.passage.database.ReadRoutes
import java.util.*


/**
 * @author Michał Wąsowicz
 * @since 27.05.17
 * @version 1.0
 */
class PassageManagerFragment : BaseManagerFragment<PassageDao, Int>() {

    var database: PassageTable? = null
    var routeDatabase: ReadRoutes? = null

    override fun getEditorClass(): Class<out BaseEditorActivity<PassageDao>> {
        return PassageEditorActivity::class.java
    }

    override fun onDataCreated(result: PassageDao?) {
        database!!.insert(result)
        adapter.add(result)
    }

    override fun onDataUpdated(result: PassageDao?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun putExtra(mail: Intent?) {
        super.putExtra(mail)

        mail?.putParcelableArrayListExtra(
                PassageEditorActivity.STATIC_FIELDS.ROUTE_KEY,
                routeDatabase!!.readAllRoutes() as ArrayList<out Parcelable>
                )
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val databaseHolder = context as OnDatabaseRequestedListener
        database = databaseHolder.onGetTableListener(PassageCRUD.ID) as PassageTable
        routeDatabase = context as ReadRoutes
    }

    override fun insert(element: PassageDao): Long {
        return database!!.insert(element)
    }

    override fun read(id: Int): PassageDao {
        return database!!.read(id)
    }

    override fun readAll(): MutableList<PassageDao> {
        return database!!.readAll()
    }

    override fun update(element: PassageDao): Int {
        return database!!.update(element)
    }

    override fun delete(element: PassageDao): Int {
        return database!!.delete(element)
    }

    override fun getTitle(): Int {
        return R.string.passage_manager_title
    }

    override fun createAdapter(): BaseManagerAdapter<out DatabaseElementWithCustomKey<*>, *> {
        return PassageManagerAdapter(this, context)
    }
}
