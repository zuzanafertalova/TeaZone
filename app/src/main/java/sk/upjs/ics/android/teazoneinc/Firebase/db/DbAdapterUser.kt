package sk.upjs.ics.android.teazoneinc.Firebase.db

import android.service.autofill.UserData
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firestore.v1.Document
import com.google.firestore.v1.StructuredQuery
import sk.upjs.ics.android.teazoneinc.Firebase.User.DataFirma
import sk.upjs.ics.android.teazoneinc.Firebase.User.DataUser
import java.lang.ref.Reference

class DbAdapterUser {

    private val db = FirebaseFirestore.getInstance()

    var userUser= DataUser()
    var userFirma= DataFirma()
    var decider = 0

    fun getStatusOfLoggedUser():String {
        if (decider==0)return "User"
        else return "Firma"
    }

    fun getStatus(userID : String){

    }


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

    fun setFirebaseUserToLocalUser(user : FirebaseUser,getUser: GetUser){

        db.collection("Users").document(user.uid).get()
            .addOnSuccessListener {document ->
                        setUserUserToLocalUser(document)
                        getUser.onSuccess()
                    }.addOnFailureListener{
                     getUser.onFailure()
                    }


                db.collection("FirmaUsers").document(user.uid).get()
                    .addOnSuccessListener { document ->
                        setUserFirmaToLocalUser(document)
                        getUser.onSuccess()
                    }
                    .addOnFailureListener{
                        getUser.onFailure()
                    }

    }

    fun setUserUserToLocalUser(document: DocumentSnapshot) {

        document.getString("email")?.let {userUser.email=it}
        document.getString("username")?.let { userUser.username=it}
        document.getLong("following")?.let {userUser.following=it.toInt()}
        decider=0
    }

    fun setUserFirmaToLocalUser(document: DocumentSnapshot) {

        document.getString("email")?.let { userFirma.email=it }
        document.getString("username")?.let { userFirma.username=it}
        document.getLong("following")?.let { userFirma.following=it.toInt()}
        document.getLong("followers")?.let { userFirma.followers=it.toInt()}
        document.getString("ico")?.let { userFirma.ICO=it}
        decider=1
    }


}

interface GetUser{
    fun onSuccess()
    fun onFailure()
}