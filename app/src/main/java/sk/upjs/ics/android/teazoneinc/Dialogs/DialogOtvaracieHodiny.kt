package sk.upjs.ics.android.teazoneinc.Dialogs

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import sk.upjs.ics.android.teazoneinc.R

class DialogOtvaracieHodiny : DialogFragment() {
    fun onCreateDialog(activity: Activity): Dialog {

        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Otváracie hodiny zatiaľ neboli určené.")
            .setNegativeButton("Zavrieť",
                DialogInterface.OnClickListener { dialog, id ->
                })
        return builder.create()
    }
}