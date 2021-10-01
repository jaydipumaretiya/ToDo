package app.todo.util

import app.todo.data.entity.ToDoEntity

interface TodoClickListener {
    fun onToDoClicked(toDoEntity: ToDoEntity)
    fun onDeleteClicked(toDoEntity: ToDoEntity)
}