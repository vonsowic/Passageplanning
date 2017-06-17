package com.bearcave.passageplanning.tides.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bearcave.passageplanning.base.BaseManagerAdapter
import com.bearcave.passageplanning.base.BasePoorManagerFragment
import com.bearcave.passageplanning.tides.database.TideItem
import org.joda.time.DateTime

/**
 *
 * @author Michał Wąsowicz
 * @since 04.06.17
 * @version 1.0
 */
class TideManagerAdapter(parent: BasePoorManagerFragment<TideItem, DateTime>, context: Context)
    : BaseManagerAdapter<TideItem, DateTime>(parent, context) {

    override fun getChildrenCount(groupPosition: Int): Int {
        TODO("not implemented")
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        TODO("not implemented")
    }

}