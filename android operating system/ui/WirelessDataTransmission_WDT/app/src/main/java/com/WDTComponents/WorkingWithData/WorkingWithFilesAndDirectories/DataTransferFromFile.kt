package com.WDTComponents.WorkingWithData.WorkingWithFilesAndDirectories

import com.WDTComponents.AppConfig
import com.WDTComponents.DelegateMethods.IDelegateMethodDoubleArg
import com.WDTComponents.DelegateMethods.IDelegateMethodStringArg
import com.WDTComponents.Other.addToDataBaseAcceptedFile
import com.WDTComponents.Other.addToDataBaseTransferredFile
import java.io.*

object DataTransferFromFile {

    fun sendDataFromFile(
        name: String,
        length: Long,
        absolutePath: String,
        fileInputStream: InputStream,
        dataOutputStream: DataOutputStream,
        dataInputStream: DataInputStream,
        iDelegateMethodStringArg: IDelegateMethodStringArg,
        iDelegateMethodDoubleArg: IDelegateMethodDoubleArg
    ) {
        /**
         * Change Text in Load Stage Message
         */
        iDelegateMethodStringArg.voidMethod(name)
        /**
         * Add file to Data Base
         */
        addToDataBaseTransferredFile(name, absolutePath, length)
        /**
         * Start send file
         */
        dataOutputStream.writeUTF(name)
        val lengthFile: Long = length
        dataOutputStream.writeLong(lengthFile)
        FileData.writeFile(
            fileInputStream,
            dataOutputStream,
            lengthFile,
            iDelegateMethodDoubleArg
        )
        dataInputStream.read()
    }

    fun acceptDataFromFile(
        dataInputStream: DataInputStream,
        dataOutputStream: DataOutputStream,
        deviceName: String,
        deviceType: String,
        ipAddress: String,
        outputPath: String,
        iDelegateMethodStringArg: IDelegateMethodStringArg,
        iDelegateMethodDoubleArg: IDelegateMethodDoubleArg,
        iDelegateMethodFileName: IDelegateMethodStringArg
    ) {
        val nameFile: String = dataInputStream.readUTF()
        val lengthFile: Long = dataInputStream.readLong()
        val path = "$outputPath/$nameFile"
        /**
         * Set file name
         */
        iDelegateMethodFileName.voidMethod(path)
        /**
         * Change Text in Load Stage Message
         */
        iDelegateMethodStringArg.voidMethod(path)
        /**
         * Add file to Data Base
         */
        addToDataBaseAcceptedFile(nameFile, path, lengthFile, deviceName, deviceType, ipAddress)
        /**
         * Start accepting file
         */
        if (lengthFile != 0.toLong())
            FileData.readFile(dataInputStream, FileOutputStream(path), lengthFile, iDelegateMethodDoubleArg)
        else File(path).createNewFile()
        AppConfig.DataBase.ModelDAOInterface.iFileModelDAO
        dataOutputStream.write(0)
    }

}