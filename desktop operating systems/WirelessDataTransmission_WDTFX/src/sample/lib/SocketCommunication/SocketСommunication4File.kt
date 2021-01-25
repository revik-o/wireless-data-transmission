package sample.lib.FileWork

import sample.lib.Message.ILoadStageMessage
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun sendDataFromFile(file: File, dataOutputStream: DataOutputStream, dataInputStream: DataInputStream, iLoadStageMessage: ILoadStageMessage) {
    /**
     * Change Text in Load Stage Message
     */
    iLoadStageMessage.changeText4NameFile(file.name + "kek")
    /**
     * Start send file
     */
    dataOutputStream.writeUTF(file.name)
    dataOutputStream.writeLong(file.length())
    sendFile(FileInputStream(file), dataOutputStream)
//    if (!dataInputStream.readBoolean()) {} /////////////////// ?
    println(dataInputStream.read()) //////////////////////////////////////////
}

fun acceptDataFromClientFile(outputPath: String, dataInputStream: DataInputStream, dataOutputStream: DataOutputStream, iLoadStageMessage: ILoadStageMessage) {
    val nameFile: String = dataInputStream.readUTF()
    val lengthFile: Long = dataInputStream.readLong()
    val path = "$outputPath/$nameFile"
    /**
     * Change Text in Load Stage Message
     */
    iLoadStageMessage.changeText4NameFile(path + "kek1a")
    /**
     * Start accepting file
     */
    if (lengthFile != 0.toLong()) acceptFile(dataInputStream, FileOutputStream(path), lengthFile, iLoadStageMessage)
    else File(path).createNewFile()
//    dataOutputStream.writeBoolean(true)  ///////////////////////// ?
    dataOutputStream.write(0) ///////////////////////////////// ?
}