package sample.lib.FileWork

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.File

fun sendDataFromDirectory(directory: File, rootDirectory: File, dataInputStream: DataInputStream, dataOutputStream: DataOutputStream) {
    val listDirectories: ArrayList<File> = ArrayList()
    val listFiles: ArrayList<File> = ArrayList()
    for (file in directory.listFiles()) { if (file.isFile) listFiles.add(file) else listDirectories.add(file) }
    dataOutputStream.writeUTF(directory.absolutePath.substring(rootDirectory.absolutePath.length - rootDirectory.name.length))
    dataOutputStream.write(listFiles.size)
    listFiles.forEach { sendDataFromFile(it, dataOutputStream, dataInputStream) }
    dataOutputStream.write(listDirectories.size)
    listDirectories.forEach { sendDataFromDirectory(File(it.absolutePath), rootDirectory, dataInputStream, dataOutputStream) }
}

fun acceptDataFromClientDirectory(dataInputStream: DataInputStream, dataOutputStream: DataOutputStream) {
    val path: String = dataInputStream.readUTF()
    File("${DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath}/$path").mkdirs()
    val countOfFiles: Int = dataInputStream.read()
    for (i in 0 until countOfFiles) acceptDataFromClientFile("${DIRECTORY_FOR_DOWNLOAD_FILES.absolutePath}/$path", dataInputStream, dataOutputStream)
    val countOfDirectories: Int = dataInputStream.read()
    for (i in 0 until countOfDirectories) acceptDataFromClientDirectory(dataInputStream, dataOutputStream)
}