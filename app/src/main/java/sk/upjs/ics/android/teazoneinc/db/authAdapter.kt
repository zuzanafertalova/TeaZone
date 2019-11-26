package sk.upjs.ics.android.teazoneinc.db

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import sk.upjs.ics.android.teazoneinc.HomeScreenActivity


class authAdapter {

    private var auth : FirebaseAuth = FirebaseAuth.getInstance()


    fun login(email : String , password : String, activity: Activity, eventListener: EventListener<Boolean>){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) {
                    task ->
                if (task.isSuccessful) {
                   eventListener.onEvent(true, null)

                } else {
                    Log.w("UNSUCCESFULL LOGIN", "signInWithEmail:failure", task.exception)
                    eventListener.onEvent(false,null)
                }

            }
    }

}