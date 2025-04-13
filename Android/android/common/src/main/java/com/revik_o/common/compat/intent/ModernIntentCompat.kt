package com.revik_o.common.compat.intent

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.io.Serializable

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class ModernIntentCompat(intent: Intent) : IntentCompat(intent) {

    override fun <T : Parcelable> getParcelableArrayListExtraCompat(
        key: String,
        type: Class<T>
    ): ArrayList<T> = super.getParcelableArrayListExtra(key, type) ?: ArrayList()

    override fun <T : Serializable> getSerializableExtraCompat(key: String, type: Class<T>): T? =
        getSerializableExtra(key, type)

    override fun <T : Parcelable> getParcelableExtraCompat(key: String, type: Class<T>): T? =
        getParcelableExtraCompat(key, type)
}