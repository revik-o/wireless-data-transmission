package com.WDTComponents.WorkingWithData.WorkingWithFilesAndDirectories

import com.WDTComponents.AppOption
import com.WDTComponents.DelegateMethods.IDelegateMethodDoubleArg
import com.WDTComponents.DelegateMethods.IDelegateMethodStringArg
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File

object DataTransferFromDirectory {

    fun sendDataFromDirectory(directory: File, rootDirectory: File, dataOutputStream: DataOutputStream, dataInputStream: DataInputStream, iDelegateMethodStringArg: IDelegateMethodStringArg) {
        val listDirectories: ArrayList<File> = ArrayList()
        val listFiles: ArrayList<File> = ArrayList()
        for (file in directory.listFiles()) { if (file.isFile) listFiles.add(file) else listDirectories.add(file) }
        dataOutputStream.writeUTF(directory.absolutePath.substring(rootDirectory.absolutePath.length - rootDirectory.name.length))
        dataOutputStream.write(listFiles.size)
        listFiles.forEach { DataTransferFromFile.sendDataFromFile(
            it,
            dataOutputStream,
            dataInputStream,
            iDelegateMethodStringArg
        ) }
        dataOutputStream.write(listDirectories.size)
        listDirectories.forEach {
            this.sendDataFromDirectory(
                File(it.absolutePath),
                rootDirectory,
                dataOutputStream,
                dataInputStream,
                iDelegateMethodStringArg
            )
        }
    }

    fun acceptDataFromDirectory(dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, iDelegateMethodStringArg: IDelegateMethodStringArg, iDelegateMethodDoubleArg: IDelegateMethodDoubleArg) {
        val path: String = dataInputStream.readUTF()
        File("${AppOption.DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath}/$path").mkdirs()
        val countOfFiles: Int = dataInputStream.read()
        for (i in 0 until countOfFiles)
            DataTransferFromFile.acceptDataFromFile(
                dataInputStream,
                dataOutputStream,
                "${AppOption.DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath}/$path",
                iDelegateMethodStringArg,
                iDelegateMethodDoubleArg
            )
        val countOfDirectories: Int = dataInputStream.read()
        for (i in 0 until countOfDirectories)
            this.acceptDataFromDirectory(
                dataInputStream,
                dataOutputStream,
                iDelegateMethodStringArg,
                iDelegateMethodDoubleArg
            )
    }

}