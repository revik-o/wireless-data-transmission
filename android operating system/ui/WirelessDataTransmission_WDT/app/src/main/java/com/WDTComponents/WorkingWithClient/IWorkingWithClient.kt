package com.WDTComponents.WorkingWithClient

//import java.io.DataInputStream
//import java.io.DataOutputStream
import java.net.Socket
import java.util.concurrent.Semaphore

interface IWorkingWithClient {

    fun start(socket: Socket, semaphore: Semaphore)
//    fun action(dataInputStream: DataInputStream, dataOutputStream: DataOutputStream)      // Not used yet

}