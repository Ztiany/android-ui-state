package com.android.base.foundation.state

import kotlinx.coroutines.flow.MutableSharedFlow

suspend fun <L : Any?, D : Any?, E : Any?> MutableSharedFlow<State<L, D, E>>.emitLoading(step: L? = null) {
    emit(State.loading(step))
}

suspend fun <L : Any?, D : Any?, E : Any?> MutableSharedFlow<State<L, D, E>>.emitError(error: Throwable) {
    emit(State.error(error))
}

suspend fun <L : Any?, D : Any?, E : Any?> MutableSharedFlow<State<L, D, E>>.emitData(data: D?) {
    val state: State<L, D, E> = if (data == null) {
        State.noData()
    } else {
        State.success(data)
    }
    emit(state)
}

suspend fun <L : Any?, D : Any?, E : Any?> MutableSharedFlow<State<L, D, E>>.emitSuccess() {
    val state: State<L, D, E> = State.noData()
    emit(state)
}

fun <L : Any?, D : Any?, E : Any?> MutableSharedFlow<State<L, D, E>>.tryEmitLoading(step: L? = null): Boolean {
    return tryEmit(State.loading(step))
}

fun <L : Any?, D : Any?, E : Any?> MutableSharedFlow<State<L, D, E>>.tryEmitError(error: Throwable): Boolean {
    return tryEmit(State.error(error))
}

fun <L : Any?, D : Any?, E : Any?> MutableSharedFlow<State<L, D, E>>.tryEmitData(data: D?): Boolean {
    val state: State<L, D, E> = if (data == null) {
        State.noData()
    } else {
        State.success(data)
    }
    return tryEmit(state)
}

fun <L : Any?, D : Any?, E : Any?> MutableSharedFlow<State<L, D, E>>.tryEmitSuccess(): Boolean {
    val state: State<L, D, E> = State.noData()
    return tryEmit(state)
}