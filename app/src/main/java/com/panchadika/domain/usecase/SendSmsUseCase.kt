package com.panchadika.domain.usecase

import com.panchadika.domain.model.Message
import com.panchadika.domain.repository.SmsRepository
import javax.inject.Inject

class SendSmsUseCase @Inject constructor(
    private val smsRepository: SmsRepository
) {
    suspend operator fun invoke(address: String, body: String): Result<Message> {
        if (address.isBlank()) {
            return Result.failure(IllegalArgumentException("Address cannot be empty"))
        }
        if (body.isBlank()) {
            return Result.failure(IllegalArgumentException("Message body cannot be empty"))
        }
        return smsRepository.sendSms(address.trim(), body.trim())
    }
}