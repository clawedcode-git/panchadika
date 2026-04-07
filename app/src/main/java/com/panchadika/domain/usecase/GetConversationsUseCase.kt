package com.panchadika.domain.usecase

import com.panchadika.domain.model.Conversation
import com.panchadika.domain.repository.SmsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConversationsUseCase @Inject constructor(
    private val smsRepository: SmsRepository
) {
    operator fun invoke(): Flow<List<Conversation>> {
        return smsRepository.getConversations()
    }
}