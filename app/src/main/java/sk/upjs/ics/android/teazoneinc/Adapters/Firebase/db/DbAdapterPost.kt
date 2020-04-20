package sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataComment
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview

class DbAdapterPost {

    private val db = FirebaseFirestore.getInstance()
    private val storageAdapter = StorageAdapter()


    fun createPostInDB(map: DataPost){
        db.collection("Posts").add(map)
            .addOnSuccessListener {
                it.update("timeStamp",FieldValue.serverTimestamp())
                it.update("postID",it.id)
                Log.w("Doc for post created","DOCUMENT CREATED")
            }
            .addOnFailureListener { exception ->
                Log.w("not create doc for post", exception)
            }
    }

    fun setPostToPostClass(creatorID:String,
                           creatorUsername : String,
                           content:String,
                           likes:Int,
                           comments:Int,
                           creatorProfilePic : String,
                           picID: String?){
        var post = DataPost(creatorID,creatorUsername,content,likes,comments,creatorProfilePic)
        post.postPic = picID
        createPostInDB(post)
    }

    fun setPost(content:String, picID : String?){
        DbAdapterUser.userFirma?.let {
            setPostToPostClass(it.docID!!,it.username!!,content,0,0,it.profilePic!!, picID)
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

//    fun addComment(comment:String,postID:String){
//        db.collection("Posts").document(postID)
//            .update("comments",FieldValue.arrayUnion(comment))
//            .addOnSuccessListener {
//                Log.w("Podarilo sa komentnut","jes")
//            }
//            .addOnFailureListener{
//                Log.w("NEPODARILO SA KOMENT",it)
//            }
//    }

    fun createComment(postID: String ,creatorID: String, username : String, content: String){
        val comment = DataComment(creatorID,username,content)
        addComment2(postID, comment)
    }

    fun addComment2(postID: String, comment: DataComment){
        db.collection("Posts").document(postID)
            .collection("Comments").add(comment)
            .addOnSuccessListener {
                it.update("timeStamp",FieldValue.serverTimestamp())
            }
    }

    fun getPostsList(creatorID:String, eventListener: EventListener<ArrayList<DataPost>>){
        val postList = ArrayList<DataPost>()
        db.collection("Posts").whereEqualTo("creatorID",creatorID).get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    postList.add(setDocumentToDataClass(document))
                    eventListener.onEvent(postList,null)
                }
            }
    }

    private fun setDocumentToDataClass(document : DocumentSnapshot):DataPost{
        var post = DataPost()
        document.getString("content")?.let { post.content = it }
        document.getString("creatorID")?.let { post.creatorID = it }
        document.getLong("likesCount")?.let { post.likesCount = it.toInt() }
        document.getLong("commentsCount")?.let { post.commentsCount = it.toInt() }

        return post
    }

    fun getCommentList(postID : String, eventListener: EventListener<ArrayList<DataComment>>){
        val commentList = ArrayList<DataComment>()
        db.collection("Posts").document(postID)
            .collection("Comments").whereEqualTo("commentID",null).get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    commentList.add(setCommentToDataClass(document))
                    eventListener.onEvent(commentList,null)
                }
                Log.w("TU SOOOOOOM" , "no to len breakpoint taky")
            }
            .addOnFailureListener {
                Log.w("ERRORIKSAAA", it)
            }
    }

    private fun setCommentToDataClass(document: DocumentSnapshot): DataComment {
        var comment = DataComment()
        document.getString("content")?.let { comment.content = it }
        document.getString("creatorID")?.let { comment.creatorID = it }
        document.getString("creatorUsername")?.let { comment.creatorUsername = it}

        return comment
    }


}
