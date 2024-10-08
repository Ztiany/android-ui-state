package me.ztiany.ui.state.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.base.foundation.state.State
import com.android.base.foundation.state.StateD
import com.android.base.foundation.state.setData
import com.android.base.foundation.state.setError
import com.android.base.foundation.state.setLoading
import com.android.base.foundation.state.setSuccess
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class Game(val id: Int)

class TestViewModel : ViewModel() {

    private val _gameDetailState = MutableLiveData<State<Step, Game, Reason>>()
    val gameDetailState: LiveData<State<Step, Game, Reason>> = _gameDetailState

    private val _gameDetailState1 = MutableLiveData<StateD<Game>>()
    val gameDetailState1: LiveData<StateD<Game>> = _gameDetailState1

    private val _gameDetailState2 = MutableStateFlow<State<Step, Game, Reason>>(State.loading())
    val gameDetailState2: StateFlow<State<Step, Game, Reason>> = _gameDetailState2

    private val _gameDetailState3 = MutableSharedFlow<State<Step, Game, Reason>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val gameDetailState3: StateFlow<State<Step, Game, Reason>> = _gameDetailState2

    fun test() {
        _gameDetailState.value = State.loading()
        _gameDetailState.value = State.noData()
        _gameDetailState.value = State.success(Game(1))
        _gameDetailState.value = State.error(NullPointerException())

        _gameDetailState1.value = State.loading()
        _gameDetailState1.value = State.noData()
        _gameDetailState1.value = State.success(Game(1))
        _gameDetailState1.value = State.error(NullPointerException())

        _gameDetailState.setLoading()
        _gameDetailState.setError(NullPointerException(), Reason("Null"))
        _gameDetailState.setData(Game(1))
        _gameDetailState.setSuccess()

        _gameDetailState1.setLoading()
        _gameDetailState1.setError(NullPointerException())
        _gameDetailState1.setData(Game(1))
        _gameDetailState1.setSuccess()
    }

}

sealed class Step {
    data object Step1 : Step()
    data object Step2 : Step()
    data object Step3 : Step()
}

data class Reason(val message: String)