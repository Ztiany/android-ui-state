package com.android.base.foundation.data

/** When in loading. */
inline fun <D, E> Resource<D, E>.onLoading(onLoading: () -> Unit): Resource<D, E> {
    if (this is Loading) {
        onLoading()
    }
    return this
}

/** When error occurred. */
inline fun <D, E> Resource<D, E>.onError(onError: (error: Throwable) -> Unit): Resource<D, E> {
    if (this is Error) {
        onError(error)
    }
    return this
}

/** When succeeded. */
inline fun <D, E> Resource<D, E>.onSuccess(onSuccess: (data: D?) -> Unit): Resource<D, E> {
    if (this is NoData) {
        onSuccess(null)
    } else if (this is Data<D>) {
        onSuccess(value)
    }
    return this
}

/** When succeeded with data. */
inline fun <D, E> Resource<D, E>.onData(onData: (data: D) -> Unit): Resource<D, E> {
    if (this is Data<D>) {
        onData(value)
    }
    return this
}

/** When succeeded without data. */
inline fun <D, E> Resource<D, E>.onNoData(onNoData: () -> Unit): Resource<D, E> {
    if (this is Data<D>) {
        onNoData()
    }
    return this
}

/** A resource has no reason when an error occurred. */
typealias ResourceU<D> = Resource<D, Unit>

/** A resource has no reason when an error occurred. */
typealias ResourceNR<D> = Resource<D, Unit>
