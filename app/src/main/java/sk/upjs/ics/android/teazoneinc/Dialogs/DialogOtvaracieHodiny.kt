package sk.upjs.ics.android.teazoneinc.Dialogs

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_otvaracie_hodiny.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.DataOpeningHours
import sk.upjs.ics.android.teazoneinc.R

open class DialogOtvaracieHodiny : DialogFragment() {

    private val dbAdapterUser = DbAdapterUser()

    var po = DataOpeningHours()
    var ut = DataOpeningHours()
    var st = DataOpeningHours()
    var št = DataOpeningHours()
    var pi = DataOpeningHours()
    var so = DataOpeningHours()
    var ne = DataOpeningHours()

    fun onCreateDialog(activity: Activity, inflater: LayoutInflater, openHours:DataOpeningHours): Dialog {

        val openingHours = openHours
        val builder = AlertDialog.Builder(activity)
        val dialogOtvaracieHodinky = inflater.inflate(R.layout.dialog_otvaracie_hodiny, null)

        builder.setNegativeButton("Zavrieť", DialogInterface.OnClickListener { dialog, id -> })
        if( tvDoPo.isNotEmpty()){
            builder.setView(dialogOtvaracieHodinky)
        }
        else{
            builder.setMessage("Otváracie hodiny zatiaľ neboli určené.")
        }

        return builder.create()
    }
}