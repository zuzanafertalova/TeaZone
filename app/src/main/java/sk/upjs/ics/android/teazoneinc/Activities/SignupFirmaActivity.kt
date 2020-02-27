package sk.upjs.ics.android.teazoneinc.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_signup_firma.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.R

class SignupFirmaActivity : AppCompatActivity() {

    private val authAdapter = AuthAdapter()
    private val dbAdapterUser = DbAdapterUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_firma)

        btnSignupFirma.setOnClickListener(View.OnClickListener {

            val email: String = tvSignupFirmaEmail.text.toString()
            val password: String = pswdSignupFirmaPswrd.text.toString()
            val confrimpassword: String = pswdSignupFirmaConfirmPswrd.text.toString()
            val ico: String = tvSignupFirmaICO.text.toString()


            if (email.isEmpty() || password.isEmpty() || confrimpassword.isEmpty()) {
                Toast.makeText(this, "Please fill up all fields", Toast.LENGTH_SHORT).show()

            }

            else {

                if (password.equals(confrimpassword)) {

                    authAdapter.signup(email, password, this, EventListener{user,_->
                        user?.let {
                            dbAdapterUser.createFirmaUserInDatabase(user, ico)
                            Toast.makeText(this, "Môžete sa prihlásiť", Toast.LENGTH_SHORT).show()
                            intent = Intent(this, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                    })

                }

                else { Toast.makeText(this, "Your passwords does not match", Toast.LENGTH_SHORT).show() }

            }
        })

        btnLoginToAccount2.setOnClickListener(View.OnClickListener { ///sa vratim na login
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })

    }
}
