package com.nntsl.data.network.di

import com.nntsl.data.network.UsersNetworkDataSource
import com.nntsl.data.network.retrofit.RetrofitUsersNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UsersNetworkModule {

//    @Binds
//    fun bindsUsersNetwork(
//        network: RetrofitUsersNetwork
//    ): UsersNetworkDataSource
}
