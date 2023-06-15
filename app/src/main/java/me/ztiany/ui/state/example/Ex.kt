package me.ztiany.ui.state.example


import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.android.base.foundation.data.State

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setLoading() {
    if (isMainThread()) {
        value = State.loading()
    } else {
        postValue(State.loading())
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setError(error: Throwable, reason: E? = null) {
    if (isMainThread()) {
        value = State.error(error, reason)
    } else {
        postValue(State.error(error, reason))
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setData(data: D?) {
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

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<State<L, D, E>>.setSuccess() {
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