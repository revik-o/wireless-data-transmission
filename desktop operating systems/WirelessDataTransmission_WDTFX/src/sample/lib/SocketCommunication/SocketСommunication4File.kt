package sample.lib.FileWork

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

fun sendDataFromFile(file: File, dataOutputStream: DataOutputStream, dataInputStream: DataInputStream) {
    dataOutputStream.writeUTF(file.name)
    dataOutputStream.writeLong(file.length())
    sendFile(FileInputStream(file), dataOutputStream)
//    if (!dataInputStream.readBoolean()) {} /////////////////// ?
    println(dataInputStream.read()) //////////////////////////////////////////
}

fun acceptDataFromClientFile(outputPath: String, dataInputStream: DataInputStream, dataOutputStream: DataOutputStream) {
    val nameFile: String = dataInputStream.readUTF()
    val lengthFile: Long = dataInputStream.readLong()
    val path = "$outputPath/$nameFile"
    if (lengthFile != 0.toLong()) acceptFile(dataInputStream, FileOutputStream(path), lengthFile)
    else File(path).createNewFile()
//    dataOutputStream.writeBoolean(true)  ///////////////////////// ?
    dataOutputStream.write(0) ///////////////////////////////// ?
}