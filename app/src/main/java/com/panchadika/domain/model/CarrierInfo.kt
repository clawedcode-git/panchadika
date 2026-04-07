package com.panchadika.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarrierInfo(
    val carrierName: String?,
    val simOperator: String?,
    val networkOperator: String?,
    val simCountryIso: String?,
    val mcc: Int?,
    val mnc: Int?,
    val simSlotIndex: Int,
    val phoneNumber: String?,
    val networkType: String?,
    val isSimReady: Boolean,
    val smscAddress: String?
) : Parcelable {
    companion object {
        fun empty() = CarrierInfo(
            carrierName = null,
            simOperator = null,
            networkOperator = null,
            simCountryIso = null,
            mcc = null,
            mnc = null,
            simSlotIndex = 0,
            phoneNumber = null,
            networkType = null,
            isSimReady = false,
            smscAddress = null
        )
    }
}