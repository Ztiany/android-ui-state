package me.ztiany.ui.state.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.base.foundation.data.Resource
import com.android.base.foundation.data.ResourceNR
import com.android.base.foundation.data.ResourceU

data class Game(val id: Int)

class TestViewModel : ViewModel() {

    private val _gameDetailState = MutableLiveData<Resource<Game, Reason>>()
    val gameDetailState: LiveData<Resource<Game, Reason>> = _gameDetailState

    private val _gameDetailState1 = MutableLiveData<ResourceU<Game>>()
    val gameDetailState1: LiveData<ResourceU<Game>> = _gameDetailState1

    fun test() {
        _gameDetailState.value = Resource.loading()
        _gameDetailState.value = Resource.noData()
        _gameDetailState.value = Resource.success(Game(1))
        _gameDetailState.value = Resource.error(NullPointerException())

        _gameDetailState1.value = Resource.loading()
        _gameDetailState1.value = Resource.noData()
        _gameDetailState1.value = Resource.success(Game(1))
        _gameDetailState1.value = Resource.error(NullPointerException())

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

data class Reason(val message: String)