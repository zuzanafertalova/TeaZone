package sk.upjs.ics.android.teazoneinc.Dialogs

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class DialogMenu : DialogFragment() {

     fun onCreateDialog(activity: Activity): Dialog {

        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Chcete uložiť súbor?")
        builder.setPositiveButton("Potvrdiť", DialogInterface.OnClickListener{ dialog, id ->

        })
        builder.setNegativeButton("Zrušiť", DialogInterface.OnClickListener{dialog,  id ->

        })
        return builder.create()
    }
}