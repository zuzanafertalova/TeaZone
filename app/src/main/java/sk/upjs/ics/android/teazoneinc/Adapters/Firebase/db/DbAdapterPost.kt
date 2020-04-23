package sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.list_item_comment.view.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataComment
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
                eventListener.onEvent(post,null)
            }

    }


    fun createComment(postID: String? ,creatorID: String?, username : String?, content: String?, eventListener: EventListener<DataComment>){
        val comment = DataComment(creatorID!!,username!!,content!!)
        db.collection("Posts").document(postID!!)
            .collection("Comments").add(comment)
            .addOnSuccessListener {
                it.update("timeStamp",FieldValue.serverTimestamp())
                comment.timeStamp=Date()
                eventListener.onEvent(comment, null)
            }
    }


//    fun addComment2(postID: String, comment: DataComment){
//        db.collection("Posts").document(postID)
//            .collection("Comments").add(comment)
//            .addOnSuccessListener {
//                it.update("timeStamp",FieldValue.serverTimestamp())
//            }
//    }

    fun addLike(postID: String,followerID:String?){
        db.collection("Posts").document(postID)
            .update("likesIDs", FieldValue.arrayUnion(followerID))
            .addOnSuccessListener {
                Log.w("PODARILO PRIDAT","PRIDAT LIKEEEEEEEEE")
            }
            .addOnFailureListener{
                Log.w("NEPODARILO SA LIKE",it)
            }
    }

    fun removeLike(postID: String,followerID: String?){
        db.collection("Posts").document(postID)
            .update("likesIDs",FieldValue.arrayRemove(followerID))
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
        document.getDate("timeStamp")?.let { comment.timeStamp=it }

        return comment
    }


    fun deletePosts(user: FirebaseUser,eventListener: EventListener<ArrayList<String>>){
        var kolkoBolo =0
        var postPicsList = ArrayList<String>()
        db.collection("Posts").whereEqualTo("creatorID",user.uid).get()
            .addOnSuccessListener { documents->
                val kolkoJe = documents.size()
                for (document in documents){
                    getPosPicID(document, EventListener{postPic,_->
                        postPic.let {
                            if (postPic==null){
                                deletePost(document.id, EventListener{staloSa, _->
                                    if (staloSa!!){
                                        kolkoBolo++
                                        if (kolkoBolo==kolkoJe){
                                            eventListener.onEvent(postPicsList,null)
                                        }
                                    }
                                })
                            }
                            else{
                                postPicsList.add(postPic)
                                deletePost(document.id, EventListener{staloSa, _->
                                    if (staloSa!!){
                                        kolkoBolo++
                                        if (kolkoBolo==kolkoJe){
                                            eventListener.onEvent(postPicsList,null)
                                        }
                                    }
                                })
                            }
                        }
                    })

                }
            }
//        var postPics = ArrayList<String>()
//        db.collection("Posts").whereEqualTo("creatorID",user.uid).get()
//            .addOnSuccessListener{documents ->
//                for (document in documents){
//                    val commentRef = db.collection("Posts").document(document.id).collection("Comments")
//                    commentRef.get()
//                        .addOnSuccessListener {
//                            deleteComments(it,commentRef)
//                        }
//                        .addOnFailureListener {
//                            Log.w("tu som",it)
//                        }
//                    if (!document.getString("postPic").isNullOrEmpty()){
//                        postPics.add(document.getString("postPiC")!!)
//                    }
//                    db.collection("Posts").document(document.id).delete()
//                }
//                if (!postPics.isEmpty()){
//                    storageAdapter.deletePostPics(postPics)
//                }
//            }
    }

    private fun getPosPicID(document: QueryDocumentSnapshot,eventListener: EventListener<String>){
        db.collection("Posts").document(document.id).get()
            .addOnSuccessListener {
                val postPic = it.getString("postPic")
                eventListener.onEvent(postPic,null)
            }
    }

    private fun deletePost(documentID:String,eventListener: EventListener<Boolean>){
        db.collection("Posts").document(documentID).delete()
            .addOnSuccessListener {
                Log.w("tu som","to je breakPoinit")
                eventListener.onEvent(true,null)
            }
    }

//    fun middleStepGetPostsDocs(user: FirebaseUser, eventListener: EventListener<QuerySnapshot>){
//        db.collection("Posts").whereEqualTo("creatorID",user.uid).get()
//            .addOnSuccessListener{documents ->
//                eventListener.onEvent(documents,null)
//            }
//    }
//
//    fun middleStepGetCommentsDocs(document: DocumentSnapshot, eventListener: EventListener<QuerySnapshot>){
//        db.collection("Posts").document(document.id).collection("Comments").get()
//            .addOnSuccessListener {
//                eventListener.onEvent(it,null)
//            }
//    }
//
//    fun deleteComments(comments: QuerySnapshot, post:QueryDocumentSnapshot,eventListener: EventListener<Boolean>){
//        for (comment in comments){
//            db.collection("Posts").document(post.id).collection("Comments").document(comment.id).delete()
//                .addOnSuccessListener {
//                    eventListener.onEvent(true,null)
//                }
//        }
//    }

//    fun deletePost(){
//
//    }


}
