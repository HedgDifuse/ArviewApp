package com.hedgdifuse.arviewapp.router

/**
 * [RouterState] - state for viewmodels, like MVI
 */
sealed class RouterState<T>(val refresh: Boolean) {

    class RouterStateSuccess<T>(val data: T, refresh: Boolean) : RouterState<T>(refresh)
    class RouterStateLoading<T>(refresh: Boolean) : RouterState<T>(refresh)
    class RouterStateErrored<T>(val error: Exception, refresh: Boolean) : RouterState<T>(refresh)

}