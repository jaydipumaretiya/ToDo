package app.todo.ui.home

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import app.todo.R
import app.todo.data.entity.ToDoEntity
import app.todo.data.viewmodel.ToDoViewModel
import app.todo.databinding.ActivityHomeBinding
import app.todo.ui.base.BaseActivity
import app.todo.ui.base.delegate.viewBinding
import app.todo.ui.home.todo.AddTodoActivity
import app.todo.util.Constants
import app.todo.util.TodoClickListener

class HomeActivity : BaseActivity(R.layout.activity_home), TodoClickListener {

    private val binding by viewBinding(ActivityHomeBinding::inflate)
    private val toDoViewModel: ToDoViewModel by lazy {
        ViewModelProviders.of(this).get(ToDoViewModel::class.java)
    }

    override fun setContent() {
        setLinearRecyclerView(
            binding.rvRecyclerView,
            RecyclerView.VERTICAL
        )
        val adapter = TodoAdapter(ArrayList(), this)
        toDoViewModel.fetchTodoList.observe(this@HomeActivity, { groupEntity ->
            groupEntity?.let {
                adapter.refreshTodoList(it)
                binding.rvRecyclerView.adapter = adapter
            }
        })
    }

    override fun onToDoClicked(toDoEntity: ToDoEntity) {
        val intent = Intent(this@HomeActivity, AddTodoActivity::class.java)
        intent.putExtra(Constants.EXTRA_TODO, toDoEntity)
        startActivity(intent)
    }

    override fun onDeleteClicked(toDoEntity: ToDoEntity) {
        AlertDialog.Builder(this@HomeActivity)
            .setTitle(getString(R.string.delete_todo_title))
            .setMessage(getString(R.string.delete_todo_message))
            .setPositiveButton(android.R.string.ok) { dialog, which ->
                dialog.cancel()
                toDoViewModel.deleteTodo(toDoEntity)
            }
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuAddNote -> {
                startActivity(
                    Intent(this@HomeActivity, AddTodoActivity::class.java)
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
