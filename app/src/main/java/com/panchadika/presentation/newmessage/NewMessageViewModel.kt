package com.panchadika.presentation.newmessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panchadika.domain.usecase.SendSmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NewMessageUiState(
    val isSending: Boolean = false,
    val sendSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class NewMessageViewModel @Inject constructor(
    private val sendSmsUseCase: SendSmsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewMessageUiState())
    val uiState: StateFlow<NewMessageUiState> = _uiState.asStateFlow()

    fun sendMessage(address: String, body: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSending = true, error = null)

            sendSmsUseCase(address, body)
                .onSuccess {
                    _uiState.value = _uiState.value.copy(
                        isSending = false,
                        sendSuccess = true
                    )
                }
                .onFailure { e ->
                    _uiState.value = _uiState.value.copy(
                        isSending = false,
                        error = e.message ?: "Failed to send message"
                    )
                }
        }
    }
}