package com.WDTlib

import com.WDTlib.Action.SendType.IActionForSendType
import com.WDTlib.AlertInterface.ILoadAlert
import com.WDTlib.AlertInterface.IMessage
import com.WDTlib.DataBaseInterface.IModelDAO.IDeviceModelDAO
import com.WDTlib.DataBaseInterface.IWorkingWithDataBase
import com.WDTlib.IPWork.IPackageIP
import com.WDTlib.ServerControll.IServer
import com.WDTlib.WorkingWithClient.IWorkingWithClient

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