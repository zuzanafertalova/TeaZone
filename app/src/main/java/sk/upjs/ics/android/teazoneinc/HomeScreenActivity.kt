package sk.upjs.ics.android.teazoneinc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_home_screen.*
import sk.upjs.ics.android.teazoneinc.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapterUser

class HomeScreenActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        setUsernameFragment()
    }


    fun setUsernameFragment(){
        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")){
            if (!DbAdapterUser.userUser.username.equals("")) {
                fragmentSetUserame.view?.visibility = View.GONE
            }
        }
        else{
            if (!DbAdapterUser.userFirma.username.equals("")) {
                fragmentSetUserame.view?.visibility = View.GONE
            }
        }

    }

}
