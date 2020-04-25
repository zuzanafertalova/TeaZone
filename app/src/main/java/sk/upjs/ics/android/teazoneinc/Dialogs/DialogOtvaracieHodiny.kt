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

open class DialogOtvaracieHodiny(inflater: LayoutInflater) : DialogFragment() {

    private val dbAdapterUser = DbAdapterUser()


    val dialogOtvaracieHodinky = inflater.inflate(R.layout.dialog_otvaracie_hodiny, null)

    fun onCreateDialog(activity: Activity, inflater: LayoutInflater, openHours:DataOpeningHours): Dialog {

        val openingHours = openHours
        val builder = AlertDialog.Builder(activity)
        builder.setNegativeButton("Zavrieť", DialogInterface.OnClickListener { dialog, id -> })
        if(openingHours.po.isEmpty()){
            builder.setMessage("Otváracie hodiny zatiaľ neboli určené.")
        }
        else{
            tvOdPo.text = openingHours.po.get(key = "OD")
            tvOdUt.text = openingHours.ut.get(key = "OD")
            tvOdSt.text = openingHours.st.get(key = "OD")
            tvOdŠt.text = openingHours.št.get(key = "OD")
            tvOdPi.text = openingHours.pi.get(key = "OD")
            tvOdSo.text = openingHours.so.get(key = "OD")
            tvOdNe.text = openingHours.ne.get(key = "OD")

            tvDoPo.text = openingHours.po.get(key = "DO")
            tvDoUt.text = openingHours.ut.get(key = "DO")
            tvDoSt.text = openingHours.st.get(key = "DO")
            tvDoŠt.text = openingHours.št.get(key = "DO")
            tvDoPi.text = openingHours.pi.get(key = "DO")
            tvDoSo.text = openingHours.so.get(key = "DO")
            tvDoNe.text = openingHours.ne.get(key = "DO")

            builder.setView(dialogOtvaracieHodinky)
        }

        return builder.create()
    }

}