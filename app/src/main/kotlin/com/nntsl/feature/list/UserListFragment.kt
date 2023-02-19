package com.nntsl.feature.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.nntsl.R
import com.nntsl.databinding.FragmentUserListBinding
import com.nntsl.feature.list.adapter.UserListAdapter
import com.nntsl.feature.model.UserListItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private val binding: FragmentUserListBinding by viewBinding(FragmentUserListBinding::bind)

    private val viewModel: UserListViewModel by viewModels()

    private val usersAdapter = UserListAdapter(::openUserDetails)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is UserListUiState.Success -> {
                            showUsers(uiState.users)
                        }
                        is UserListUiState.Error -> {
                            showError()
                        }
                        UserListUiState.Loading -> {
                            showLoading()
                        }
                    }
                }
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            usersRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = usersAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        context,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
    }

    private fun showLoading() {
        binding.userProgressBar.isVisible = true
    }

    private fun hideLoading() {
        binding.userProgressBar.isVisible = false
    }

    private fun showError() {
        hideLoading()
        binding.usersError.isVisible = true
    }

    private fun hideError() {
        binding.usersError.isVisible = false
    }

    private fun showUsers(users: List<UserListItem>) {
        hideLoading()
        hideError()
        usersAdapter.submitList(users)
    }

    private fun openUserDetails(user: UserListItem) {
        findNavController().navigate(UserListFragmentDirections.openUserDetails(user))
    }
}
