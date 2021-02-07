package sample.WDTComponents

import sample.DataBase.IWorkingWithDataBase
import sample.DataBase.ModelDAO.IDeviceModelDAO
import sample.WDTComponents.Action.SendType.IActionForSendType
import sample.WDTComponents.AlertInterfaces.ILoadAlert
import sample.WDTComponents.AlertInterfaces.IMessage
import sample.WDTComponents.IPWork.IPackageIP
import sample.WDTComponents.ServerControll.IServer
import sample.WDTComponents.WorkingWithClient.IWorkingWithClient
import java.io.File
import java.net.InetAddress

/**
 *
 */
object AppOption {

    val SOCKET_PORT = 4000

    val MAX_COUNT_OF_CONNECT = 100

    var SOCKET_TIMEOUT = 500

    val BUFFER_SIZE_FOR_TRANSFER = 1024 * 8

    var LOCAL_DEVICE_NAME = "${System.getProperty("user.name")} ${System.getProperty("os.name")} ${InetAddress.getLocalHost().hostName}"

    val DEVICE_TYPE = "COMPUTER"

    var SERVER_SOCKET_IS_ON = true

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

        lateinit var iLoadAlert: Class<ILoadAlert>

        lateinit var iMessage: IMessage

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

    object WorkingWithClientInterface {

        lateinit var iWorkingWithClient: IWorkingWithClient

    }

}