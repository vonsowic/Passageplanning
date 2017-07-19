package com.bearcave.passageplanning.passage_monitor.pdf_viewer

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import butterknife.ButterKnife
import com.bearcave.passageplanning.BuildConfig
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passages.planner.PassagePlan


class PassagePlanViewerActivity : AppCompatActivity() {

    private var plan: PassagePlan? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passage_plan_viewer)

        plan = intent.getParcelableExtra<PassagePlan>(PASSAGE_PLAN_KEY)
        ButterKnife.findById<WebView>(this, R.id.monitor)
                .loadData(plan!!.toHTML(this), "text/html", null)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.pdf_viewer_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.menu_item_share){
            val intent = Intent(Intent.ACTION_SEND)
            val uri = FileProvider.getUriForFile(
                    applicationContext,
                    BuildConfig.APPLICATION_ID,
                    plan!!.toPDF(this)
            )
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, uri)

            startActivity(Intent.createChooser(intent, "Send to"))
        }

        return true
    }

    companion object {
        val PASSAGE_PLAN_KEY = "passage_paln_key"
    }
}
