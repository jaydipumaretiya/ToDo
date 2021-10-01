package app.todo.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.todo.data.db.AppDatabase
import app.todo.data.entity.ToDoEntity
import app.todo.data.repository.ToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private var parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private val repository: ToDoRepository

    val _id: MutableLiveData<String> = MutableLiveData()

    val fetchTodoList: LiveData<List<ToDoEntity>>

    init {
        val toDoDao = AppDatabase.getDatabase(application, scope).todoDao()
        repository = ToDoRepository(toDoDao)
        fetchTodoList = repository.fetchTodoList
    }

    fun addTodo(toDoEntity: ToDoEntity) = scope.launch(Dispatchers.IO) {
        repository.addTodo(toDoEntity)
    }

    fun updateTodo(toDoEntity: ToDoEntity) = scope.launch(Dispatchers.IO) {
        repository.updateTodo(toDoEntity)
    }

    fun deleteTodo(toDoEntity: ToDoEntity) = scope.launch(Dispatchers.IO) {
        repository.deleteTodo(toDoEntity)
    }

    fun setGroupId(id: String) {
        _id.value = id
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
