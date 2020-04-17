package sk.upjs.ics.android.teazoneinc.Adapters

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost
import sk.upjs.ics.android.teazoneinc.R
import sk.upjs.ics.android.teazoneinc.R.*

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val storageAdapter = StorageAdapter()
    val dbAdapterUser = DbAdapterUser()
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
    private var tiCommentLayout: LinearLayout = itemView.findViewById(id.tiCommentLayout)
    private var rvComments : RecyclerView = itemView.findViewById(id.rvComments)

    fun bind(post: DataPost, context: Context?) {

        setLikeButtonIfLiked(context)
        setCommentButtonOnClick(context)

        tvContentView.text = post.content
        tvLikesCountView.text = post.likesCount.toString()
        tvCommentCounts.text = post.commentsCount.toString()
        tvFirmaUsername.text = post.creatorUsername
        storageAdapter.getProfilePic(post.creatorProfilePic!!, ivProfilePic)

        if (!post.postPic.equals(null)) {
            storageAdapter.getPostPic(post.postPic!!, ivPostPic)
        }
    }

    private fun setLikeButtonIfLiked(context: Context?) {

        likeButton.setOnClickListener(View.OnClickListener {
            if (isLikeButtonClicked == true) {
                isLikeButtonClicked = false
                //    dbAdapterUser.removeFollower(userFirma, docID, authAdapter.currentUser?.uid)
                likeButton.background = context?.let { it1 -> ContextCompat.getDrawable(it1, drawable.ic_like)}
            } else {
                //    dbAdapterUser.addFollower(docID, authAdapter.currentUser?.uid)
                isLikeButtonClicked = true
                likeButton.background = context?.let { it1 -> ContextCompat.getDrawable(it1, drawable.ic_like_onclick)}
            }
        })
    }

    private fun setCommentButtonOnClick(context: Context?) {
        commentButton.setOnClickListener(View.OnClickListener {
            if (isCommentButtonClicked == true) {
                isCommentButtonClicked = false
                tiCommentLayout.setVisibility(View.VISIBLE)
                rvComments.setVisibility(View.VISIBLE)
                commentButton.background = context?.let {it1 -> ContextCompat.getDrawable(it1, drawable.ic_comment)}
            } else {
                isCommentButtonClicked = true
                tiCommentLayout.setVisibility(View.GONE)
                rvComments.setVisibility(View.GONE)
                commentButton.background = context?.let {it1 -> ContextCompat.getDrawable(it1, drawable.ic_comment_onclick)}

            }
        })
    }

}