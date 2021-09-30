package app.todo.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.todo.databinding.ItemLayoutBinding
import app.todo.model.ToDo

class MainAdapter constructor(
    private val users: ArrayList<ToDo>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    class DataViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: ToDo) {
            binding.apply {
//                textViewUserName.text = user.name
//                textViewUserEmail.text = user.email
//                Glide.with(imageViewAvatar.context)
//                    .load(user.avatar)
//                    .into(imageViewAvatar)
            }
        }
    }

    fun addUsers(users: List<ToDo>) {
        this.users.apply {
            clear()
            addAll(users)
        }
    }
}