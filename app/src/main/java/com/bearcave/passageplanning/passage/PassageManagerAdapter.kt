package com.bearcave.passageplanning.passage

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bearcave.passageplanning.base.BaseManagerAdapter
import com.bearcave.passageplanning.passage.database.Passage

/**
 *
 * @author Michał Wąsowicz
 * @since 27.05.17
 * @version 1.0
 */
class PassageManagerAdapter(parent: PassageManagerFragment, context: Context) : BaseManagerAdapter<Passage, Int>(parent, context) {

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}