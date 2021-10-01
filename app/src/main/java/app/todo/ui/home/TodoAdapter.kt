package app.todo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.todo.data.entity.ToDoEntity
import app.todo.databinding.ItemTodoBinding
import app.todo.util.TodoClickListener

class TodoAdapter(
    private val todoList: ArrayList<ToDoEntity>,
    private val todoClickListener: TodoClickListener
) : RecyclerView.Adapter<TodoAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            ItemTodoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = todoList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(todoList[position], todoClickListener)
    }

    class DataViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoEntity: ToDoEntity, todoClickListener: TodoClickListener) {
            binding.apply {
                tvTitle.text = toDoEntity.title
                tvDescription.text = toDoEntity.description
                ivDelete.setOnClickListener {
                    todoClickListener.onDeleteClicked(toDoEntity)
                }

                with(binding.root) {
                    setOnClickListener {
                        todoClickListener.onToDoClicked(toDoEntity)
                    }
                }
            }
        }
    }

    fun refreshTodoList(todoList: List<ToDoEntity>) {
        this.todoList.apply {
            clear()
            addAll(todoList)
        }
    }
}