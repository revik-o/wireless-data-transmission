package com.WDTComponents.WorkingWithData.WorkingWithFilesAndDirectories

import com.WDTComponents.DelegateMethods.IDelegateMethodDoubleArg
import com.WDTComponents.DelegateMethods.IDelegateMethodStringArg
import java.io.File
import java.io.DataOutputStream
import java.io.DataInputStream
import java.io.FileInputStream
import java.io.FileOutputStream

object DataTransferFromFile {

    fun sendDataFromFile(file: File, dataOutputStream: DataOutputStream, dataInputStream: DataInputStream, iDelegateMethodStringArg: IDelegateMethodStringArg) {
        /**
         * Change Text in Load Stage Message
         */
        iDelegateMethodStringArg.voidMethod(file.name)
        /**
         * Start send file
         */
        dataOutputStream.writeUTF(file.name)
        dataOutputStream.writeLong(file.length())
        FileData.writeFile(FileInputStream(file), dataOutputStream)
//    if (!dataInputStream.readBoolean()) {} /////////////////// ?
        println(dataInputStream.read()) //////////////////////////////////////////
    }

    fun acceptDataFromFile(dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, outputPath: String, iDelegateMethodStringArg: IDelegateMethodStringArg, iDelegateMethodDoubleArg: IDelegateMethodDoubleArg) {
        val nameFile: String = dataInputStream.readUTF()
        val lengthFile: Long = dataInputStream.readLong()
        val path = "$outputPath/$nameFile"
        /**
         * Change Text in Load Stage Message
         */
        iDelegateMethodStringArg.voidMethod(path)
        /**
         * Start accepting file
         */
        if (lengthFile != 0.toLong())
            FileData.readFile(dataInputStream, FileOutputStream(path), lengthFile, iDelegateMethodDoubleArg)
        else File(path).createNewFile()
//    dataOutputStream.writeBoolean(true)  ///////////////////////// ?
        dataOutputStream.write(0) ///////////////////////////////// ?
    }

}