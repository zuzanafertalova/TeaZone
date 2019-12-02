package sk.upjs.ics.android.teazoneinc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_signup.*
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapter

class SignupActivity : AppCompatActivity() {


    val authAdapter = sk.upjs.ics.android.teazoneinc.Firebase.authentication.AuthAdapter()
    val dbAdapter = DbAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        btnLoginToAccount.setOnClickListener(View.OnClickListener {
            finish()
        })

        btnSignup.setOnClickListener(View.OnClickListener {
            val email : String = tvSignupUsername.text.toString()
            val password : String = pswdSignupPswrd.text.toString()
            val confrimpassword: String = pswdSignupConfirmPswrd.text.toString()

            if (email.isEmpty() || password.isEmpty() || confrimpassword.isEmpty()){
                Toast.makeText(this,"Please fill up all fields",Toast.LENGTH_SHORT).show()
            }

            else {
                if (password.equals(confrimpassword)){
                    authAdapter.signup(email,password,this, EventListener { user, _ ->
                        val user = user
                        Toast.makeText(this,"Môžete sa prihlásiť",Toast.LENGTH_SHORT).show()
                        finish()
                        user?.let {user ->
                            dbAdapter.createUserUserInDatabase(user)
                        }
                    })

                }
                else{
                    Toast.makeText(this,"Your passwords does not match",Toast.LENGTH_SHORT).show()
                }

                }
            }

        )

        tvSignupFiremnyUcet.setOnClickListener(View.OnClickListener {
            intent = Intent(this,SignupFirmaActivity::class.java)
            startActivity(intent)
        })


    }

}
