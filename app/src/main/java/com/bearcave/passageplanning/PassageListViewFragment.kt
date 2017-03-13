package com.bearcave.passageplanning


import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ListView
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.waypoints.ListViewAdapter


/**
 * @author Michał Wąsowicz
 */
class PassageListViewFragment : Fragment() {

    internal var listView: ListView? = null
    internal var adapter : ListViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_passage_list_view, container, false)
        listView = view?.findViewById(R.id.listView) as ListView?
        adapter = ListViewAdapter(context)

        return view
    }

}