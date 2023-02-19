package com.nntsl.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nntsl.domain.usecase.GetUsersUseCase
import com.nntsl.feature.model.UserListItem
import com.nntsl.feature.model.mapToUserScreenItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UserListUiState> = MutableStateFlow(UserListUiState.Loading)
    val uiState: StateFlow<UserListUiState> = _uiState

    init {
        viewModelScope.launch {
            getUsersUseCase()
                .collect {
                    _uiState.value = UserListUiState.Success(it.mapToUserScreenItems())
                }
        }
    }
}

sealed class UserListUiState {
    data class Success(val users: List<UserListItem>) : UserListUiState()
    data class Error(val exception: Throwable) : UserListUiState()
    object Loading : UserListUiState()
}
