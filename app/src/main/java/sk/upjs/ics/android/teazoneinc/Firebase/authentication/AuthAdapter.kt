package sk.upjs.ics.android.teazoneinc.Firebase.authentication

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.HomeScreenActivity


class AuthAdapter {

    private val auth : FirebaseAuth = FirebaseAuth.getInstance()
    private val dbAdapterUser=DbAdapterUser()

    fun getFirebaseUser(): FirebaseUser? {
        return auth.currentUser
    }


    fun login(email : String , password : String, activity: Activity,eventListener: EventListener<FirebaseUser>){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) {
                    task ->
                if (task.isSuccessful) {
                    eventListener.onEvent(auth.currentUser,null)
                } else {
                    task.exception?.localizedMessage?.let {
                       Log.w("UNSUCCSFULL LOGIN" , it)
                        when(it){
                            "The password is invalid or the user does not have a password." -> Toast.makeText(activity , "Meno a heslo sa nezhoduju", Toast.LENGTH_SHORT).show()
                            "There is no user record corresponding to this identifier. The user may have been deleted." -> Toast.makeText(activity , "Meno a heslo sa nezhoduju", Toast.LENGTH_SHORT).show()
                            "An internal error has occurred. [ 7: ]" -> Toast.makeText(activity,"Skontrolujte si internetove pripojenie",Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }
    }


    fun signup(email : String , password: String, activity: Activity,eventListener: EventListener<FirebaseUser>){

        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(activity){ task ->
                if (task.isSuccessful) {
                    eventListener.onEvent(auth.currentUser,null)
                }

                else {
                    Log.w("POZRIME SA CO SA STALO", "createUserWithEmail:failure", task.exception)
                    task.exception?.localizedMessage?.let {
                        Log.w("UNSUCCSFULL LOGIN" , it)
                        when(it){
                            "The email address is already in use by another account." -> Toast.makeText(activity , "Na tento email už existuje účet", Toast.LENGTH_SHORT).show()
                            "The given password is invalid. [ Password should be at least 6 characters ]" -> Toast.makeText(activity , "Heslo musí byť dlhšie ako 6 znakov", Toast.LENGTH_SHORT).show()
                            "An internal error has occurred. [ 7: ]" -> Toast.makeText(activity,"Skontrolujte si internetové pripojenie",Toast.LENGTH_SHORT).show()
                            "The email address is badly formatted." -> Toast.makeText(activity,"Zadaná emailová adresa je nesprávna",Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
    }

}