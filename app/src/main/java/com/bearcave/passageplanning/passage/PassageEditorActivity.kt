package com.bearcave.passageplanning.passage

import android.content.Intent
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import butterknife.ButterKnife
import com.android.datetimepicker.date.DatePickerDialog
import com.android.datetimepicker.time.RadialPickerLayout
import com.android.datetimepicker.time.TimePickerDialog
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseEditorActivity
import com.bearcave.passageplanning.passage.database.PassageDao
import com.bearcave.passageplanning.routes.database.route.RouteDAO
import org.joda.time.DateTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PassageEditorActivity : BaseEditorActivity<PassageDao>(),
                                DatePickerDialog.OnDateSetListener,
                                TimePickerDialog.OnTimeSetListener {

    var id = -2

    var time: TextView? = null
    var date: TextView? = null
    var routeName: TextView? = null
    var speedText: EditText? = null

    var chosenRoute: Int = 0
    var routes: ArrayList<RouteDAO> = ArrayList()


    override fun setViewsContent(passage: PassageDao) {
        routeName!!.text = passage.route.name
        date!!.text = passage.dateTime.toDate().toString()
        time!!.text = passage.dateTime.toLocalTime().toString()
    }

    override fun getParcelableExtra(intent: Intent?) {
        super.getParcelableExtra(intent)
        routes = intent?.getParcelableArrayListExtra<RouteDAO>(STATIC_FIELDS.ROUTE_KEY) as ArrayList<RouteDAO>
    }

    override fun findViews() {
        super.findViews()
        routeName = ButterKnife.findById(this, R.id.route)
        if ( routes.size != 0)
            routeName!!.text = routes[chosenRoute].name

        registerForContextMenu(routeName)
        routeName!!.setOnClickListener(this::openContextMenu)

        date = ButterKnife.findById(this, R.id.date)
        date!!.text = dateFormat.format(calendar.time)
        date!!.setOnClickListener {
            DatePickerDialog.newInstance(
                    this,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show(fragmentManager, "datePicker")
        }

        time = ButterKnife.findById(this, R.id.time)
        time!!.text = timeFormat.format(calendar.time)
        time!!.setOnClickListener {
            TimePickerDialog.newInstance(
                    this,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE), true
            ).show(fragmentManager, "timePicker")
        }

        speedText = ButterKnife.findById(this, R.id.speed)
    }

    override fun getContentLayoutId(): Int {
        return R.layout.content_passage_editor
    }

    override fun isAllFilled(): Boolean {
        return true
    }

    override fun getFilledDAO(): PassageDao {
        return PassageDao(
                id,
                routes[chosenRoute],
                DateTime(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.HOUR),
                        calendar.get(Calendar.MINUTE)
                ),
                speedText!!.text.toString().toFloat()
        )
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.setHeaderTitle(R.string.editor_route_chooser_title)

        for ((i, route) in routes.withIndex()) {
            menu.add(Menu.NONE, i, Menu.NONE, route.name)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        chosenRoute = item.itemId
        return super.onContextItemSelected(item)
    }

    override fun onDateSet(dialog: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendar.set(year, monthOfYear, dayOfMonth)

        date!!.text = dateFormat.format(calendar.time)
    }

    override fun onTimeSet(view: RadialPickerLayout?, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        time!!.text = timeFormat.format(calendar.time)
    }


    object STATIC_FIELDS {
        val ROUTE_KEY = "key_for_routes_to_be_received"
    }

    val TIME_PATTERN = "HH:mm"

    private var calendar: Calendar = Calendar.getInstance()
    private var dateFormat: DateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault())
    private var timeFormat: SimpleDateFormat = SimpleDateFormat(TIME_PATTERN, Locale.getDefault())
}
