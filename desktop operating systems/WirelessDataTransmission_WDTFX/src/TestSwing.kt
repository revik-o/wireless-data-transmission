import com.WDTComponents.StartApplicationConfigs.DefaultStartApplicationConfigs
import com.sun.javafx.webkit.theme.PopupMenuImpl
import javafx.application.Platform
import javafx.embed.swing.JFXPanel
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ContextMenu
import sample.Platform.COMMON_DIRECTORY
import sample.Platform.Message
import sample.Platform.PlatformDataBase
import sample.Platform.RealizeAlertInterface
import sampleimagePath.APPLICATION_NAME
import sampleimagePath.ICON
import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.File
import java.io.IOException
import java.util.Objects
import javax.swing.JFrame
import kotlin.system.exitProcess

fun main() {
    val frame = JFrame()
    val dimension = Toolkit.getDefaultToolkit().screenSize
    val width: Int = dimension.width / 2
    val height = (dimension.height / 1.5).toInt()
    frame.preferredSize = Dimension(width, ((dimension.height / 1.5).toInt()))
    frame.title = APPLICATION_NAME
    frame.setLocation(
            dimension.width / 2 - width / 2,
            dimension.height / 2 - height / 2
    )
    frame.addWindowListener(object : WindowAdapter() {
        override fun windowClosing(e: WindowEvent) {
            exitProcess(0)
        }
    })
    Thread {
        val jfxPanel = JFXPanel()
        if (!File(COMMON_DIRECTORY).exists()) File(COMMON_DIRECTORY).mkdirs()
        val configs = DefaultStartApplicationConfigs(RealizeAlertInterface(), Message(), PlatformDataBase(), File(System.getProperty("user.home") + "/Downloads/WirelessDataTransmission_WDTFX"))
        configs.start()
        Platform.runLater {
            try {
                val root = FXMLLoader.load<Parent>(Objects.requireNonNull(FMain::class.java.getResource("sample/sample.fxml")))
                jfxPanel.scene = Scene(root)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        frame.add(jfxPanel)
        frame.pack()
        System.gc()
    }.start()
    frame.pack()
    frame.isVisible = true
    addToSysTray()
}

fun addToSysTray() {
    ICON = FMain::class.java.getResource("ic_launcher_round.png").file
//    print(ICON)
    val systemTray: SystemTray = SystemTray.getSystemTray()
    val image = Toolkit.getDefaultToolkit().getImage(FMain::class.java.getResource("ic_launcher_round.png"))
    val popup = PopupMenu()
    val activity = MenuItem("Launch")
//    activity.addActionListener {  }
    popup.add(activity)
    val trayIcon = TrayIcon(image, "WDT", popup)
    trayIcon.isImageAutoSize = true
    systemTray.add(trayIcon)
    trayIcon.displayMessage("Hi", "message", TrayIcon.MessageType.INFO)
}