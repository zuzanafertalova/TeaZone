package sk.upjs.ics.android.teazoneinc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_signup_firma.*
import sk.upjs.ics.android.teazoneinc.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapter

class SignupFirmaActivity : AppCompatActivity() {

    private val authAdapter = AuthAdapter()
    private val dbAdapter = DbAdapter()

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
            } else {
                if (password.equals(confrimpassword)) {
                    authAdapter.signup(email, password, this, EventListener { user, _ ->
                        val user = user
                        Toast.makeText(this, "Môžete sa prihlásiť", Toast.LENGTH_SHORT).show()
                        finish()
                        user?.let { user ->
                            dbAdapter.createFirmaUserInDatabase(user, ico)
                        }
                    })

                } else {
                    Toast.makeText(this, "Your passwords does not match", Toast.LENGTH_SHORT).show()
                }

            }
        })

    }
}
