package app.todo.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import app.todo.data.dao.ToDoDao
import app.todo.data.entity.ToDoEntity

class ToDoRepository(private val toDoDao: ToDoDao) {

    val fetchTodoList: LiveData<List<ToDoEntity>> = toDoDao.fetchToDoList()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addTodo(toDoEntity: ToDoEntity) {
        toDoDao.addTodo(toDoEntity)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateTodo(toDoEntity: ToDoEntity) {
        toDoDao.updateTodo(toDoEntity)
    }

////    @WorkerThread
////    fun fetchTotalGroupExpense(groupId: String): LiveData<BigDecimal> {
////        return toDoDao.fetchTotalGroupExpense(groupId)
////    }
//
//    @WorkerThread
//    fun fetchGroupMember(groupId: String): LiveData<List<ToDoEntity>> {
//        return toDoDao.fetchGroupExpanse(groupId)
//    }
}
