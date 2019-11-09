package sk.upjs.ics.android.teazoneinc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener(View.OnClickListener {

            val email: String = tvLoginUsername.text.toString()
            val password : String = pswdLoginPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please fill up all fields",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                            task ->
                        if (task.isSuccessful) {
                            Log.w("SUCCESFULL LOGIN", "signInWithEmail:success")
                            val user = auth.currentUser
                            intent = Intent(this,HomeScreenActivity::class.java)
                            startActivity(intent)
                        } else {
                            Log.w("UNSUCCESFULL LOGIN", "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "daco nedobre moj", Toast.LENGTH_SHORT).show()
                        }

                    }
            }

        })

        btnCreateAccount.setOnClickListener(View.OnClickListener {
            intent = Intent(this,SignupActivity::class.java)
            startActivity(intent)
        })
        
    }
}
