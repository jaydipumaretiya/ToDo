package app.todo.ui.home.todo

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.core.app.NotificationCompat
import androidx.lifecycle.ViewModelProviders
import app.todo.R
import app.todo.data.entity.ToDoEntity
import app.todo.data.viewmodel.ToDoViewModel
import app.todo.databinding.ActivityAddTodoBinding
import app.todo.receivers.AlarmReceiver
import app.todo.receivers.AlarmReceiver.Companion.NOTIFICATION_CHANNEL_ID
import app.todo.receivers.AlarmReceiver.Companion.default_notification_channel_id
import app.todo.ui.base.BaseActivity
import app.todo.ui.base.delegate.viewBinding
import app.todo.util.Constants
import java.text.SimpleDateFormat
import java.util.*

class AddTodoActivity : BaseActivity(R.layout.activity_add_todo),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private val binding by viewBinding(ActivityAddTodoBinding::inflate)
    private var toDoEntity: ToDoEntity? = null
    private var calendar = Calendar.getInstance()
    private var type = 2
    private lateinit var simpleDateFormat: SimpleDateFormat
    private var intervalMilliseconds: Long = 24 * 60 * 60 * 1000

    private val toDoViewModel: ToDoViewModel by lazy {
        ViewModelProviders.of(this).get(ToDoViewModel::class.java)
    }

    override fun setContent() {
        val myFormat = "dd/MM/yyyy HH:mm"
        simpleDateFormat = SimpleDateFormat(myFormat, Locale.US)

        if (intent.hasExtra(Constants.EXTRA_TODO)) {
            toDoEntity = intent.getSerializableExtra(Constants.EXTRA_TODO) as ToDoEntity
            binding.btnCreate.text = getString(R.string.update)

            binding.edtTitle.setText(toDoEntity!!.title)
            binding.edtDescription.setText(toDoEntity!!.description)
            binding.tvSelectDateTime.text = simpleDateFormat.format(toDoEntity!!.dateTime!!)

            type = toDoEntity!!.types
            if (toDoEntity!!.types == 0) {
                binding.rbDaily.isChecked = true
                binding.rbWeekly.isChecked = false
            } else if (toDoEntity!!.types == 1) {
                binding.rbDaily.isChecked = false
                binding.rbWeekly.isChecked = true
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbDaily -> {
                    type = 0
                    intervalMilliseconds = 24 * 60 * 60 * 1000
                }
                R.id.rbWeekly -> {
                    type = 1
                    intervalMilliseconds = 7 * 24 * 60 * 60 * 1000
                }
            }
        }

        binding.tvSelectDateTime.setOnClickListener {
            choseDate()
        }

        binding.btnCreate.setOnClickListener {
            validate()
        }
    }

    private fun validate() {
        when {
            binding.edtTitle.text!!.isEmpty() -> {
                showToast(getString(R.string.message_valid_title))
                return
            }
            binding.edtDescription.text!!.isEmpty() -> {
                showToast(getString(R.string.message_valid_description))
                return
            }
            (type >= 2) -> {
                showToast(getString(R.string.message_valid_type))
                return
            }
            (binding.tvSelectDateTime.text.equals(getString(R.string.select_time_and_date))) -> {
                showToast(getString(R.string.message_valid_date_and_time))
                return
            }
            else -> {
                addTodo()
            }
        }
    }

    private fun addTodo() {
        if (toDoEntity == null) {
            toDoEntity = ToDoEntity()
        }

        toDoEntity!!.title = binding.edtTitle.text.toString()
        toDoEntity!!.description = binding.edtDescription.text.toString()
        toDoEntity!!.types = type
        toDoEntity!!.dateTime = calendar.time

        Log.e("Type", "" + type)

        toDoViewModel.addTodo(toDoEntity!!)
        setAlarm()
    }

    private fun setAlarm() {
        val builder = NotificationCompat.Builder(this, default_notification_channel_id)
        builder.setContentTitle(toDoEntity!!.title)
        builder.setContentText(toDoEntity!!.description)
        builder.setSmallIcon(R.drawable.ic_alarm)
        builder.setChannelId(NOTIFICATION_CHANNEL_ID)

        val notificationIntent = Intent(this, AlarmReceiver::class.java)
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, 1)
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, builder.build())
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    intervalMilliseconds,
                    pendingIntent
                )
            }
            else -> {
                alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    intervalMilliseconds,
                    pendingIntent
                )
            }
        }

        val alarmIntent = Intent("android.intent.action.ALARM_CHANGED")
        alarmIntent.putExtra("alarmSet", true)
        sendBroadcast(alarmIntent)

        finish()
    }

    private fun choseDate() {
        DatePickerDialog(
            this@AddTodoActivity,
            this@AddTodoActivity,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        TimePickerDialog(
            this@AddTodoActivity,
            this@AddTodoActivity,
            calendar.get(Calendar.HOUR),
            calendar.get(Calendar.MINUTE),
            false
        ).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        calendar.set(Calendar.HOUR, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        updateDateInView()
    }

    private fun updateDateInView() {
        binding.tvSelectDateTime.text = simpleDateFormat.format(calendar.time)
    }
}
