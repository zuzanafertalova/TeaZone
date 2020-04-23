package sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import sk.upjs.ics.android.teazoneinc.Adapters.AlgoliaSearchAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataFirma
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataUser

class DbAdapterUser {

    private val db = FirebaseFirestore.getInstance()
    private val dbAdapterPost=DbAdapterPost()
    private val authAdapter = AuthAdapter()
    private val storageAdapter=StorageAdapter()
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
            0,
            0,
            "default_user.png"
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
            0,
            ico,
            "default_firma.png",
            ""
        )
        db.collection("FirmaUsers").document(user.uid).set(firmaData)
            .addOnSuccessListener {
                Log.w("DB for user created","")
            }
            .addOnFailureListener { exception ->
                Log.w("Couldnt createDBforUser", exception)
            }
    }

    fun setFirstSettingsUser(user: FirebaseUser, username : String, eventListener: EventListener<String>){
            db.collection("Users").document(user.uid).update("username", username)
                .addOnSuccessListener {
                    userUser.username=username
                    algoliaSearchAdapter.addUserUserToAlgolia(userUser)
                    eventListener.onEvent(username,null)
                }
    }

    fun setFirstSettingsFirma(user: FirebaseUser, username : String, typPodniku: String, eventListener: EventListener<String>){
        val docRef = db.collection("FirmaUsers").document(user.uid)
        db.runBatch{
            it.update(docRef,"username", username)
            it.update(docRef,"typPodniku",typPodniku)
            }.addOnSuccessListener {

                userFirma.username=username
                userFirma.typPodniku=typPodniku
                algoliaSearchAdapter.addFirmaUserToAlgolia(userFirma)
                eventListener.onEvent(username,null)
            }
//            db.collection("FirmaUsers").document(user.uid).update()
//                .addOnSuccessListener {
//                    userFirma.username=username
//                    eventListener.onEvent(username,null)
//                }
//            db.collection("FirmaUsers").document(user.uid).update("typPodniku", typPodniku)
//                .addOnSuccessListener {
//                    userFirma.username=username
//                    algoliaSearchAdapter.addFirmaUserToAlgolia(userFirma)
//                    eventListener.onEvent(username,null)
//                }
    }

    fun changeUsername(user: FirebaseUser, username: String){
        if (getStatusOfLoggedUser().equals("User")) {
            db.collection("Users").document(user.uid).update("username", username)
                .addOnSuccessListener {
                    algoliaSearchAdapter.updateUsername(username,user.uid)
                }
        }
        else{
            db.collection("FirmaUsers").document(user.uid).update("username", username)
                .addOnSuccessListener {
                    algoliaSearchAdapter.updateUsername(username,user.uid)
                }
        }
    }


    fun setTypPodniku(user: FirebaseUser ,typPodniku: String) {

        db.collection("FirmaUsers").document(user.uid).update("typPodniku", typPodniku)
            .addOnSuccessListener {
                userFirma.typPodniku = typPodniku
            }
    }

    fun setDescription(user:FirebaseUser,description:String){
        db.collection("FirmaUsers").document(user.uid).update("description", description)
            .addOnSuccessListener {
                userFirma.description =description
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
        document.getString("profilePic")?.let { userUser.profilePic=it }
        val list = document.get("followingIDs") as ArrayList<String>
        list?.let { userUser.followingIDs=it}
        decider=0
    }

    private fun setUserFirmaToLocalUser(document: DocumentSnapshot,userDocID:String) {
        userFirma.docID=userDocID
        document.getString("email")?.let { userFirma.email=it}
        document.getString("username")?.let { userFirma.username=it}
        document.getLong("following")?.let { userFirma.following=it.toInt()}
        document.getLong("followers")?.let { userFirma.followers=it.toInt()}
        document.getString("ico")?.let { userFirma.ICO=it}
        document.getString("typPodniku")?.let{ userFirma.typPodniku=it}
        document.getString("profilePic")?.let { userFirma.profilePic=it}
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
        document.getString("profilePic")?.let { user.profilePic=it }
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
        document.getString("typPodniku")?.let{ firmaUser.typPodniku=it}
        document.getString("profilePic")?.let { firmaUser.profilePic=it }
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

    fun removeFollower(user:DataFirma ,docID: String,followerID: String?){
        user.followersIDs.remove(followerID)
        db.collection("FirmaUsers").document(docID)
            .update("followersIDs",FieldValue.arrayRemove(followerID))
        if (getStatusOfLoggedUser().equals("User")){
            userUser.followingIDs.remove(docID)
            db.collection("Users").document(followerID!!)
                .update("followingIDs", userUser.followingIDs)
        }
        else {
            userFirma.followingIDs.remove(docID)
            db.collection("FirmaUsers").document(followerID!!)
                .update("followingIDs", userFirma.followingIDs)
        }
    }

    fun updateProfilePic(docID:String, profilePic:String){
        if (getStatusOfLoggedUser().equals("User")){
            db.collection("Users").document(docID).update("profilePic",profilePic)
        }
        else{
            db.collection("FirmaUsers").document(docID).update("profilePic",profilePic)
        }

    }

    fun deleteUserFromDatabase(user: FirebaseUser,eventListener: EventListener<Boolean>){
        if (getStatusOfLoggedUser().equals("User")){
            deleteUser(user)
        }
        else{
            deleteFirma(user, EventListener{spraviloSa,_->
                if(spraviloSa!!){
                    eventListener.onEvent(true,null)
                }
            })
        }
    }

    private fun deleteUser(user: FirebaseUser){

    }

    private fun deleteFirma(user: FirebaseUser, eventListener: EventListener<Boolean>){
//        dbAdapterPost.middleStepGetPostsDocs(user, EventListener{ posts, _->
//            posts.let {posts->
//                for (post in posts!!){
//                    dbAdapterPost.middleStepGetCommentsDocs(post, EventListener{ comments, _->
//                        comments.let {
//                            dbAdapterPost.deleteComments(it!!,post, EventListener{spraviloSa,_->
//                                spraviloSa.let {
//                                    if (it!!){
//
//                                    }
//                                }
//                            })
//                        }
//                    })
//
//                }
//
//            }
//        })
        dbAdapterPost.deletePosts(user, EventListener{staloSa,_->
            staloSa.let {
                if (staloSa==null){
                    getProfilePicFirma(user, EventListener{profilePic,_->
                        if (profilePic==null){
                            deleteUserDoc(user,"FirmaUsers", EventListener{staloSa,_->
                                if (staloSa!!){
                                    authAdapter.deleteUser(EventListener{staloSa, _->
                                        if(staloSa!!){
                                            eventListener.onEvent(true,null)
                                        }
                                    })
                                }
                            })
                        }
                        else{
                            storageAdapter.deleteProfilePic(profilePic)
                            deleteUserDoc(user,"FirmaUsers", EventListener{staloSa,_->
                                if (staloSa!!){
                                    authAdapter.deleteUser(EventListener{staloSa, _->
                                        if(staloSa!!){
                                            eventListener.onEvent(true,null)
                                        }
                                    })
                                }
                            })
                        }
                    })
                }
                else{
                storageAdapter.deletePostPics(it!!, EventListener{staloSa, _->
                    if (staloSa!!){
                        getProfilePicFirma(user, EventListener{profilePic,_->
                            if (profilePic==null){
                                deleteUserDoc(user,"FirmaUsers", EventListener{staloSa,_->
                                    if (staloSa!!){
                                        authAdapter.deleteUser(EventListener{staloSa, _->
                                            if(staloSa!!){
                                                eventListener.onEvent(true,null)
                                            }
                                        })
                                    }
                                })
                            }
                            else{
                                storageAdapter.deleteProfilePic(profilePic)
                                deleteUserDoc(user,"FirmaUsers", EventListener{staloSa,_->
                                    if (staloSa!!){
                                        authAdapter.deleteUser(EventListener{staloSa, _->
                                            if(staloSa!!){
                                                eventListener.onEvent(true,null)
                                            }
                                        })
                                    }
                                })
                            }
                        })
                    }
                })
                }
            }
        })
//        db.collection("FirmaUsers").document(user.uid).get()
//            .addOnSuccessListener {
//                if (!it.getString("profilePic")!!.equals("deafualt_firma.png")){
//                    storageAdapter.deleteProfilePic(it.getString("profilePic")!!)
//                }
//                db.collection("FirmaUsers").document(user.uid).delete()
//                    .addOnSuccessListener {
//                        Log.w("odarilo","PODAROLO SAAAA")
//                    }
//                    .addOnFailureListener {
//                        Log.w("tu jest",it)
//                    }
//            }
//        authAdapter.deleteUser()
    }

    private fun getProfilePicFirma(user: FirebaseUser,eventListener: EventListener<String>){
        db.collection("FirmaUsers").document(user.uid).get()
            .addOnSuccessListener {
                val profilePic = it.getString("profilePic")
                if(profilePic!!.equals("default_firma.png")){
                    eventListener.onEvent(null,null)
                }
                else{
                    eventListener.onEvent(profilePic,null)
                }
            }
    }

    private fun deleteUserDoc(user: FirebaseUser, collection: String, eventListener: EventListener<Boolean>){
        db.collection(collection).document(user.uid).delete()
            .addOnSuccessListener {
                eventListener.onEvent(true,null)
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