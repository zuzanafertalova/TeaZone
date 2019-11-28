package sk.upjs.ics.android.teazoneinc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_login.*
import sk.upjs.ics.android.teazoneinc.Firebase.authentication.authAdapter


class LoginActivity : AppCompatActivity() {

    val authAdapter = authAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        btnLogin.setOnClickListener(View.OnClickListener {

            val email: String = tvLoginUsername.text.toString()
            val password : String = pswdLoginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please fill up all fields",Toast.LENGTH_SHORT).show()
            }
            else{
                authAdapter.login(email,password, this,EventListener { it, _ ->
                    val user = it
                    intent = Intent(this,HomeScreenActivity::class.java)
                    startActivity(intent)
                })
            }

        })

        btnCreateAccount.setOnClickListener(View.OnClickListener {
            intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        })
        
    }

}

