package com.android.base.foundation.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.updateToLoading(step: L? = null) {
    update { State.loading(step) }
}

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.updateToError(error: Throwable) {
    update { State.error(error) }
}

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.updateToData(data: D?) {
    val state: State<L, D, E> = if (data == null) {
        State.noData()
    } else {
        State.success(data)
    }
    update { state }
}

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.updateToSuccess() {
    val state: State<L, D, E> = State.noData()
    update { state }
}

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.setLoading(step: L? = null) {
    value = State.loading(step)
}

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.setError(error: Throwable) {
    value = State.error(error)
}

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.setData(data: D?) {
    val state: State<L, D, E> = if (data == null) {
        State.noData()
    } else {
        State.success(data)
    }
    value = state
}

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.setSuccess() {
    val state: State<L, D, E> = State.noData()
    value = state
}