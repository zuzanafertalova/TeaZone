package sk.upjs.ics.android.teazoneinc.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseUser
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbInterface
import sk.upjs.ics.android.teazoneinc.R

class OpeningActivity : AppCompatActivity() {

    val authAdapter=AuthAdapter()
    val dbAdapterUser = DbAdapterUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opening)
    }


    override fun onStart() {
        super.onStart()
        val currentUser = authAdapter.currentUser
        if (currentUser==null){
            startLoginScreenActivity()
        }
        else {
            setUserToLocal(currentUser)
        }
    }

    fun setUserToLocal(user: FirebaseUser){
        dbAdapterUser.setFirebaseUserToLocalUser(user,object : DbInterface {
            override fun onSuccess() {
                startHomeScreenActivity()
            }
            override fun onFailure() {
                startLoginScreenActivity()
            }
        })
    }

    fun startLoginScreenActivity(){
        intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    fun startHomeScreenActivity(){
        intent = Intent(this, HomeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}
