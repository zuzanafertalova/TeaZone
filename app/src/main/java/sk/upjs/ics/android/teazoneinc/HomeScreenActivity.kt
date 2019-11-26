package sk.upjs.ics.android.teazoneinc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home_screen.*
import sk.upjs.ics.android.teazoneinc.db.Data

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
            db.collection("Users").document(it)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null){
                        postID = document.getString("PostID")
                        postID?.let {id ->
                            data.getData(id.trim(), "Posts", EventListener { it, _ ->
                                tvDescription.text = it?.getString("Name")
                                Log.w("TU SOOOOOOMMM" ,id.trim())
                            })
                        }

                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("nedobre", "Error getting documents.", exception)
                }
        }
    }
}
