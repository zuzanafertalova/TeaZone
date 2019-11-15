package sk.upjs.ics.android.teazoneinc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        auth = FirebaseAuth.getInstance()

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
                    auth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this){
                                task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                Toast.makeText(this,"you have been Signed In. Please Log In",Toast.LENGTH_LONG).show()
                                finish()
                            }
                            else {
                                Toast.makeText(this, "An error occur, please try aggain later", Toast.LENGTH_SHORT).show()
                                Log.w("POZRIME SA CO SA STALO", "createUserWithEmail:failure", task.exception)
                            }

                        }
                }
                else{
                    Toast.makeText(this,"Your passwords does not match",Toast.LENGTH_SHORT).show()
                }
            }

        })


    }


}