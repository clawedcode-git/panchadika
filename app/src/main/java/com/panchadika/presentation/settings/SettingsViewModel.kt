package com.panchadika.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panchadika.domain.model.CarrierInfo
import com.panchadika.domain.usecase.GetCarrierInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val carrierInfo: CarrierInfo? = null,
    val isLoadingCarrier: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getCarrierInfoUseCase: GetCarrierInfoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun loadCarrierInfo() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingCarrier = true)

            try {
                val carrierInfo = getCarrierInfoUseCase.refresh()
                _uiState.value = _uiState.value.copy(
                    carrierInfo = carrierInfo,
                    isLoadingCarrier = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingCarrier = false,
                    error = e.message
                )
            }
        }
    }
}