package sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import sk.upjs.ics.android.teazoneinc.Adapters.AlgoliaSearchAdapter
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataFirma
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataUser

class DbAdapterUser {

    private val db = FirebaseFirestore.getInstance()
    private val algoliaSearchAdapter = AlgoliaSearchAdapter()

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


    private fun createUserInDatabase(collectionID : String, user : FirebaseUser, map: DataUser){
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
                    algoliaSearchAdapter.addUserUserToAlgolia(userUser)
                    eventListener.onEvent(username,null)
                }
        }
        else{
            db.collection("FirmaUsers").document(user.uid).update("username", username)
                .addOnSuccessListener {
                    userFirma.username=username
                    algoliaSearchAdapter.addFirmaUserToAlgolia(userFirma)
                    eventListener.onEvent(username,null)
                }
        }
    }

    fun setFirebaseUserToLocalUser(user : FirebaseUser, dbInterface: DbInterface){

        db.collection("Users").document(user.uid).get()
            .addOnSuccessListener {document ->
                if (document.exists()) {
                    setUserUserToLocalUser(document,user.uid)
                    dbInterface.onSuccess()
                }
                }
                .addOnFailureListener{
                     dbInterface.onFailure()
                    }


                db.collection("FirmaUsers").document(user.uid).get()
                    .addOnSuccessListener { document ->
                        val daco = document.getData()
                        if (document.exists()) {
                            setUserFirmaToLocalUser(document,user.uid)
                            dbInterface.onSuccess()
                        }
                    }
                    .addOnFailureListener{
                        dbInterface.onFailure()
                    }
    }

    private fun setUserUserToLocalUser(document: DocumentSnapshot,userDocID:String) {
        userUser.docID=userDocID
        document.getString("email")?.let {userUser.email=it}
        document.getString("username")?.let { userUser.username=it}
        document.getLong("following")?.let {userUser.following=it.toInt()}
        val list = document.get("followingIDs") as ArrayList<String>
        list?.let { userUser.followingIDs=it}
        decider=0
    }

    private fun setUserFirmaToLocalUser(document: DocumentSnapshot,userDocID:String) {
        userFirma.docID=userDocID
        document.getString("email")?.let { userFirma.email=it }
        document.getString("username")?.let { userFirma.username=it}
        document.getLong("following")?.let { userFirma.following=it.toInt()}
        document.getLong("followers")?.let { userFirma.followers=it.toInt()}
        document.getString("ico")?.let { userFirma.ICO=it}
        val followingIDs = document.get("followingIDs") as ArrayList<String>
        followingIDs?.let { userFirma.followingIDs=it }
        val followersIDs = document.get("followersIDs") as ArrayList<String>
        followersIDs?.let { userFirma.followersIDs=it }
        decider=1
    }

    fun setProfileData(docID:String, sendData: sendData){

        db.collection("Users").document(docID).get()
            .addOnSuccessListener {document ->
                if (document.exists()) {
                    sendData.send(setUserUserProfileData(docID ,document),null)
                }
            }
            .addOnFailureListener{
            }

        db.collection("FirmaUsers").document(docID).get()
            .addOnSuccessListener { document ->
                val daco = document.getData()
                if (document.exists()) {
                    sendData.send(null,setFirmaUserProfileData(docID ,document))
                }
            }
            .addOnFailureListener{
            }
    }

    private fun setUserUserProfileData(docID: String,document: DocumentSnapshot):DataUser{
        var user = DataUser()
        user.docID = docID
        document.getString("email")?.let {user.email=it}
        document.getString("username")?.let { user.username=it}
        document.getLong("following")?.let {user.following=it.toInt()}
        document.get("followersIDs")
        val list = document.get("followingIDs") as ArrayList<String>
        list?.let { user.followingIDs=it}
        return user
    }

    private fun setFirmaUserProfileData(docID: String,document: DocumentSnapshot):DataFirma{
        var firmaUser= DataFirma()
        firmaUser.docID = docID
        document.getString("email")?.let { firmaUser.email=it }
        document.getString("username")?.let { firmaUser.username=it}
        document.getLong("following")?.let { firmaUser.following=it.toInt()}
        document.getLong("followers")?.let { firmaUser.followers=it.toInt()}
        document.getString("ico")?.let { firmaUser.ICO=it}
        val list = document.get("followingIDs") as ArrayList<String>
        list?.let { firmaUser.followingIDs=it}
        val followersIDs = document.get("followersIDs") as ArrayList<String>
        followersIDs?.let { firmaUser.followersIDs=it }
        return firmaUser
    }

    fun addFollower(docID: String,followerID:String?){
        db.collection("FirmaUsers").document(docID)
            .update("followersIDs", FieldValue.arrayUnion(followerID))
            .addOnSuccessListener {
                Log.w("PODARILO PRIDAT","PRIDAT FOLOWERAAAAAAAAAAAAAAAA")
            }
            .addOnFailureListener{
                Log.w("NEPODARILO SA FOLLOW",it)
            }
        if (getStatusOfLoggedUser().equals("User")){
            db.collection("Users").document(followerID!!)
                .update("followingIDs", FieldValue.arrayUnion(docID))
                .addOnSuccessListener {
                    Log.w("PODARILO PRIDAT","PRIDAT FOLOWERAAAAAAAAAAAAAAAA")
                }
                .addOnFailureListener{
                    Log.w("NEPODARILO SA FOLLOW",it)
                }
        }
        else{
            db.collection("FirmaUsers").document(followerID!!)
                .update("followingIDs", FieldValue.arrayUnion(docID))
                .addOnSuccessListener {
                    Log.w("PODARILO PRIDAT","PRIDAT FOLOWERAAAAAAAAAAAAAAAA")
                }
                .addOnFailureListener{
                    Log.w("NEPODARILO SA FOLLOW",it)
                }
        }
    }

}

interface DbInterface{
    fun onSuccess()
    fun onFailure()
}

interface sendData{
    fun send(userUser: DataUser?,userFirma: DataFirma?)
}