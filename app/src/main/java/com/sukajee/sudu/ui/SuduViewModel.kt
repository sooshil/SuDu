package com.sukajee.sudu.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sukajee.sudu.data.model.BottomSheetUiState
import com.sukajee.sudu.data.model.MainUiState
import com.sukajee.sudu.data.model.Sudu
import com.sukajee.sudu.data.repository.SuduRepository
import com.sukajee.sudu.data.util.OrderType
import com.sukajee.sudu.data.util.SuduOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
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

    private val _mainUiState = MutableStateFlow(MainUiState())
    val mainUiState = _mainUiState.asStateFlow()

    private val _bottomSheetUiState = MutableStateFlow(BottomSheetUiState())
    val bottomSheetUiState = _bottomSheetUiState.asStateFlow()

    init {
        fetchSudus(suduOrder = SuduOrder.CreatedDate(OrderType.Descending))
    }

    fun insertSudu(sudu: Sudu) = viewModelScope.launch {
        repository.insertSudu(sudu)
        updateBottomSheetUiState(null)
    }

    private fun fetchSudus(
        suduOrder: SuduOrder
    ) = viewModelScope.launch {

        _mainUiState.update { currentState ->
            currentState.copy(
                loading = true
            )
        }
        repository.getAllSudus().collect { sudus ->
            val sortedSudu = when (suduOrder.orderType) {
                is OrderType.Ascending -> {
                    when (suduOrder) {
                        is SuduOrder.Title -> sudus.sortedBy { it.title.lowercase() }
                        is SuduOrder.CreatedDate -> sudus.sortedBy { it.created }
                        is SuduOrder.Color -> sudus.sortedBy { it.color }
                    }
                }

                is OrderType.Descending -> {
                    when (suduOrder) {
                        is SuduOrder.Title -> sudus.sortedByDescending { it.title.lowercase() }
                        is SuduOrder.CreatedDate -> sudus.sortedByDescending { it.created }
                        is SuduOrder.Color -> sudus.sortedByDescending { it.color }
                    }
                }
            }
            _mainUiState.update { currentState ->
                currentState.copy(
                    sudus = sortedSudu,
                    loading = false
                )
            }
        }
    }

    private suspend fun getSudu(suduId: Int): Sudu = repository.getSudu(suduId)

    fun deleteSudu(sudu: Sudu) = viewModelScope.launch {
        repository.deleteSudu(sudu)
    }

    fun updateSudu(sudu: Sudu) = viewModelScope.launch {
        repository.updateSudu(sudu)
        updateBottomSheetUiState(null)
    }

    fun updateBottomSheetUiState(currentSudu: Sudu?, isEditMode: Boolean = false) {
        _bottomSheetUiState.update { currentState ->
            currentState.copy(
                isEditMode = isEditMode,
                currentSudu = currentSudu
            )
        }
    }

    fun deleteCompletedSudus() = viewModelScope.launch {
        repository.deleteCompletedSudus()
    }

    suspend fun onSuduClick(suduId: Int) {
        updateBottomSheetUiState(
            isEditMode = true,
            currentSudu = getSudu(suduId = suduId)
        )
    }
}
