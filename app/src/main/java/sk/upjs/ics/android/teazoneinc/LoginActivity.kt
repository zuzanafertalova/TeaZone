package sk.upjs.ics.android.teazoneinc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.activity_login.*
import sk.upjs.ics.android.teazoneinc.db.authAdapter
import com.google.firebase.auth.FirebaseUser
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class LoginActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val authAdapter = authAdapter()

        auth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener(View.OnClickListener {

            val email: String = tvLoginUsername.text.toString()
            val password : String = pswdLoginPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please fill up all fields",Toast.LENGTH_SHORT).show()
            }
            else{
                authAdapter.login(email,password, this,EventListener { it, _ ->
                    val user = auth.currentUser
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

//        //toto je na to ze ked zapne apku hned ho prihlasi usera ked ostane prihlaseny
//    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        if (currentUser==null){
//
//        }
//        else{
//            intent = Intent(this,HomeScreenActivity::class.java)
//            startActivity(intent)
//        }
//    }

}

