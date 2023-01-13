package com.android.base.foundation.data

/**when in loading*/
inline fun <D, E> Resource<D, E>.onLoading(onLoading: () -> Unit): Resource<D, E> {
    if (this is Loading) {
        onLoading()
    }
    return this
}

/**when error occurred*/
inline fun <D, E> Resource<D, E>.onError(onError: (error: Throwable) -> Unit): Resource<D, E> {
    if (this is Error) {
        onError(error)
    }
    return this
}

/**when succeeded*/
inline fun <D, E> Resource<D, E>.onSuccess(onSuccess: (data: D?) -> Unit): Resource<D, E> {
    if (this is NoData) {
        onSuccess(null)
    } else if (this is Data<D>) {
        onSuccess(value)
    }
    return this
}

/**when succeeded and has data*/
inline fun <D, E> Resource<D, E>.onData(onData: (data: D) -> Unit): Resource<D, E> {
    if (this is Data<D>) {
        onData(value)
    }
    return this
}

/**when succeeded*/
inline fun <D, E> Resource<D, E>.onNoData(onNoData: () -> Unit): Resource<D, E> {
    if (this is Data<D>) {
        onNoData()
    }
    return this
}

typealias ResourceNR<D> = Resource<D, Nothing>