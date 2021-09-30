package app.todo.ui.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.lifecycle.ViewModelProviders
import app.todo.R
import app.todo.data.entity.ToDoEntity
import app.todo.data.viewmodel.ToDoViewModel
import app.todo.databinding.ActivityAddTodoBinding
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
    private var cal = Calendar.getInstance()
    private var type = 0
    private lateinit var simpleDateFormat: SimpleDateFormat

    private val toDoViewModel: ToDoViewModel by lazy {
        ViewModelProviders.of(this).get(ToDoViewModel::class.java)
    }

    override fun setContent() {
        val myFormat = "dd/MM/yyyy HH:mm" // mention the format you need
        simpleDateFormat = SimpleDateFormat(myFormat, Locale.US)

        if (intent.hasExtra(Constants.EXTRA_TODO)) {
            toDoEntity = intent.getSerializableExtra(Constants.EXTRA_TODO) as ToDoEntity
            binding.btnAddUpdate.text = getString(R.string.update)

            binding.edtTitle.setText(toDoEntity!!.title)
            binding.edtDescription.setText(toDoEntity!!.description)
            binding.tvSelectDateTime.text = simpleDateFormat.format(toDoEntity!!.dateTime!!)

            if (toDoEntity!!.types == 0) {
                binding.rbDaily.isChecked = true
                binding.rbWeekly.isChecked = false
            } else if (type == 1) {
                binding.rbDaily.isChecked = false
                binding.rbWeekly.isChecked = true
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbDaily -> {
                    type = 0
                }
                R.id.rbWeekly -> {
                    type = 1
                }
            }
        }

        binding.tvSelectDateTime.setOnClickListener {
            choseDate()
        }

        binding.btnAddUpdate.setOnClickListener {
            if (toDoEntity == null) {
                toDoEntity = ToDoEntity()
            }

            toDoEntity!!.title = binding.edtTitle.text.toString()
            toDoEntity!!.description = binding.edtDescription.text.toString()
            toDoEntity!!.types = type
            toDoEntity!!.dateTime = cal.time

            Log.e("Type", "" + type)

            toDoViewModel.addTodo(toDoEntity!!)
            finish()
        }
    }

    private fun choseDate() {
        DatePickerDialog(
            this@AddTodoActivity,
            this@AddTodoActivity,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        cal.set(Calendar.YEAR, year)
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        TimePickerDialog(
            this@AddTodoActivity,
            this@AddTodoActivity,
            cal.get(Calendar.HOUR),
            cal.get(Calendar.MINUTE),
            false
        ).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        cal.set(Calendar.HOUR, hourOfDay)
        cal.set(Calendar.MINUTE, minute)
        updateDateInView()
    }

    private fun updateDateInView() {
        binding.tvSelectDateTime.text = simpleDateFormat.format(cal.time)
    }
}
