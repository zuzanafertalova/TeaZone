package sk.upjs.ics.android.teazoneinc.Firebase.db

import android.provider.ContactsContract
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import sk.upjs.ics.android.teazoneinc.Firebase.User.dataFirma
import sk.upjs.ics.android.teazoneinc.Firebase.User.dataUser
import java.io.DataInput

class DbAdapter {
    private val db = FirebaseFirestore.getInstance()

    lateinit var userUser: dataUser
    lateinit var userFirma:dataFirma


    fun createUserInDatabase(collectionID : String, user : FirebaseUser, map: dataUser){
        db.collection(collectionID).document(user.uid).set(map)
            .addOnSuccessListener {
                Log.w("DB for user created","")
            }
            .addOnFailureListener { exception ->
                Log.w("Couldnt createDBforUser", exception)
            }
    }


    fun createUserUserInDatabase(user : FirebaseUser){
        val userData = dataUser(user.uid, user.email.toString(),"",0);
        createUserInDatabase("Users" , user, userData)
    }

    fun createFirmaUserInDatabase(user: FirebaseUser, ico : String){
        val firmaData = dataFirma(user.uid, user.email.toString(),"",0,0,ico);
        db.collection("FirmaUsers").document(user.uid).set(firmaData)
            .addOnSuccessListener {
                Log.w("DB for user created","")
            }
            .addOnFailureListener { exception ->
                Log.w("Couldnt createDBforUser", exception)
            }
    }

    fun setUsersUsername(user: FirebaseUser ,username : String){
        db.collection("Users").document(user.uid).update("username",username)
    }

    fun setFirebaseUserToLocalUser(user : FirebaseUser){
        val docRef = db.collection("Users").document(user.uid).get()
        if (docRef.isSuccessful){
            docRef.addOnSuccessListener {document ->
                document.getString("email")?.let {email ->
                    document.getString("username")?.let { username ->
                        document.getString("following")?.let { following->
                            userUser=dataUser(user.uid,email,username,following.toInt())
                            return@let userUser
                        }
                    }
                }
            }
        }
        else{
            val docRef = db.collection("FirmaUsers").document(user.uid).get()
                .addOnSuccessListener { document->
                    document.getString("email")?.let {email ->
                        document.getString("username")?.let { username ->
                            document.getString("following")?.let { following->
                                document.getString("followers")?.let { followers ->
                                    document.getString("ico")?.let { ico->
                                       userFirma = dataFirma(user.uid,email,username,following.toInt(),followers.toInt(),ico)
                                        return@let userFirma
                                    }
                                }
                            }
                        }
                    }
                }
        }

    }



}