package sk.upjs.ics.android.teazoneinc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.activity_signup_firma.*
import sk.upjs.ics.android.teazoneinc.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapterUser

class HomeScreenActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        authAdapter.getFirebaseUser()?.let {
            dbAdapterUser.setFirebaseUserToLocalUser(it)
        }

        button_user.setOnClickListener(View.OnClickListener { ///idem na profil usera
            intent = Intent(this, ProfilUserActivity::class.java)
            startActivity(intent)
        })
        button_find.setOnClickListener(View.OnClickListener { ///idem nazad domov
            intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        })

    }

//    fun setUsername(){
//        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")){
//            if (!dbAdapterUser.userUser.username.equals("")) {
//                fragmentSetUserame.view?.visibility = View.GONE
//            }
//        }
//        else{
//            if (!dbAdapterUser.userFirma.username.equals("")) {
//                fragmentSetUserame.view?.visibility = View.GONE
//            }
//        }
//
//    }
}
