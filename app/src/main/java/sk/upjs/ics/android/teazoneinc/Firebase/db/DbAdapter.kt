package sk.upjs.ics.android.teazoneinc.Firebase.db

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User

class DbAdapter {
    private val db = FirebaseFirestore.getInstance()


    fun getData(collections: String, document: String, eventListener: EventListener<DocumentSnapshot>) {
        db.collection(collections).document(document)
            .get()
            .addOnSuccessListener {
                eventListener.onEvent(it, null)
            }
            .addOnFailureListener { exception ->
                eventListener.onEvent(null, null)
                Log.w("TU SOMMMMMMM", "Error getting documents.", exception)
            }
    }



    fun createUserInDatabase(collectionID : String, user : FirebaseUser, map: HashMap<String,String>){
        var map = HashMap<String,String>()
        map.put("Email",user.email.toString())
        db.collection(collectionID).document(user.uid).set(map)
            .addOnSuccessListener {
                Log.w("DB for user created","")
            }
            .addOnFailureListener { exception ->
                Log.w("Couldnt createDBforUser", exception)
            }
    }

    fun createUserUserInDatabase(user : FirebaseUser){
        var map = HashMap<String,String>()
        map.put("Email",user.email.toString())
        createUserInDatabase("Users" , user, map)
    }

    fun createFirmaUserInDatabse(user: FirebaseUser, ico : String){
        var map = HashMap<String,String>()
        map.put("Email",user.email.toString())
        map.put("ICO",ico)
        createUserInDatabase("FirmaUsers", user, map)
    }



}