package com.bearcave.passageplanning.passage_monitor.pdf_viewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passage_monitor.PassagePlan

class PassagePlanViewerActivity : AppCompatActivity() {

    var plan: PassagePlan? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passage_plan_viewer)

        plan = intent.getParcelableExtra<PassagePlan>(PASSAGE_PLAN_KEY)
        ButterKnife.findById<WebView>(this, R.id.monitor)
                .loadData(plan!!.toHTML(this), "text/html", null)
    }

    companion object {
        val PASSAGE_PLAN_KEY = "passage_paln_key"
    }
}
