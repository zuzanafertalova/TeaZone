package sk.upjs.ics.android.teazoneinc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_signup.*
import sk.upjs.ics.android.teazoneinc.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapter

class SignupActivity : AppCompatActivity() {


    val authAdapter = AuthAdapter()
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
                Toast.makeText(this,"Vyplňte všetky polia",Toast.LENGTH_SHORT).show()
            }

            else {
                if (password.equals(confrimpassword)){
                    authAdapter.signup(email,password,this, EventListener { currentUser, _ ->
                        Toast.makeText(this,"Úspešná registrácia, môžete sa prihlásiť",Toast.LENGTH_SHORT).show()
                        currentUser?.let {
                            dbAdapter.createUserUserInDatabase(it)
                            finish()
                        }
                    })

                }
                else{
                    Toast.makeText(this,"Heslá sa nezhodujú",Toast.LENGTH_SHORT).show()
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
