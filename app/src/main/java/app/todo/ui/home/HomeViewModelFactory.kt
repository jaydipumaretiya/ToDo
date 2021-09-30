package app.todo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.todo.network.APIInterface
import app.todo.ui.home.repository.HomeRepository
import app.todo.ui.home.viewmodel.HomeViewModel

class HomeViewModelFactory(private val apiInterface: APIInterface) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(HomeRepository(apiInterface)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}