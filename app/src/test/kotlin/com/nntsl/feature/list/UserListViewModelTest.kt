package com.nntsl.feature.list

import com.nntsl.data.testing.repository.TestUsersRepository
import com.nntsl.data.testing.util.MainDispatcherRule
import com.nntsl.domain.usecase.GetUsersUseCase
import com.nntsl.feature.model.mapToUserScreenItems
import com.nntsl.testUserList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.flow.collect
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UserListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val usersRepository = TestUsersRepository()

    private val getUsersUseCase = GetUsersUseCase(
        usersRepository = usersRepository
    )

    private lateinit var viewModel: UserListViewModel

    @Before
    fun setUp() {
        viewModel = UserListViewModel(
            getUsersUseCase = getUsersUseCase,
        )
    }

    @Test
    fun uiState_whenInitialized_thenShowLoading() = runTest {
        assertEquals(UserListUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun uiState_whenUsersLoaded_thenShowSuccess() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        usersRepository.sendUsers(testUserList)

        assertEquals(
            UserListUiState.Success(
                testUserList.mapToUserScreenItems()
            ),
            viewModel.uiState.value
        )

        collectJob.cancel()
    }
}