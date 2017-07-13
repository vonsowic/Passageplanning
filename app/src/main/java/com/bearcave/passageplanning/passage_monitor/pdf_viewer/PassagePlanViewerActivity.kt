package com.bearcave.passageplanning.passage_monitor.pdf_viewer

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebView
import butterknife.ButterKnife
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.passage_monitor.PassagePlan


class PassagePlanViewerActivity : AppCompatActivity() {

    private var plan: PassagePlan? = null

    private var mShareActionProvider: ShareActionProvider? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passage_plan_viewer)

        plan = intent.getParcelableExtra<PassagePlan>(PASSAGE_PLAN_KEY)
        ButterKnife.findById<WebView>(this, R.id.monitor)
                .loadData(plan!!.toHTML(this), "text/html", null)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.pdf_viewer_menu, menu)

        // Locate MenuItem with ShareActionProvider
        val item = menu.findItem(R.id.menu_item_share)

        // Fetch and store ShareActionProvider
        //mShareActionProvider = item.actionProvider as ShareActionProvider?

        // Return true to display menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.menu_item_share){
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.type = "application/pdf"
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, plan!!.passage.name)
            sendIntent.putExtra(Intent.EXTRA_STREAM, plan!!.toPDF(this).toURI())
            startActivity(Intent.createChooser(sendIntent, "Send to"))
        }

        return true
    }

    // Call to update the share intent
    private fun setShareIntent(shareIntent: Intent) {
        if (mShareActionProvider != null) {
            mShareActionProvider!!.setShareIntent(shareIntent)
        }
    }

    companion object {
        val PASSAGE_PLAN_KEY = "passage_paln_key"
    }
}
