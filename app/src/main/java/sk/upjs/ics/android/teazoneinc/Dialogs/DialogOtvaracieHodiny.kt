package sk.upjs.ics.android.teazoneinc.Dialogs

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.DataOpeningHours
import sk.upjs.ics.android.teazoneinc.R

open class DialogOtvaracieHodiny : DialogFragment() {

    private val dbAdapterUser = DbAdapterUser()
    fun onCreateDialog(activity: Activity, openHours:DataOpeningHours): Dialog {

        val openingHours = openHours

        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Otváracie hodiny zatiaľ neboli určené.")
            .setNegativeButton("Zavrieť",
                DialogInterface.OnClickListener { dialog, id ->
                })
        return builder.create()
    }
}