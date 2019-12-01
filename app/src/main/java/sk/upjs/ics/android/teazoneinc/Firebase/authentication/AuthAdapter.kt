package sk.upjs.ics.android.teazoneinc.Firebase.authentication

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import sk.upjs.ics.android.teazoneinc.HomeScreenActivity


class AuthAdapter {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()


    fun login(email : String , password : String, activity: Activity, eventListener: EventListener<FirebaseUser>){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) {
                    task ->
                if (task.isSuccessful) {
                   eventListener.onEvent(auth.currentUser, null)

                } else {
                    Log.w("UNSUCCESFULL LOGIN", "signInWithEmail:failure", task.exception)
                    eventListener.onEvent(null,null)
                }

            }
    }


    fun signup(email : String , password: String, activity: Activity,eventListener: EventListener<FirebaseUser>){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(activity){
                    task ->
                if (task.isSuccessful) {
                    eventListener.onEvent(auth.currentUser,null)
                }
                else {
                    Log.w("POZRIME SA CO SA STALO", "createUserWithEmail:failure", task.exception)
                }

            }
    }

}