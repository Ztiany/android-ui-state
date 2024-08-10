package com.android.base.foundation.state

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

fun <L, D, E> State<L, D, E>.toLoadingRetained(step: L? = null): State<L, D, E> {
    if (this is Data) {
        return Loading(step, value)
    }
    if (this is Error) {
        return Loading(step, data)
    }
    if (this is Loading) {
        return Loading(step, data)
    }
    return Loading(step)
}

fun <L, D, E> State<L, D, E>.toErrorRetained(error: Throwable, reason: E? = null): State<L, D, E> {
    if (this is Data) {
        return Error(error, reason, value)
    }
    if (this is Error) {
        return Error(error, reason, data)
    }
    if (this is Loading) {
        return Error(error, reason, data)
    }
    return Error(error, reason)
}

/** as long as the state holds the data, [onData] will be called. if no data, [onNoData] will be called. */
fun <D> State<*, D, *>.withStateData(onNoData: () -> Unit = {}, onData: (D) -> Unit) {
    when (this) {
        is Data -> onData(value)

        is Error -> data?.let(onData) ?: onNoData()

        is Loading -> data?.let(onData) ?: onNoData()

        Idle -> onNoData()

        is NoData -> onNoData()
    }
}

fun <D> State<*, D, *>.getStateData(): D? {
    return when (this) {
        is Data -> value

        is Error -> data

        is Loading -> data

        Idle -> null

        is NoData -> null
    }
}

fun <D> State<*, D, *>.getStateDataOr(default: D): D {
    return when (this) {
        is Data -> value

        is Error -> data ?: default

        is Loading -> data ?: default

        Idle -> default

        is NoData -> default
    }
}

fun <D> State<*, D, *>.getStateDataOrElse(default: () -> D): D {
    return when (this) {
        is Data -> value

        is Error -> data ?: default()

        is Loading -> data ?: default()

        Idle -> default()

        is NoData -> default()
    }
}

fun <D> State<*, D, *>.getData(): D? {
    return when (this) {
        is Data -> value
        else -> null
    }
}

fun <D> State<*, D, *>.getDataOr(default: D): D {
    return when (this) {
        is Data -> value
        else -> default
    }
}

fun <D> State<*, D, *>.getDataOrElse(default: () -> D): D {
    return when (this) {
        is Data -> value
        else -> default()
    }
}

typealias StateN = State<Unit, Unit, Unit>
typealias StateD<D> = State<Unit, D, Unit>
typealias StateDE<D, E> = State<Unit, D, E>
typealias StateLD<L, D> = State<L, D, Unit>