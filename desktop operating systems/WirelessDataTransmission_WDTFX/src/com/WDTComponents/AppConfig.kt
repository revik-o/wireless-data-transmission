package com.WDTComponents

import com.WDTComponents.Action.SendType.IActionForSendType
import com.WDTComponents.AlertInterfaces.ILoadAlert
import com.WDTComponents.AlertInterfaces.ILittleMessage
import com.WDTComponents.AlertInterfaces.IMessage
import com.WDTComponents.DataBase.IWorkingWithDataBase
import com.WDTComponents.DataBase.ModelDAO.IDeviceModelDAO
import com.WDTComponents.IPWork.IPackageIP
import com.WDTComponents.ServerControll.IServer
import com.WDTComponents.SystemClipboard.ISystemClipboard
import com.WDTComponents.WorkingWithClient.IWorkingWithClient
import java.io.File

/**
 *
 */
object AppOption {

    val SOCKET_PORT = 4000

    val MAX_COUNT_OF_CONNECT = 100

    var SOCKET_TIMEOUT = 500

    const val BUFFER_SIZE_FOR_TRANSFER = 1024 * 8

    lateinit var LOCAL_DEVICE_NAME: String

    lateinit var DEVICE_TYPE: String

    var SERVER_SOCKET_IS_ON = false

    lateinit var DIRECTORY_FOR_DOWNLOAD_FILES: File

}

/**
 *
 */
object AppConfig {

    object Action {

        object SendTypeInterface {

            lateinit var iActionForSendType: IActionForSendType

        }

    }

    object AlertInterface {

        lateinit var errorIMessage: ILittleMessage

        lateinit var iLoadAlert: Class<ILoadAlert>

        lateinit var iMessage: IMessage

        lateinit var littleIMessage: ILittleMessage

    }

    object DataBase {

        object WorkWithDataBaseInterface {

            lateinit var iWorkingWithDataBase: IWorkingWithDataBase

        }

        object ModelDAOInterface {

            lateinit var iDeviceModelDAO: IDeviceModelDAO

        }

    }

    object IPWorkInterface {

        object IPV4 {

            lateinit var iIP: IPackageIP

        }

    }

    object ServerControllInterface {

        lateinit var iServer: IServer

    }

    object SystemClipboard {

        lateinit var iSystemClipboard: ISystemClipboard

    }

    object WorkingWithClientInterface {

        lateinit var iWorkingWithClient: IWorkingWithClient

    }

}

/**
 *
 */
object TypeDeviceENUM {

    val PHONE = "PHONE".intern()

    val COMPUTER = "COMPUTER".intern()

}