package com.panchadika.domain.usecase

import com.panchadika.domain.model.Message
import com.panchadika.domain.repository.SmsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val smsRepository: SmsRepository
) {
    operator fun invoke(threadId: Long): Flow<List<Message>> {
        return smsRepository.getMessages(threadId)
    }

    fun byAddress(address: String): Flow<List<Message>> {
        return smsRepository.getMessagesByAddress(address)
    }
}