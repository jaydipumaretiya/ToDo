package app.todo.ui.login

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import app.todo.R
import app.todo.databinding.ActivityLoginBinding
import app.todo.network.RetrofitBuilder
import app.todo.network.response.RestResponse
import app.todo.ui.base.BaseActivity
import app.todo.ui.base.delegate.viewBinding
import app.todo.ui.home.HomeActivity
import app.todo.ui.login.viewmodel.LoginViewModel
import app.todo.util.AppUtils.isValidEmail
import app.todo.util.Status

class LoginActivity : BaseActivity(R.layout.activity_login) {

    private val binding by viewBinding(ActivityLoginBinding::inflate)
    private lateinit var viewModel: LoginViewModel

    override fun setContent() {
        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(RetrofitBuilder.apiInterface)
        ).get(LoginViewModel::class.java)

        binding.btnSubmit.setOnClickListener {
            validate()
        }
    }

    private fun validate() {
        when {
            !binding.edtEmail.text!!.isValidEmail() -> {
                showToast(getString(R.string.message_valid_email))
                return
            }
            binding.edtPassword.text!!.isEmpty() -> {
                showToast(getString(R.string.message_valid_password))
                return
            }
            (binding.edtPassword.text!!.length < 6) -> {
                showToast(getString(R.string.message_valid_password_length))
                return
            }
            else -> {
                if (isNetworkConnected)
                    login()
            }
        }
    }

    private fun login() {
        val email = binding.edtEmail.text.toString()
        val password = binding.edtPassword.text.toString()

        viewModel.login(email, password).observe(this, {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideLoading()
                        resource.data?.let { restResponse ->
                            retrieveList(restResponse)
                        }
                    }
                    Status.ERROR -> {
                        hideLoading()
                        showToast(it.message!!)
                    }
                    Status.LOADING -> {
                        showLoading()
                    }
                }
            }
        })
    }

    private fun retrieveList(restResponse: RestResponse) {
        sessionManager!!.token = restResponse.token
        sessionManager!!.isLogin = true

        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        finishAffinity()
    }
}
