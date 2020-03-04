package sk.upjs.ics.android.teazoneinc.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_login.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbInterface
import sk.upjs.ics.android.teazoneinc.R


class LoginActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btnLogin.setOnClickListener(View.OnClickListener {

            val email: String = tvLoginUsername.text.toString()
            val password : String = pswdLoginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Vyplňte všetky polia",Toast.LENGTH_SHORT).show()
            }
            else{
                authAdapter.login(email,password, this, EventListener{user,_ ->
                    user?.let {
                        setUserToLocal(it)
                    }
                })
            }

        })



        btnCreateAccount.setOnClickListener(View.OnClickListener {
            intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        })
        
    }

    override fun onStart() {
        super.onStart()
        val currentUser = authAdapter.currentUser
        if (currentUser==null){}
        else { setUserToLocal(currentUser) }
    }

    fun setUserToLocal(user:FirebaseUser){
        dbAdapterUser.setFirebaseUserToLocalUser(user,object : DbInterface{
            override fun onSuccess() {
                startHomeScreenActivity()
            }
            override fun onFailure() {

            }
        })
    }

    fun startHomeScreenActivity(){
        intent = Intent(this, HomeScreenActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}

