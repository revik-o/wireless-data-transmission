package com.revik_o.core.entity

import java.util.Date

data class HistoryEntity(
    val id: Long,
    val typeOfResource: ResourceType,
    val shortFormData: String,
    val commentFromSender: String?,
    val fromDevice: DeviceEntity?, // Can be null if the action was “SENT”
    val action: ActionType,
    val date: Date = Date()
) {

    companion object {
        enum class ResourceType(val signature: Short) {
            PING(0), FILE_OR_FOLDER(1), CLIPBOARD_DATA(2);

            companion object {
                fun getResourceTypeBySignature(signature: Short): ResourceType? {
                    for (type in ResourceType.entries) {
                        if (signature == type.signature) {
                            return type
                        }
                    }

                    return null
                }
            }
        }

        @Deprecated(message = "Mb can be removed")
        enum class ActionType(val signature: Short) {
            CLOSE_CONNECTION(-1), ACCEPT(0), SEND(1);

            companion object {
                fun getActionTypeBySignature(signature: Short): ActionType? {
                    for (type in ActionType.entries) {
                        if (signature == type.signature) {
                            return type
                        }
                    }

                    return null
                }
            }
        }
    }
}