package com.nntsl.feature.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.nntsl.R
import com.nntsl.databinding.RvUserItemBinding
import com.nntsl.feature.model.UserListItem

class UserListAdapter(
    private val openUserDetails: (UserListItem) -> Unit
) : ListAdapter<UserListItem, UserListAdapter.ViewHolder>(UserListAdapterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvUserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: RvUserItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserListItem) {
            with(binding) {
                userItem.setOnClickListener {
                    openUserDetails(user)
                }
                userNameTextView.text = user.name
                user.avatar?.let {
                    userAvatarImageView.load(user.avatar) {
                        transformations(
                            CircleCropTransformation()
                        )
                        build()
                    }
                } ?: userAvatarImageView.setImageDrawable(
                    AppCompatResources.getDrawable(itemView.context, R.drawable.ic_default_avatar)
                )
                userSourceTextView.text = user.source
            }
        }
    }

    class UserListAdapterDiffCallback : DiffUtil.ItemCallback<UserListItem>() {

        override fun areItemsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserListItem, newItem: UserListItem): Boolean =
            oldItem == newItem
    }
}
