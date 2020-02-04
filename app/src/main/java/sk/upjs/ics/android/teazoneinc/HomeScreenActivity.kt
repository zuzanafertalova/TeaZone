package sk.upjs.ics.android.teazoneinc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import sk.upjs.ics.android.teazoneinc.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapter

class HomeScreenActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapter = DbAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        authAdapter.getFirebaseUser()?.let {
            dbAdapter.setFirebaseUserToLocalUser(it)
        }

    }
}
