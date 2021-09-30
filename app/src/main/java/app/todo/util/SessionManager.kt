package app.todo.util

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    private val preferencesName = "todo"
    private val LOGIN = "login"
    private val TOKEN = "token"
    private val user = "user"

    init {
        sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.apply()
    }

//    var storedUser: User?
//        get() {
//            val json = sharedPreferences.getString(user, "")
//            val type = object : TypeToken<User>() {
//            }.type
//            return Gson().fromJson(json, type)
//        }
//        set(storedAppData) {
//            editor.putString(user, Gson().toJson(storedAppData))
//            editor.commit()
//        }

    var token: String?
        get() = sharedPreferences.getString(TOKEN, "")
        set(token) {
            editor.putString(TOKEN, token)
            editor.commit()
        }

    var isLogin: Boolean
        get() = sharedPreferences.getBoolean(LOGIN, false)
        set(isLogin) {
            editor.putBoolean(LOGIN, isLogin)
            editor.commit()
        }
}
