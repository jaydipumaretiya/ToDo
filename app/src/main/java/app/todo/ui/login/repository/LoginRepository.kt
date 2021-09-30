package app.todo.ui.login.repository

import app.todo.network.APIInterface

class LoginRepository constructor(private val apiInterface: APIInterface) {
    suspend fun login(email: String, password: String) = apiInterface.login(email, password)
}