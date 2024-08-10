package com.android.base.foundation.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.setLoading(step: L? = null) {
    update {
        State.loading(step)
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.setError(error: Throwable) {
    update {
        State.error(error)
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.setLoadingRetained(step: L? = null) {
    update {
        it.toLoadingRetained(step)
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.setErrorRetained(error: Throwable) {
    update {
        it.toErrorRetained(error)
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.setData(data: D?) {
    update {
        if (data == null) {
            State.noData()
        } else {
            State.success(data)
        }
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableStateFlow<State<L, D, E>>.setSuccess() {
    update {
        State.noData()
    }
}