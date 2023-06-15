package com.android.base.foundation.data

/** When in loading. */
inline fun <L, D, E> State<L, D, E>.onLoading(onLoading: () -> Unit): State<L, D, E> {
    if (this is Loading) {
        onLoading()
    }
    return this
}

/** When in loading. */
inline fun <L, D, E> State<L, D, E>.onLoadingWithStep(onLoading: (L?) -> Unit): State<L, D, E> {
    if (this is Loading) {
        onLoading(step)
    }
    return this
}

/** When error occurred. */
inline fun <L, D, E> State<L, D, E>.onError(onError: (error: Throwable) -> Unit): State<L, D, E> {
    if (this is Error) {
        onError(error)
    }
    return this
}

/** When error occurred. */
inline fun <L, D, E> State<L, D, E>.onErrorWithReason(onError: (error: Throwable, reason: E?) -> Unit): State<L, D, E> {
    if (this is Error) {
        onError(error, reason)
    }
    return this
}

/** When succeeded. */
inline fun <L, D, E> State<L, D, E>.onSuccess(onSuccess: (data: D?) -> Unit): State<L, D, E> {
    if (this is NoData) {
        onSuccess(null)
    } else if (this is Data<D>) {
        onSuccess(value)
    }
    return this
}

/** When succeeded with data. */
inline fun <L, D, E> State<L, D, E>.onData(onData: (data: D) -> Unit): State<L, D, E> {
    if (this is Data<D>) {
        onData(value)
    }
    return this
}

/** When succeeded without data. */
inline fun <L, D, E> State<L, D, E>.onNoData(onNoData: () -> Unit): State<L, D, E> {
    if (this is Data<D>) {
        onNoData()
    }
    return this
}

typealias StateN = State<Unit, Unit, Unit>
typealias StateD<D> = State<Unit, D, Unit>
typealias StateDE<D, E> = State<Unit, D, E>
typealias StateLD<L, D> = State<L, D, Unit>