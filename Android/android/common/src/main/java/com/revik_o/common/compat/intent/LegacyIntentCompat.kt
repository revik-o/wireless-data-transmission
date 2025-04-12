package com.revik_o.common.compat.intent

import android.content.Intent
import android.os.Parcelable
import java.io.Serializable

class LegacyIntentCompat(intent: Intent) : IntentCompat(intent) {

    override fun <T : Parcelable> getParcelableArrayListExtraCompat(
        key: String,
        type: Class<T>
    ): ArrayList<T> = super.getParcelableArrayListExtra(key) ?: ArrayList()

    override fun <T : Serializable> getSerializableExtraCompat(key: String, type: Class<T>): T? =
        type.cast(getSerializableExtra(key))

    override fun <T : Parcelable> getParcelableExtraCompat(key: String, type: Class<T>): T? =
        type.cast(getParcelableExtra(key))
}