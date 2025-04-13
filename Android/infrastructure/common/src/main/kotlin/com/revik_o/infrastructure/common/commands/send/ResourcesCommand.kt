package com.revik_o.infrastructure.common.commands.send

import com.revik_o.core.common.RequestType

data class ResourcesCommand<T>(override val ip: String, val refs: Array<out T>) : SendCommandI {
    override val requestType = RequestType.RESOURCES

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ResourcesCommand<*>

        if (ip != other.ip) return false
        if (!refs.contentEquals(other.refs)) return false
        if (requestType != other.requestType) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ip.hashCode()
        result = 31 * result + refs.contentHashCode()
        result = 31 * result + requestType.hashCode()
        return result
    }
}
