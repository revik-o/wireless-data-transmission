package sample.lib.Message

interface ILoadStageMessage {

    fun showMessage()
    fun changeText4NameFile(text: String)
    fun changeProgress4ProgressBar(number: Double)
    fun changeTitle(text: String)
    fun closeMessage()

}