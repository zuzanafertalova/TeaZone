package sk.upjs.ics.android.teazoneinc.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_fragment_profile.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Dialogs.DialogOtvaracieHodiny
import sk.upjs.ics.android.teazoneinc.R

class SettingsActivity : AppCompatActivity() {

    val dbAdapterUser = DbAdapterUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_firma)

        if(dbAdapterUser.getStatusOfLoggedUser().equals("User")){
            setContentView(R.layout.activity_settings_user)
        }
    }
}
