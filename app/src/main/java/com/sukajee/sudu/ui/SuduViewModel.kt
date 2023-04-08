package com.sukajee.sudu.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.sudu.data.model.Sudu
import com.sukajee.sudu.data.model.UiState
import com.sukajee.sudu.data.repository.SuduRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "SuduViewModel"

@HiltViewModel
class SuduViewModel @Inject constructor(
    private val repository: SuduRepository
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(UiState())
    val homeUiState
        get() = _homeUiState.asStateFlow()

    init {
        fetchSudus()
    }

    fun insertSudu(sudu: Sudu) = viewModelScope.launch {
        repository.insertSudu(sudu)
    }

    private fun fetchSudus() = viewModelScope.launch {
        repository.getAllSudus().let { sudus ->
            _homeUiState.update { currentState ->
                currentState.copy(
                    sudus = sudus
                )
            }
        }
    }

    fun getSudu(suduId: Int) = viewModelScope.launch {
        repository.getSudu(suduId)
    }

    fun deleteSudu(sudu: Sudu) = viewModelScope.launch {
        repository.deleteSudu(sudu)
    }

    fun deleteCompletedSudus() = viewModelScope.launch {
        repository.deleteCompletedSudus()
    }
}
