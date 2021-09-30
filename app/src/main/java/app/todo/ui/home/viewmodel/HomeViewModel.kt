package app.todo.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import app.todo.util.Resource
import app.todo.ui.home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import java.io.IOException

class HomeViewModel(
    private val loginRepository: HomeRepository
) : ViewModel() {

//    val users: MutableLiveData<Resource<User>> = MutableLiveData()

    init {
//        getUsers()
    }

    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = loginRepository.login(email, password)))
        } catch (exception: Exception) {
            when (exception) {
                is IOException -> emit(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "IOException Error Occurred!"
                    )
                )
                else -> emit(
                    Resource.error(
                        data = null,
                        message = exception.message ?: "Conversion Error Occurred!"
                    )
                )
            }
        }
    }
}