package me.ztiany.ui.state.example


import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.android.base.foundation.data.Resource

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<Resource<L, D, E>>.setLoading() {
    if (isMainThread()) {
        value = Resource.loading()
    } else {
        postValue(Resource.loading())
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<Resource<L, D, E>>.setError(error: Throwable, reason: E? = null) {
    if (isMainThread()) {
        value = Resource.error(error, reason)
    } else {
        postValue(Resource.error(error, reason))
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<Resource<L, D, E>>.setData(data: D?) {
    val resource: Resource<L, D, E> = if (data == null) {
        Resource.noData()
    } else {
        Resource.success(data)
    }

    if (isMainThread()) {
        value = resource
    } else {
        postValue(resource)
    }
}

fun <L : Any?, D : Any?, E : Any?> MutableLiveData<Resource<L, D, E>>.setSuccess() {
    val resource: Resource<L, D, E> = Resource.noData()
    if (isMainThread()) {
        value = resource
    } else {
        postValue(resource)
    }
}

private fun isMainThread(): Boolean {
    return Looper.myLooper() == Looper.getMainLooper()
}