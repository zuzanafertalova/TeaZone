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
                    authAdapter.signup(email,password,this, EventListener { user, _ ->
                        val user = user
                        Toast.makeText(this,"Úspešná registrácia",Toast.LENGTH_SHORT).show()
                        user?.let {user ->
                            dbAdapter.createUserUserInDatabase(user)
                            authAdapter.login(email,password, this,EventListener { currentUser, firestoreExeption ->
                                val user = currentUser
                                user?.let {
                                    intent = Intent(this,HomeScreenActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    intent.putExtra("fromSignUp",3)
                                    startActivity(intent)
                                }

                            })
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
