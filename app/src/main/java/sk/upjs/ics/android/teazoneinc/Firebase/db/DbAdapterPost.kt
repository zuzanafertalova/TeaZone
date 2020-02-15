package sk.upjs.ics.android.teazoneinc.Firebase.db

import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import sk.upjs.ics.android.teazoneinc.Firebase.DataHolderClasses.Post.DataPost

class DbAdapterPost {

    private val db = FirebaseFirestore.getInstance()


    fun createPostInDB(map: DataPost){
        db.collection("Posts").add(map)
            .addOnSuccessListener {
                Log.w("Doc for post created","DOCUMENT CREATED")
            }
            .addOnFailureListener { exception ->
                Log.w("not create doc for post", exception)
            }
    }

    fun setPostToPostClass(creatorID:String,content:String,likes:Int,comments:Int){
        val post = DataPost(creatorID,content,likes,comments)
        createPostInDB(post)
    }

    fun setPost(content:String){
        DbAdapterUser.userFirma.docID?.let {
            setPostToPostClass(it,content,0,0)
        }
    }

    fun addComment(comment:String){
        db.collection("Posts").document("I62spcdIAjGYKaGYC8T9")
            .update("comments",FieldValue.arrayUnion(comment))
            .addOnSuccessListener {
                Log.w("Podarilo sa komentnut","jes")
            }
            .addOnFailureListener{
                Log.w("NEPODARILO SA KOMENT",it)
            }
    }



}