package com.revik_o.infrastructure.common.utils

import com.revik_o.test.utils.LogUtils
import kotlinx.coroutines.test.runTest
import org.junit.Test

class IPv4UtilsTest {

    @Test
    fun scanTest() = runTest { IPv4Utils.iterateOverNetwork { ip -> LogUtils.debug(ip) } }
}