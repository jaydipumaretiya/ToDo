package app.todo.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import app.todo.ui.login.repository.LoginRepository
import app.todo.util.Resource
import kotlinx.coroutines.Dispatchers
import java.io.IOException

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {

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