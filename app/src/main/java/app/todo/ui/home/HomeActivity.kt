package app.todo.ui.home

import androidx.lifecycle.ViewModelProvider
import app.todo.R
import app.todo.databinding.ActivityHomeBinding
import app.todo.network.RetrofitBuilder
import app.todo.ui.base.BaseActivity
import app.todo.ui.base.delegate.viewBinding
import app.todo.ui.login.viewmodel.LoginViewModel

class HomeActivity : BaseActivity(R.layout.activity_login) {

    private val binding by viewBinding(ActivityHomeBinding::inflate)
    private lateinit var viewModel: LoginViewModel

    override fun setContent() {
        viewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(RetrofitBuilder.apiInterface)
        ).get(LoginViewModel::class.java)
    }

//    private fun setupUI() {
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = MainAdapter(arrayListOf())
//        binding.recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                binding.recyclerView.context,
//                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
//            )
//        )
//        binding.recyclerView.adapter = adapter
//    }
//
//    private fun setupObservers() {
//        viewModel.login("eve.holt@reqres.in", "cityslicka").observe(this, {
//            it?.let { resource ->
//                when (resource.status) {
//                    Status.SUCCESS -> {
//                        binding.recyclerView.visibility = View.VISIBLE
//                        binding.progressBar.visibility = View.GONE
//                        resource.data?.let { users ->
////                            retrieveList(users)
//                        }
//                    }
//                    Status.ERROR -> {
//                        binding.recyclerView.visibility = View.VISIBLE
//                        binding.progressBar.visibility = View.GONE
//                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
//                    }
//                    Status.LOADING -> {
//                        binding.progressBar.visibility = View.VISIBLE
//                        binding.recyclerView.visibility = View.GONE
//                    }
//                }
//            }
//        })
//    }
//
//    private fun retrieveList(token: String) {
//
//    }
}
