package com.revik_o.core.data.model

import com.revik_o.core.common.RequestType
import java.util.Date
import kotlin.enums.enumEntries

data class HistoryModel(
    val id: Long,
    val typeOfResource: RequestType,
    val shortFormData: String,
    val commentFromSender: String?,
    val fromDevice: DeviceModel?, // Can be null if the action was “SENT”
    val action: ActionType,
    val date: Date = Date()
) {

    enum class ActionType(val signature: Short) {
        CLOSE_CONNECTION(-1), ACCEPT(0), SEND(1);

        companion object {
            fun getResourceTypeBySignature(signature: Short): ActionType? {
                for (type in enumEntries<ActionType>()) {
                    if (signature == type.signature) {
                        return type
                    }
                }

                return null
            }
        }
    }
}