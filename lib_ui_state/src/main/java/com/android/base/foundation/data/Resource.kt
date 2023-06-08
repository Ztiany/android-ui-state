package com.android.base.foundation.data

/**
 * A resource represents loading, data and error state.
 *
 * @author Ztiany
 * @param L loading
 * @param D data
 * @param E error
 */
sealed class Resource<out L, out D, out E> {

    companion object {

        fun <L, D, E> noData(): Resource<L, D, E> {
            return NoData()
        }

        fun <L, D, E> success(data: D): Resource<L, D, E> {
            return Data(data)
        }

        fun <L, D, E> error(error: Throwable, reason: E? = null): Resource<L, D, E> {
            return Error(error, reason)
        }

        fun <L, D, E> loading(step: L? = null): Resource<L, D, E> {
            return Loading(step)
        }

    }

}

object InitialState : Resource<Nothing, Nothing, Nothing>()

class Loading<L>(val step: L?) : Resource<L, Nothing, Nothing>() {

    override fun toString(): String {
        return "Loading(hash=${hashCode()}, step=$step)"
    }

}

class Error<E>(val error: Throwable, val reason: E?) : Resource<Nothing, Nothing, E>() {

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

sealed class Success<D> : Resource<Nothing, D, Nothing>() {

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