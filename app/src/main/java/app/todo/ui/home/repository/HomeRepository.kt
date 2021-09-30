package app.todo.ui.home.repository

import app.todo.network.APIInterface

class HomeRepository constructor(private val apiInterface: APIInterface) {
    suspend fun login(email: String, password: String) = apiInterface.login(email, password)
}