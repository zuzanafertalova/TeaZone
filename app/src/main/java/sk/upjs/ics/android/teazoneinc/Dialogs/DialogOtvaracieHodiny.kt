package sk.upjs.ics.android.teazoneinc.Dialogs

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginLeft
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_otvaracie_hodiny.*
import kotlinx.android.synthetic.main.dialog_otvaracie_hodiny.view.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.DataOpeningHours
import sk.upjs.ics.android.teazoneinc.R

open class DialogOtvaracieHodiny(inflater: LayoutInflater) : DialogFragment() {

    private val dbAdapterUser = DbAdapterUser()

    fun onCreateDialog(activity: Activity, inflater: LayoutInflater, openHours:DataOpeningHours): Dialog {
        val dialogView = inflater.inflate(R.layout.dialog_otvaracie_hodiny, null)
        val openingHours = openHours
        val builder = AlertDialog.Builder(activity)
        builder.setNegativeButton("Zavrieť", DialogInterface.OnClickListener { dialog, id -> })
        if(openingHours.po.isEmpty()){
            builder.setMessage("Otváracie hodiny zatiaľ neboli určené.")
        }
        else{
            dialogView.tvOdPo.text = openingHours.po.get(key = "OD")
            dialogView.tvOdUt.text = openingHours.ut.get(key = "OD")
            dialogView.tvOdSt.text = openingHours.st.get(key = "OD")
            dialogView.tvOdŠt.text = openingHours.št.get(key = "OD")
            dialogView.tvOdPi.text = openingHours.pi.get(key = "OD")
            dialogView.tvOdSo.text = openingHours.so.get(key = "OD")
            dialogView.tvOdNe.text = openingHours.ne.get(key = "OD")

            dialogView.tvDoPo.text = openingHours.po.get(key = "DO")
            dialogView.tvDoUt.text = openingHours.ut.get(key = "DO")
            dialogView.tvDoSt.text = openingHours.st.get(key = "DO")
            dialogView.tvDoŠt.text = openingHours.št.get(key = "DO")
            dialogView.tvDoPi.text = openingHours.pi.get(key = "DO")
            dialogView.tvDoSo.text = openingHours.so.get(key = "DO")
            dialogView.tvDoNe.text = openingHours.ne.get(key = "DO")

            builder.setView(dialogView)
        }

        return builder.create()
    }

}