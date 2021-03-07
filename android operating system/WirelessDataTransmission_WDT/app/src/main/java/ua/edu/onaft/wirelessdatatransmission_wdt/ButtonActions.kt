package ua.edu.onaft.wirelessdatatransmission_wdt

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.view.View

object ButtonActions {

    object WelcomeActivity {

        class StartActionListener(activity: Activity): View.OnClickListener {

            private val activity: Activity = activity

            private class AlertButtonAction(activity: Activity): DialogInterface.OnClickListener {

                val activity: Activity = activity

                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when(which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            activity.startActivity(Intent(activity.applicationContext, DataFromFolder::class.java))
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                        }
                    }
                }

            }

//            @RequiresApi(Build.VERSION_CODES.M)
            override fun onClick(view: View?) {
//                activity.requestPermissions(arrayOf(
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                    android.Manifest.permission.MANAGE_EXTERNAL_STORAGE,
//                ), 1)
//
//                val f = File(Environment.getExternalStorageDirectory(), "text1")
//
//                if (!f.exists()) {
//                    Toast.makeText(activity.application, "${f.absolutePath} -> created", Toast.LENGTH_LONG).show()
//                    f.mkdirs()
//                } else {
//                    Toast.makeText(activity.application, "${f.absolutePath} -> NO", Toast.LENGTH_LONG).show()
//                }

//                val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
//                intent.type = "*/*"
//                activity.startActivityForResult(intent, 10)

                val alertButtonAction = AlertButtonAction(this.activity)
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(view?.context)
                alertDialog.setMessage("Norm?").setPositiveButton("Yes", alertButtonAction)
                    .setNegativeButton("No", alertButtonAction).setTitle("nice").show()
            }
        }

    }

}