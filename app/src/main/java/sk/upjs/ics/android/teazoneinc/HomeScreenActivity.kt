package sk.upjs.ics.android.teazoneinc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.fragment_set_username2.*
import sk.upjs.ics.android.teazoneinc.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapterUser

class HomeScreenActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        btnSetUsername.setOnClickListener(View.OnClickListener {
            val username=tvSetUsername.text.toString()
            authAdapter.currentUser?.let {
                dbAdapterUser.setUsername(it,username)
                fragmentSetUserame.view?.visibility = View.GONE
            }
        })
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
