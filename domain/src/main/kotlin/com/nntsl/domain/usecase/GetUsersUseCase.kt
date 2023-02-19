package com.nntsl.domain.usecase

import com.nntsl.domain.model.UserInfo
import com.nntsl.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val usersRepository: UsersRepository
) {
    suspend operator fun invoke(): Flow<List<UserInfo>> {
        return usersRepository.getUsers()
    }
}
