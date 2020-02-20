package sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db

import android.util.Log
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost

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

    fun getPost(eventListener: EventListener<DataPost>){
        var post = DataPost()
        db.collection("Posts").document("xhTlJ9jKD4EQIVxcpQHg").get()
            .addOnSuccessListener {
                post.postID=it.id
                it.getString("creatorID")?.let { post.creatorID=it }
                it.getString("content")?.let { post.content=it }
                it.getLong("likesCount")?.toInt()?.let { post.likesCount=it }
                it.getLong("commentsCount")?.toInt()?.let { post.commentsCount=it }
                val list:ArrayList<String>? = it.get("comments") as ArrayList<String>
                list?.let { post.comments=it }
                eventListener.onEvent(post,null)
            }

    }

    fun getPostFirma(eventListener: EventListener<DataPost>){
        var post = DataPost()
        db.collection("Posts").document("I62spcdIAjGYKaGYC8T9").get()
            .addOnSuccessListener {
                post.postID=it.id
                it.getString("creatorID")?.let { post.creatorID=it }
                it.getString("content")?.let { post.content=it }
                it.getLong("likesCount")?.toInt()?.let { post.likesCount=it }
                it.getLong("commentsCount")?.toInt()?.let { post.commentsCount=it }
                val list:ArrayList<String>? = it.get("comments") as ArrayList<String>
                list?.let { post.comments=it }
                eventListener.onEvent(post,null)
            }

    }

    fun addComment(comment:String,postID:String){
        db.collection("Posts").document(postID)
            .update("comments",FieldValue.arrayUnion(comment))
            .addOnSuccessListener {
                Log.w("Podarilo sa komentnut","jes")
            }
            .addOnFailureListener{
                Log.w("NEPODARILO SA KOMENT",it)
            }
    }


}
