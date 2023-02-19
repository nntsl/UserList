package com.nntsl.data.common.dispatchers

@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val usersDispatcher: UsersDispatchers)

enum class UsersDispatchers {
    IO
}
