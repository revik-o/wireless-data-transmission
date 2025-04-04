package com.revik_o.infrastructure.tcp.exception

import com.revik_o.core.AppVersion

open class UnsupportedVersionException(appVersion: AppVersion) :
    UnsupportedException("The remote device does not support the version ${appVersion.name}")