package ua.edu.onaft.wirelessdatatransmission_wdt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.WDTComponents.AppConfig

class AcceptedFileHistoryActivity : AppCompatActivity() {

    private lateinit var mainView:ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accepted_file_history)
        mainView = findViewById<ConstraintLayout>(R.id.acceptedFileHistoryConstraintLayout)
//        AppConfig.DataBase.ModelDAOInterface.iAcceptedFilesHistoryModelDAO.
    }
}