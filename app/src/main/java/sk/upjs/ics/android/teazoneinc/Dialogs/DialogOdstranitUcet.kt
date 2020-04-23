package sk.upjs.ics.android.teazoneinc.Dialogs

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.firebase.firestore.EventListener
import sk.upjs.ics.android.teazoneinc.Activities.HomeScreenActivity
import sk.upjs.ics.android.teazoneinc.Activities.LoginActivity
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser

class DialogOdstranitUcet : DialogFragment() {

    private val dbAdapterUser=DbAdapterUser()
    private val authAdapterUser=AuthAdapter()

    fun onCreateDialog(activity: Activity): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage("Naozaj chcete odstrániť účet?")
            .setPositiveButton("Potvrdiť",
                DialogInterface.OnClickListener{ dialog, id ->

                            dbAdapterUser.deleteUserFromDatabase(authAdapterUser.currentUser!!, EventListener{spraviloSA,_->
                                if (spraviloSA!!){
                                    startLoginActivity()
                                }
                            })

            })
            .setNegativeButton("Zrušiť",
                DialogInterface.OnClickListener{ dialog, id ->
                })
        return builder.create()
    }

    fun startLoginActivity(){
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}