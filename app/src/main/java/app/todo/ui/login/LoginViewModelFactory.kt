package app.todo.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.todo.network.APIInterface
import app.todo.ui.login.repository.LoginRepository
import app.todo.ui.login.viewmodel.LoginViewModel

class LoginViewModelFactory(private val apiInterface: APIInterface) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(LoginRepository(apiInterface)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}