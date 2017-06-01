package com.bearcave.passageplanning.passage


import android.content.Context
import android.content.Intent
import android.os.Parcelable
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseEditorActivity
import com.bearcave.passageplanning.base.BaseManagerAdapter
import com.bearcave.passageplanning.base.BaseManagerFragment
import com.bearcave.passageplanning.passage.database.Passage
import com.bearcave.passageplanning.passage.database.PassageCRUD
import com.bearcave.passageplanning.passage.database.ReadRoutes
import java.util.*


/**
 * @author Michał Wąsowicz
 * @since 27.05.17
 * @version 1.0
 */
class PassageManagerFragment : BaseManagerFragment<Passage, Int>() {


    override val databaseKey: Int
        get() = PassageCRUD.ID

    override var adapter: BaseManagerAdapter<Passage, Int>
        get() = adapter
        set(value) { adapter = value }

    override val editorClass: Class<out BaseEditorActivity<Passage>>
        get() = PassageEditorActivity::class.java

    var routeDatabase: ReadRoutes? = null
        get() = null

    override fun onDataCreated(result: Passage) {
        database!!.insert(result)
        adapter.add(result)
    }

    override fun onDataUpdated(result: Passage) {
        update(result)
        adapter.update(result)
    }

    override fun putExtra(mail: Intent) {
        super.putExtra(mail)

        mail.putParcelableArrayListExtra(
                PassageEditorActivity.ROUTE_KEY,
                routeDatabase!!.readAllRoutes() as ArrayList<out Parcelable>
        )
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        routeDatabase = context as ReadRoutes
    }

    override val title: Int
        get() = R.string.passage_manager_title


    override fun createAdapter(): BaseManagerAdapter<Passage, Int> = PassageManagerAdapter(this, context)

    fun  startPassage(dao: Passage) {

    }
}
