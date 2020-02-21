package sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_fragment_profile.view.*
import sk.upjs.ics.android.teazoneinc.Activities.setUsernameFragment
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataFirma
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataUser
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.ProfileFragment
import sk.upjs.ics.android.teazoneinc.R

class DbAdapterUser {

    private val db = FirebaseFirestore.getInstance()

    companion object{
        var userUser= DataUser()
        var userFirma= DataFirma()
        var decider = 0
    }

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
        val userData = DataUser(
            user.uid,
            user.email.toString(),
            "",
            0
        )
        createUserInDatabase("Users" , user, userData)

    }

    fun createFirmaUserInDatabase(user: FirebaseUser, ico : String){
        val firmaData = DataFirma(
            user.uid,
            user.email.toString(),
            "",
            0,
            0,
            ico
        )
        db.collection("FirmaUsers").document(user.uid).set(firmaData)
            .addOnSuccessListener {
                Log.w("DB for user created","")
            }
            .addOnFailureListener { exception ->
                Log.w("Couldnt createDBforUser", exception)
            }
    }

    fun setUsername(user: FirebaseUser ,username : String,eventListener: EventListener<String>){
        if (getStatusOfLoggedUser().equals("User")) {
            db.collection("Users").document(user.uid).update("username", username)
                .addOnSuccessListener {
                    userUser.username=username
                    eventListener.onEvent(username,null)
                }
        }
        else{
            db.collection("FirmaUsers").document(user.uid).update("username", username)
                .addOnSuccessListener {
                    userFirma.username=username
                    val fragment = ProfileFragment()
                    fragment.setUserToTextFields()
                }
        }
    }

    fun setFirebaseUserToLocalUser(user : FirebaseUser,getUser: GetUser){

        db.collection("Users").document(user.uid).get()
            .addOnSuccessListener {document ->
                if (document.exists()) {
                    setUserUserToLocalUser(document,user.uid)
                    getUser.onSuccess()
                }
                }
                .addOnFailureListener{
                     getUser.onFailure()
                    }


                db.collection("FirmaUsers").document(user.uid).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            setUserFirmaToLocalUser(document,user.uid)
                            getUser.onSuccess()
                        }
                    }
                    .addOnFailureListener{
                        getUser.onFailure()
                    }
    }

    fun setUserUserToLocalUser(document: DocumentSnapshot,userDocID:String) {
        userUser.docID=userDocID
        document.getString("email")?.let {userUser.email=it}
        document.getString("username")?.let { userUser.username=it}
        document.getLong("following")?.let {userUser.following=it.toInt()}
        decider=0
    }

    fun setUserFirmaToLocalUser(document: DocumentSnapshot,userDocID:String) {
        userFirma.docID=userDocID
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