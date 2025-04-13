package com.revik_o.common.compat.intent

import android.content.Intent
import android.net.Uri
import android.os.Parcelable
import com.revik_o.common.definitions.IntentExtraApplicationKeys.FETCH_OR_SEND_KEY
import com.revik_o.common.definitions.IntentExtraApplicationKeys.REQUEST_TYPE_KEY
import com.revik_o.common.definitions.IntentExtraApplicationKeys.RESOURCE_SEQUENCE
import com.revik_o.core.common.FetchOrSendType
import com.revik_o.core.common.FetchOrSendType.FETCH
import com.revik_o.core.common.FetchOrSendType.SEND
import com.revik_o.core.common.RequestType
import com.revik_o.core.common.RequestType.CLIPBOARD
import com.revik_o.core.common.RequestType.RESOURCES
import java.io.Serializable

abstract class IntentCompat(intent: Intent) : Intent(intent) {

    abstract fun <T : Parcelable> getParcelableArrayListExtraCompat(
        key: String,
        type: Class<T>
    ): ArrayList<T>

    abstract fun <T : Serializable> getSerializableExtraCompat(key: String, type: Class<T>): T?

    abstract fun <T : Parcelable> getParcelableExtraCompat(key: String, type: Class<T>): T?

    fun getFetchOrSendType(): FetchOrSendType =
        getSerializableExtraCompat(FETCH_OR_SEND_KEY, FetchOrSendType::class.java)
            ?: throw IllegalStateException()

    fun getRequestTypeType(): RequestType =
        getSerializableExtraCompat(REQUEST_TYPE_KEY, RequestType::class.java)
            ?: throw IllegalStateException()

    fun getResourceSequence(): List<Uri> =
        getParcelableArrayListExtraCompat(RESOURCE_SEQUENCE, Uri::class.java)

    fun sendClipboard(): IntentCompat {
        putExtra(FETCH_OR_SEND_KEY, SEND)
        putExtra(REQUEST_TYPE_KEY, CLIPBOARD)
        return this
    }

    fun sendResources(resources: ArrayList<Uri>): IntentCompat {
        putExtra(FETCH_OR_SEND_KEY, SEND)
        putExtra(REQUEST_TYPE_KEY, RESOURCES)
        putParcelableArrayListExtra(RESOURCE_SEQUENCE, resources)
        return this
    }

    fun getRemoteClipboard(): IntentCompat {
        putExtra(FETCH_OR_SEND_KEY, FETCH)
        putExtra(REQUEST_TYPE_KEY, CLIPBOARD)
        return this
    }
}