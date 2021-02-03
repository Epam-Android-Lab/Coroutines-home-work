package com.school.coroutines

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityViewModel : ViewModel() {
    private val _state = MutableLiveData<State>(State.Loading)
    val state: LiveData<State>
        get() = _state

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resp = Repository.getPosts()
                if (resp.isSuccessful) {
                    resp.body()?.let {
                        _state.postValue(State.Loaded(it))
                    }
                }
            }
            catch (e : Exception) {
                _state.postValue(State.Loaded(emptyList()))
            }
        }
    }
}
