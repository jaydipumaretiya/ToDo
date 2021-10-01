package app.todo.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import app.todo.data.entity.ToDoEntity

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addTodo(toDoEntity: ToDoEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTodo(toDoEntity: ToDoEntity)

    @Delete
    fun deleteTodo(toDoEntity: ToDoEntity)

    @Query("SELECT * FROM todo_master")
    fun fetchToDoList(): LiveData<List<ToDoEntity>>

    @Query("SELECT * FROM todo_master WHERE id=:id")
    fun fetchToDoById(id: Int): LiveData<List<ToDoEntity>>
}
