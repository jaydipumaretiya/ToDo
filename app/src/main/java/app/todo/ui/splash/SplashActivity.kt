package app.todo.ui.splash

import android.content.Intent
import app.todo.R
import app.todo.databinding.ActivitySplashBinding
import app.todo.ui.base.BaseActivity
import app.todo.ui.base.delegate.viewBinding
import app.todo.ui.home.HomeActivity
import app.todo.ui.login.LoginActivity

class SplashActivity : BaseActivity(R.layout.activity_splash) {

    private val binding by viewBinding(ActivitySplashBinding::inflate)

    override fun setContent() {
        if (sessionManager!!.isLogin) {
            startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
    }
}