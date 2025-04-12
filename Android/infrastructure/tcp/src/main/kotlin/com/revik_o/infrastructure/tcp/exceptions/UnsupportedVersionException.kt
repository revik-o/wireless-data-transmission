package com.revik_o.infrastructure.tcp.exceptions

import com.revik_o.core.common.AppVersion

open class UnsupportedVersionException(appVersion: AppVersion) :
    UnsupportedException("The remote device does not support the version ${appVersion.name}")