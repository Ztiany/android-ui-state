package com.android.base.foundation.state

import android.os.Looper
import androidx.lifecycle.MutableLiveData

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setLoading(step: L? = null) {
    value = State.loading(step)
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setError(error: Throwable, reason: E? = null) {
    value = State.error(error, reason)
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setLoadingRetained(step: L? = null) {
    value = value?.toLoadingRetained(step) ?: State.loading(step)
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setErrorRetained(error: Throwable, reason: E? = null) {
    value = value?.toErrorRetained(error, reason) ?: State.error(error, reason)
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setData(data: D?) {
    value = if (data == null) {
        State.noData()
    } else {
        State.success(data)
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setSuccess() {
    value = State.noData()
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.postLoading(step: L? = null) {
    postValue(State.loading(step))
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.postError(error: Throwable, reason: E? = null) {
    postValue(State.error(error, reason))
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.postLoadingRetained(step: L? = null) {
    postValue(value?.toLoadingRetained(step) ?: State.loading(step))
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.postErrorRetained(error: Throwable, reason: E? = null) {
    postValue(value?.toErrorRetained(error, reason) ?: State.error(error, reason))
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.postData(data: D?) {
    postValue(
        if (data == null) {
            State.noData()
        } else {
            State.success(data)
        }
    )
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.postSuccess() {
    postValue(State.noData())
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setLoadingSafely(step: L? = null) {
    if (isMainThread()) {
        value = State.loading(step)
    } else {
        postValue(State.loading(step))
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setErrorSafely(error: Throwable, reason: E? = null) {
    if (isMainThread()) {
        value = State.error(error, reason)
    } else {
        postValue(State.error(error, reason))
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setLoadingRetainedSafely(step: L? = null) {
    if (isMainThread()) {
        value = value?.toLoadingRetained(step) ?: State.loading(step)
    } else {
        postValue(value?.toLoadingRetained(step) ?: State.loading(step))
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setErrorRetainedSafely(error: Throwable, reason: E? = null) {
    if (isMainThread()) {
        value = value?.toErrorRetained(error, reason) ?: State.error(error, reason)
    } else {
        postValue(value?.toErrorRetained(error, reason) ?: State.error(error, reason))
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setDataSafely(data: D?) {
    val state: State<L, D, E> = if (data == null) {
        State.noData()
    } else {
        State.success(data)
    }

    if (isMainThread()) {
        value = state
    } else {
        postValue(state)
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setSuccessSafely() {
    val state: State<L, D, E> = State.noData()
    if (isMainThread()) {
        value = state
    } else {
        postValue(state)
    }
}

private fun isMainThread(): Boolean {
    return Looper.myLooper() == Looper.getMainLooper()
}