package sk.upjs.ics.android.teazoneinc.Adapters

import android.content.Context
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.EventListener
import de.hdodenhof.circleimageview.CircleImageView
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataComment
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost
import sk.upjs.ics.android.teazoneinc.Dialogs.BottomSheetComments
import sk.upjs.ics.android.teazoneinc.R.*
import java.text.SimpleDateFormat
import java.util.ArrayList

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val storageAdapter = StorageAdapter()
    val dbAdapterUser = DbAdapterUser()
    val dbAdapterPost = DbAdapterPost()
    val authAdapter = AuthAdapter()
    var isLikeButtonClicked: Boolean = false
    var isCommentButtonClicked: Boolean = false


    private var tvContentView: TextView = itemView.findViewById(id.tvContent)
    private var tvLikesCountView: TextView = itemView.findViewById(id.tvLikesCount)
    private var tvCommentCounts: TextView = itemView.findViewById(id.tvCommentCount)
    private var tvFirmaUsername: TextView = itemView.findViewById(id.tvFirmaUsername)
    private var ivPostPic: ImageView = itemView.findViewById(id.ivPostPic)
    private var ivProfilePic: CircleImageView = itemView.findViewById(id.ivProfilePic)
    private var likeButton: Button = itemView.findViewById(id.likeButton)
    private var commentButton: Button = itemView.findViewById(id.commentButton)
    private var tvTimeAdded : TextView = itemView.findViewById(id.casPridania)

    fun bind(post: DataPost, context: Context?, fragmentManager: FragmentManager) {

        ifLikeButtonClicked(context, post.likesIDs)
        setLikeButtonIfLiked(context, post.postID!!)
        setCommentButtonOnClick(context,post.postID!!, fragmentManager)

        tvContentView.text = post.content
        tvLikesCountView.text = post.likesCount.toString()
        tvCommentCounts.text = post.commentsCount.toString()
        tvFirmaUsername.text = post.creatorUsername
        val date = post.timeStamp
        val dateFormat = SimpleDateFormat("dd.MM.yyyy hh:mm")
        tvTimeAdded.text=dateFormat.format(date)
        storageAdapter.getProfilePic(post.creatorProfilePic!!, ivProfilePic)

        if (!post.postPic.equals(null)) {
            storageAdapter.getPostPic(post.postPic!!, ivPostPic)
        }
    }

    private fun ifLikeButtonClicked(context: Context?, likesIDs: ArrayList<String>){
        if(likesIDs.contains(authAdapter.currentUser?.uid)){
            isLikeButtonClicked = true
            likeButton.background = context?.let { it1 -> ContextCompat.getDrawable(it1, drawable.ic_like_onclick)}
        }
    }

    private fun setLikeButtonIfLiked(context: Context?, postID: String) {

        likeButton.setOnClickListener(View.OnClickListener {
            if (isLikeButtonClicked == false) {
                isLikeButtonClicked = true
                likeButton.background = context?.let { it1 -> ContextCompat.getDrawable(it1, drawable.ic_like_onclick)}
                dbAdapterPost.addLike(postID, authAdapter.currentUser?.uid)
            } else {
                isLikeButtonClicked = false
                likeButton.background = context?.let { it1 -> ContextCompat.getDrawable(it1, drawable.ic_like)}
                dbAdapterPost.removeLike(postID,authAdapter.currentUser?.uid)
            }
        })
    }

    private fun setCommentButtonOnClick(context: Context?,postID : String, fragmentManager: FragmentManager) {
                showBottomSheetComments(fragmentManager,postID)
    }

    fun showBottomSheetComments(fragmentManager: FragmentManager,postID: String) {
        commentButton.setOnClickListener(View.OnClickListener {
            val bottomSheet = BottomSheetComments(this,postID)
            bottomSheet.show(fragmentManager, "BottomSheetComments")
        })
    }



//    private fun btnAddCommentOnClick(postID:String, context: Context?){
//        btnAddComment.setOnClickListener(View.OnClickListener {
//            if (tiComment.text.isNotEmpty()){
//                if (dbAdapterUser.getStatusOfLoggedUser().equals("User")){
//                    dbAdapterPost.createComment(postID, authAdapter.currentUser!!.uid, DbAdapterUser.userUser.username!!,tiComment.text.toString())
//                    Toast.makeText(context,"Pridany" , Toast.LENGTH_SHORT).show()
//                }
//                else
//                {
//                    dbAdapterPost.createComment(postID, authAdapter.currentUser!!.uid, DbAdapterUser.userFirma.username!!,tiComment.text.toString())
//                    Toast.makeText(context,"Pridany" , Toast.LENGTH_SHORT).show()
//                }
//
//            }
//        })
//    }

}