package com.bearcave.passageplanning.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import butterknife.ButterKnife
import com.bearcave.passageplanning.R

abstract class BaseEditorActivity<DAO : Parcelable> : AppCompatActivity() {

    private var updateMode = false

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_editor)
        val toolbar = ButterKnife.findById<Toolbar>(this, R.id.toolbar)
        setSupportActionBar(toolbar)

        title = getString(R.string.editor_title)

        val mail = intent
        val tmp = mail.getParcelableExtra<Parcelable>(EDITOR_RESULT)
        updateMode = tmp != null

        getParcelableExtra(mail)

        val content_placeholder = ButterKnife.findById<FrameLayout>(this, R.id.content_placeholder)
        content_placeholder.addView(layoutInflater.inflate(contentLayoutId, null))

        findViews()

        if (updateMode) {
            setViewsContent(tmp as DAO)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_editor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        if(item?.itemId == R.id.save_button){
            onSaveButtonClicked()
        }
        return true
    }

    protected open fun getParcelableExtra(intent: Intent) {

    }

    protected abstract fun setViewsContent(`object`: DAO)

    protected abstract val contentLayoutId: Int

    protected open fun findViews() {}

    fun onSaveButtonClicked() {
        if (!isAllFilled) {
            Toast.makeText(this, "Fill all gaps", Toast.LENGTH_SHORT).show()
            return
        }

        val returnIntent = Intent()
        returnIntent.putExtra(EDITOR_RESULT, filledDAO)

        if (updateMode) {
            setResult(BaseEditorActivity.EDITOR_UPDATED.toInt(), returnIntent)
        } else {
            setResult(BaseEditorActivity.EDITOR_CREATED.toInt(), returnIntent)
        }

        finish()
    }

    abstract val isAllFilled: Boolean

    protected abstract val filledDAO: DAO

    override fun onBackPressed() {
        val returnIntent = Intent()
        setResult(Activity.RESULT_CANCELED, returnIntent)
        super.onBackPressed()
    }

    companion object {
        val EDITOR_REQUEST: Short = 1
        val EDITOR_RESULT = "editor_result"
        val EDITOR_CREATED: Short = 10000
        val EDITOR_UPDATED: Short = 10001
    }
}
