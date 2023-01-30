package com.android.base.foundation.data

/**
 * @author Ztiany
 */
sealed class Resource<out D, out E> {

    companion object {

        fun <D, E> noData(): Resource<D, E> {
            return NoData()
        }

        fun <D, E> success(data: D): Resource<D, E> {
            return Data(data)
        }

        fun <D, E> error(error: Throwable, reason: E? = null): Resource<D, E> {
            return Error(error, reason)
        }

        fun <D, E> loading(): Resource<D, E> {
            return Loading
        }

    }

}

object Loading : Resource<Nothing, Nothing>()

class Error<E>(val error: Throwable, val reason: E?) : Resource<Nothing, E>() {

    private var handled = false
    val isHandled: Boolean
        get() = handled

    fun markAsHandled() {
        handled = true
    }

    override fun toString(): String {
        return "Error(error=$error, reason=$reason, handled=$isHandled)"
    }

}

sealed class Success<D> : Resource<D, Nothing>() {

    private var handled = false
    val isHandled: Boolean
        get() = handled

    fun markAsHandled() {
        handled = true
    }

}

class Data<D>(val value: D) : Success<D>() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Data<*>
        if (value != other.value) return false
        return true
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Data(value=$value, handled=$isHandled)"
    }

}

class NoData : Success<Nothing>() {

    override fun toString(): String {
        return "NoData(hash=${hashCode()}, handled=$isHandled)"
    }

}