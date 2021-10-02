package app.todo.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import app.todo.ui.login.LoginActivity

class SessionManager(context: Context) {

    private val sharedPreferences: SharedPreferences
    private var editor: SharedPreferences.Editor

    private val preferencesName = "todo"
    private val LOGIN = "login"
    private val TOKEN = "token"

    init {
        sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.apply()
    }

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

    fun logout(activity: Activity) {
        editor.clear()
        editor.apply()

        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity.finishAffinity()
    }
}
