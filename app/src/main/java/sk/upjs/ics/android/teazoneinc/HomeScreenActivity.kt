package sk.upjs.ics.android.teazoneinc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
<<<<<<< Updated upstream
import androidx.core.view.isVisible
=======
>>>>>>> Stashed changes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.fragment_set_username2.*
import sk.upjs.ics.android.teazoneinc.Firebase.User.dataFirma
import sk.upjs.ics.android.teazoneinc.Firebase.User.dataUser
import sk.upjs.ics.android.teazoneinc.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapter

class HomeScreenActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapter = DbAdapter()
    lateinit var user : dataUser;
    lateinit var userFirma : dataFirma;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

<<<<<<< Updated upstream

        fragmentSetUserame.view?.visibility = View.INVISIBLE
        intent.extras?.let {
            fragmentSetUserame.view?.visibility = View.VISIBLE
        }

        btnSetUsername.setOnClickListener(View.OnClickListener {
            authAdapter.getUser()?.let {
                dbAdapter.setUsersUsername(it,tvSetUsername.text.toString())
            }
            fragmentSetUserame.view?.visibility = View.GONE
        })

    }

    fun setUser(){

    }

    fun setFirmaUser(){

    }


=======
        button_house.setOnClickListener(View.OnClickListener {
            button_house.setBackgroundColor(7)
        })
    }

>>>>>>> Stashed changes
}
