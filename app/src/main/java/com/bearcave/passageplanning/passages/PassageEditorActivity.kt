package com.bearcave.passageplanning.passages

import android.content.Intent
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import butterknife.ButterKnife
import com.android.datetimepicker.date.DatePickerDialog
import com.android.datetimepicker.time.RadialPickerLayout
import com.android.datetimepicker.time.TimePickerDialog
import com.bearcave.passageplanning.R
import com.bearcave.passageplanning.base.BaseEditorActivity
import com.bearcave.passageplanning.passages.database.Passage
import com.bearcave.passageplanning.routes.database.Route
import com.bearcave.passageplanning.settings.Settings
import org.joda.time.DateTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PassageEditorActivity : BaseEditorActivity<Passage>(),
                                DatePickerDialog.OnDateSetListener,
                                TimePickerDialog.OnTimeSetListener {
    private var id = -2

    private var time: TextView? = null
    private var date: TextView? = null
    private var routeName: TextView? = null

    private var speedSlide: SeekBar? = null
    private var speedValueView: TextView? = null

    private var draughtSlide: SeekBar? = null
    private var draughtValueView: TextView? = null

    private var chosenRoute: Int = 0
    private var routes: ArrayList<Route> = ArrayList()

    override val isAllFilled: Boolean
        get() = !routes.isEmpty()

    override val contentLayoutId: Int
        get() = R.layout.content_passage_editor


    override fun setViewsContent(`object`: Passage) {
        id = `object`.id
        routeName!!.text = `object`.route.name

        calendar.set(
                `object`.dateTime.year,
                `object`.dateTime.monthOfYear - 1,
                `object`.dateTime.dayOfMonth,
                `object`.dateTime.hourOfDay,
                `object`.dateTime.minuteOfHour
        )

        speedSlide!!.progress = (`object`.speed * 10 / Settings.KTS).toInt()
        speedValueView!!.text = "${`object`.speed}"

        draughtSlide!!.progress = (`object`.draught * 10 / Settings.KTS).toInt()
        draughtValueView!!.text = "${`object`.draught}"

        updateTimeView()
    }

    override fun getParcelableExtra(intent: Intent) {
        super.getParcelableExtra(intent)
        routes = intent.getParcelableArrayListExtra<Route>(ROUTE_KEY)
    }

    override fun findViews() {
        super.findViews()
        routeName = ButterKnife.findById(this, R.id.route)
        if ( routes.size != 0) {
            routeName!!.text = routes[chosenRoute].name
        }
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

        speedSlide = ButterKnife.findById(this, R.id.speed)
        speedValueView = ButterKnife.findById(this, R.id.speed_value)
        initializeSlide(speedSlide!!, speedValueView!!)

        draughtSlide = ButterKnife.findById(this, R.id.draught)
        draughtValueView = ButterKnife.findById(this, R.id.draught_value)
        initializeSlide(draughtSlide!!, draughtValueView!!)
    }

    private fun initializeSlide(view: SeekBar, label: TextView){
        view.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                label.text = "${seekBar!!.progress.toFloat() / 10}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        label.text = 0f.toString()
    }


    override val filledDAO: Passage
        get() = Passage(
                    id,
                    routes[chosenRoute],
                    DateTime(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH),
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE)
                    ),
                    speedSlide!!.progress.toFloat() * Settings.KTS / 10,
                    draughtSlide!!.progress.toFloat() / 10
                )



    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.setHeaderTitle(R.string.editor_route_chooser_title)

        for ((i, route) in routes.withIndex()) {
            menu.add(Menu.NONE, i, Menu.NONE, route.name)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        chosenRoute = item.itemId
        routeName!!.text = routes[chosenRoute].name
        return super.onContextItemSelected(item)
    }

    override fun onDateSet(dialog: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        calendar.set(year, monthOfYear, dayOfMonth)
        updateTimeView()
    }

    override fun onTimeSet(view: RadialPickerLayout?, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        updateTimeView()
    }

    private fun updateTimeView() {
        time!!.text = timeFormat.format(calendar.time)
        date!!.text = dateFormat.format(calendar.time)
    }

    companion object {
        val ROUTE_KEY = "key_for_routes_to_be_received"
        private val TIME_PATTERN = "HH:mm"
    }

    private val calendar: Calendar = Calendar.getInstance()
    private val dateFormat: DateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault())
    private val timeFormat: SimpleDateFormat = SimpleDateFormat(TIME_PATTERN, Locale.getDefault())
}
