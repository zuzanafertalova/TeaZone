package sk.upjs.ics.android.teazoneinc.Dialogs

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DialogOdstranitUcet : DialogFragment() {

    fun onCreateDialog(activity: Activity): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Naozaj chcete odstrániť účet?")
            .setPositiveButton("Potvrdiť",
                DialogInterface.OnClickListener{ dialog, id ->
            })
            .setNegativeButton("Zrušiť",
                DialogInterface.OnClickListener{ dialog, id ->
                })
        return builder.create()
    }
}