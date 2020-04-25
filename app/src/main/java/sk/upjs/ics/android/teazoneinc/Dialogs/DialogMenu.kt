package sk.upjs.ics.android.teazoneinc.Dialogs

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter

class DialogMenu : DialogFragment() {

     fun onCreateDialog(activity: Activity,firmaUsername:String?,menu:String?): Dialog {

         val storageAdapter= StorageAdapter()

         val builder = AlertDialog.Builder(activity)
         if (menu.isNullOrEmpty()){
             builder.setMessage("Menu nie je k dispozícií")
             builder.setPositiveButton("Zavrieť", DialogInterface.OnClickListener{ dialog, id ->

             })
         }
         else{
             builder.setMessage("Chcete uložiť súbor "+firmaUsername+"Menu.pdf?")
             builder.setPositiveButton("Potvrdiť", DialogInterface.OnClickListener{ dialog, id ->
                 storageAdapter.downloadDPFFile(menu,activity,firmaUsername!!)
             })
             builder.setNegativeButton("Zrušiť", DialogInterface.OnClickListener{dialog,  id ->

             })
         }

        return builder.create()
    }
}