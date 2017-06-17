package com.bearcave.passageplanning.passage_monitor

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passages.database.Passage

class PassageMonitorActivity : AppCompatActivity() {

    var passage: Passage? = null
    //val waypoints =

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passage_monitor)
        passage = intent.getParcelableExtra<Passage>(PASSAGE_KEY)

        title = passage!!.route.name

    }

    companion object {
        val PASSAGE_KEY = "passage key"
    }
}
