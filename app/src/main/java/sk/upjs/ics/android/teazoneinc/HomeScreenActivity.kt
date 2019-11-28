package sk.upjs.ics.android.teazoneinc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_screen.*
import sk.upjs.ics.android.teazoneinc.Firebase.db.Data

class HomeScreenActivity : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    var postID : String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        val userID: String? = auth.currentUser?.uid

        val data = Data()

        userID?.let {
            data.getData("Users", it, EventListener { document, firebaseFirestoreException ->
                postID = document?.getString("PostID")
                postID?.let {id ->
                    data.getData("Posts", id.trim(), EventListener { it, _ ->
                        tvDescription.text = it?.getString("Name")
                    })
                }
            })

        }
    }
}
