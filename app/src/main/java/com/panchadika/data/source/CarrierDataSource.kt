package com.panchadika.data.source

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.CarrierConfigManager
import android.telephony.SubscriptionManager
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import com.panchadika.domain.model.CarrierInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CarrierDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val telephonyManager: TelephonyManager by lazy {
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    }

    private val subscriptionManager: SubscriptionManager by lazy {
        context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE) as SubscriptionManager
    }

    @Suppress("DEPRECATION")
    private val carrierConfigManager: CarrierConfigManager by lazy {
        context.getSystemService(Context.CARRIER_CONFIG_SERVICE) as CarrierConfigManager
    }

    suspend fun getCarrierInfo(): CarrierInfo = withContext(Dispatchers.IO) {
        val hasPhonePermission = ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPhonePermission) {
            return@withContext CarrierInfo.empty()
        }

        try {
            val subscriptionInfo = subscriptionManager.activeSubscriptionInfoList?.firstOrNull()
            val slotIndex = subscriptionInfo?.simSlotIndex ?: 0
            
            val simOperator = (subscriptionInfo?.mccString ?: "") + (subscriptionInfo?.mncString ?: "")
            val networkOperator = telephonyManager.networkOperator
            val carrierName = subscriptionInfo?.carrierName?.toString() 
                ?: telephonyManager.networkOperatorName
            val simCountryIso = getSimCountryIso()
            val mcc = subscriptionInfo?.mcc
            val mnc = subscriptionInfo?.mnc
            val phoneNumber = subscriptionInfo?.number
            val networkType = getNetworkTypeName()
            val isSimReady = checkSimReady()
            val smscAddress = "Auto"

            CarrierInfo(
                carrierName = carrierName,
                simOperator = simOperator,
                networkOperator = networkOperator,
                simCountryIso = simCountryIso,
                mcc = mcc,
                mnc = mnc,
                simSlotIndex = slotIndex,
                phoneNumber = phoneNumber,
                networkType = networkType,
                isSimReady = isSimReady,
                smscAddress = smscAddress
            )
        } catch (e: Exception) {
            CarrierInfo.empty()
        }
    }

    @Suppress("DEPRECATION")
    private fun getSimCountryIso(): String? {
        return try {
            val simState = telephonyManager.simState
            if (simState == TelephonyManager.SIM_STATE_READY) {
                telephonyManager.simCountryIso.takeIf { it.isNotEmpty() }
            } else null
        } catch (e: Exception) {
            null
        }
    }

    private fun checkSimReady(): Boolean {
        return try {
            val simState = telephonyManager.simState
            simState == TelephonyManager.SIM_STATE_READY
        } catch (e: Exception) {
            false
        }
    }

    private fun getNetworkTypeName(): String {
        return try {
            when (telephonyManager.dataNetworkType) {
                TelephonyManager.NETWORK_TYPE_LTE -> "4G LTE"
                TelephonyManager.NETWORK_TYPE_NR -> "5G"
                TelephonyManager.NETWORK_TYPE_HSPAP, TelephonyManager.NETWORK_TYPE_HSPA -> "3G HSPA"
                TelephonyManager.NETWORK_TYPE_EDGE -> "2G EDGE"
                TelephonyManager.NETWORK_TYPE_GPRS -> "2G GPRS"
                TelephonyManager.NETWORK_TYPE_CDMA -> "CDMA"
                TelephonyManager.NETWORK_TYPE_EVDO_0, TelephonyManager.NETWORK_TYPE_EVDO_A, 
                TelephonyManager.NETWORK_TYPE_EVDO_B, TelephonyManager.NETWORK_TYPE_EHRPD -> "EVDO"
                else -> "Unknown"
            }
        } catch (e: Exception) {
            "Unknown"
        }
    }

    fun getSmscAddress(): String? {
        return "Auto"
    }
}