package com.bearcave.passageplanning.base


import android.content.Intent
import android.os.Parcelable
import android.support.design.widget.FloatingActionButton
import android.view.View
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.database.withcustomkey.DatabaseElementWithCustomKey

/**
 * BasePoorManager with FloatingActionButton, which opens DAO Editor.
 * @see BasePoorManagerFragment
 * @author Michał Wąsowicz
 * @version 1.0
 */
abstract class BaseManagerFragment<DAO, T> : BasePoorManagerFragment<DAO, T>() where DAO : Parcelable, DAO : DatabaseElementWithCustomKey<T>{


    //@OnClick(R.id.open_editor)
    fun openEditor()  {
        openEditor(null)
    }

    override fun findViews(parent: View) {
        super.findViews(parent)
        ButterKnife.findById<FloatingActionButton>(parent, R.id.open_editor).setOnClickListener { openEditor() }
    }

    /**
     * @param element - Data Access Object. If null, then editor creates a new one.
     */
    fun openEditor(element: DAO?) {
        val intent = Intent(context, editorClass)

        if (element != null) {
            intent.putExtra(BaseEditorActivity.EDITOR_RESULT, element)
        }

        putExtra(intent)
        startActivityForResult(intent, BaseEditorActivity.EDITOR_REQUEST.toInt())
    }

    protected abstract val editorClass: Class<out BaseEditorActivity<DAO>>

    /**
     * @param mail - used to send object, excluding DAO.
     */
    protected open fun putExtra(mail: Intent) {
        // for possible override
    }

    override fun layoutId(): Int {
        return R.layout.fragment_base_manager
    }

    /**
     * @param requestCode
     * @param resultCode - to check if element is updated or created.
     * @param data - DAO is sent in this intent.
     * @see BaseManagerFragment.openEditor
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == BaseEditorActivity.EDITOR_REQUEST.toInt()) {
            if (resultCode == BaseEditorActivity.EDITOR_CREATED.toInt()) {
                val result = data.getParcelableExtra<DAO>(BaseEditorActivity.EDITOR_RESULT)
                onDataCreated(result)

            } else if (resultCode == BaseEditorActivity.EDITOR_UPDATED.toInt()) {
                val result = data.getParcelableExtra<DAO>(BaseEditorActivity.EDITOR_RESULT)
                onDataUpdated(result)
            }
        }
    }

    fun onDataCreated(result: DAO) {
        insert(result)
        adapter!!.add(result)
    }

    fun onDataUpdated(result: DAO) {
        database!!.update(result)
        adapter!!.update(result)
    }

}
