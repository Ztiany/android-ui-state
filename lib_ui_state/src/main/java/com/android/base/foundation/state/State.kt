package com.android.base.foundation.state

import java.util.concurrent.atomic.AtomicBoolean

/**
 * A resource represents loading, data and error state.
 *
 * @param L Loading Step.
 * @param D Data
 * @param E Error Reason.
 */
sealed class State<out L, out D, out E> {

    companion object {

        fun <L, D, E> noData(): State<L, D, E> {
            return NoData()
        }

        fun <L, D, E> success(data: D): State<L, D, E> {
            return Data(data)
        }

        fun <L, D, E> error(error: Throwable, reason: E? = null): State<L, D, E> {
            return Error(error, reason)
        }

        fun <L, D, E> loading(step: L? = null): State<L, D, E> {
            return Loading(step)
        }

    }

}

object Idle : State<Nothing, Nothing, Nothing>()

class Loading<L>(val step: L?) : State<L, Nothing, Nothing>() {

    override fun toString(): String {
        return "Loading(hash=${hashCode()}, step=$step)"
    }

}

class Error<E>(val error: Throwable, val reason: E?) : State<Nothing, Nothing, E>() {

    private var handled = AtomicBoolean(false)

    val isHandled: Boolean
        get() = handled.get()

    fun markAsHandled(): Boolean {
        return handled.compareAndSet(false, true)
    }

    override fun toString(): String {
        return "Error(error=$error, reason=$reason, handled=$isHandled)"
    }

}

sealed class Success<D> : State<Nothing, D, Nothing>() {

    private var handled = AtomicBoolean(false)

    val isHandled: Boolean
        get() = handled.get()

    fun markAsHandled(): Boolean {
        return handled.compareAndSet(false, true)
    }

}

class Data<D>(val value: D) : Success<D>() {

    override fun toString(): String {
        return "Data(value=$value, handled=$isHandled)"
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Data<*>
        return value == other.value
    }

}

class NoData : Success<Nothing>() {

    override fun toString(): String {
        return "NoData(hash=${hashCode()}, handled=$isHandled)"
    }

}