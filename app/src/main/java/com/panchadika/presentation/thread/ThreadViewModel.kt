package com.panchadika.presentation.thread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panchadika.domain.model.Message
import com.panchadika.domain.usecase.GetMessagesUseCase
import com.panchadika.domain.usecase.SendSmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ThreadUiState(
    val messages: List<Message> = emptyList(),
    val isLoading: Boolean = true,
    val isSending: Boolean = false,
    val error: String? = null,
    val sendSuccess: Boolean = false
)

@HiltViewModel
class ThreadViewModel @Inject constructor(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val sendSmsUseCase: SendSmsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ThreadUiState())
    val uiState: StateFlow<ThreadUiState> = _uiState.asStateFlow()

    private var currentAddress: String = ""

    fun loadMessages(threadId: Long, address: String) {
        currentAddress = address
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            getMessagesUseCase(threadId)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load messages"
                    )
                }
                .collect { messages ->
                    _uiState.value = _uiState.value.copy(
                        messages = messages,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }

    fun sendMessage(address: String, body: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSending = true, error = null)

            sendSmsUseCase(address, body)
                .onSuccess { message ->
                    val currentMessages = _uiState.value.messages.toMutableList()
                    currentMessages.add(message)
                    _uiState.value = _uiState.value.copy(
                        messages = currentMessages,
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