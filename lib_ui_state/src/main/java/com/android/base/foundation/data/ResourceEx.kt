package com.android.base.foundation.data

/** When in loading. */
inline fun <L, D, E> Resource<L, D, E>.onLoading(onLoading: () -> Unit): Resource<L, D, E> {
    if (this is Loading) {
        onLoading()
    }
    return this
}

/** When error occurred. */
inline fun <L, D, E> Resource<L, D, E>.onError(onError: (error: Throwable) -> Unit): Resource<L, D, E> {
    if (this is Error) {
        onError(error)
    }
    return this
}

/** When succeeded. */
inline fun <L, D, E> Resource<L, D, E>.onSuccess(onSuccess: (data: D?) -> Unit): Resource<L, D, E> {
    if (this is NoData) {
        onSuccess(null)
    } else if (this is Data<D>) {
        onSuccess(value)
    }
    return this
}

/** When succeeded with data. */
inline fun <L, D, E> Resource<L, D, E>.onData(onData: (data: D) -> Unit): Resource<L, D, E> {
    if (this is Data<D>) {
        onData(value)
    }
    return this
}

/** When succeeded without data. */
inline fun <L, D, E> Resource<L, D, E>.onNoData(onNoData: () -> Unit): Resource<L, D, E> {
    if (this is Data<D>) {
        onNoData()
    }
    return this
}


typealias ResourceD<D> = Resource<Unit, D, Unit>
typealias ResourceDE<D, E> = Resource<Unit, D, E>
typealias ResourceLD<L, D> = Resource<L, D, Unit>
