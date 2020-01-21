package sk.upjs.ics.android.teazoneinc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_login.*
import sk.upjs.ics.android.teazoneinc.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapter


class LoginActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapter = DbAdapter()


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
                authAdapter.login(email,password, this)
                authAdapter.getUser()?.let {
                    dbAdapter.setFirebaseUserToLocalUser(it)
                    intent = Intent(this, HomeScreenActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            }

        })

        btnCreateAccount.setOnClickListener(View.OnClickListener {
            intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        })
        
    }

}

