package sk.upjs.ics.android.teazoneinc.Firebase.db

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import sk.upjs.ics.android.teazoneinc.Firebase.User.DataFirma
import sk.upjs.ics.android.teazoneinc.Firebase.User.DataUser

class DbAdapter {
    private val db = FirebaseFirestore.getInstance()

    lateinit var userUser: DataUser
    lateinit var userFirma: DataFirma


    fun createUserInDatabase(collectionID : String, user : FirebaseUser, map: DataUser){
        db.collection(collectionID).document(user.uid).set(map)
            .addOnSuccessListener {
                Log.w("DB for user created","")
            }
            .addOnFailureListener { exception ->
                Log.w("Couldnt createDBforUser", exception)
            }
    }


    fun createUserUserInDatabase(user : FirebaseUser){
        val userData = DataUser(user.uid, user.email.toString(),"",0)
        createUserInDatabase("Users" , user, userData)
    }

    fun createFirmaUserInDatabase(user: FirebaseUser, ico : String){
        val firmaData = DataFirma(user.uid, user.email.toString(),"",0,0,ico)
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

        db.collection("Users").document(user.uid).get()
            .addOnSuccessListener {document ->
                        document.getString("email")?.let { email ->
                            document.getString("username")?.let { username ->
                                document.getLong("following")?.let {following ->
                                    userUser = DataUser(user.uid, email, username, following.toInt())
                                }
                            }
                        }
                    }


                db.collection("FirmaUsers").document(user.uid).get()
                    .addOnSuccessListener { document ->
                        document.getString("email")?.let { email ->
                            document.getString("username")?.let { username ->
                                document.getLong("following")?.let { following ->
                                    document.getLong("followers")?.let { followers ->
                                        document.getString("ico")?.let { ico ->
                                            userFirma = DataFirma(
                                                user.uid,
                                                email,
                                                username,
                                                following.toInt(),
                                                followers.toInt(),
                                                ico
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

    }



}