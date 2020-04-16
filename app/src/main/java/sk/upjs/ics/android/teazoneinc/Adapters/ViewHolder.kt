package sk.upjs.ics.android.teazoneinc.Adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost
import sk.upjs.ics.android.teazoneinc.R

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val storageAdapter = StorageAdapter()
    var isLikeButtonClicked: Boolean = false
    val dbAdapterUser = DbAdapterUser()

    private var tvContentView: TextView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.tvContent)
    private var tvLikesCountView: TextView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.tvLikesCount)
    private var tvCommentCounts: TextView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.tvCommentCount)
    private var tvFirmaUsername : TextView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.tvFirmaUsername)
    private var ivPostPic : ImageView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.ivPostPic)
    private var ivProfilePic : CircleImageView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.ivProfilePic)
    private var likeButton : Button = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.likeButton)



    fun bind(post: DataPost, context: Context?) {

        setLikeButtonIfLiked(context)

        tvContentView.text = post.content
        tvLikesCountView.text = post.likesCount.toString()
        tvCommentCounts.text=post.commentsCount.toString()
        tvFirmaUsername.text=post.creatorUsername
        storageAdapter.getProfilePic(post.creatorProfilePic!!,ivProfilePic)

        if (!post.postPic.equals(null)){
            storageAdapter.getPostPic(post.postPic!!, ivPostPic)
        }

    }


    private fun setLikeButtonIfLiked(context: Context?) {
        likeButton.setOnClickListener(View.OnClickListener {
            if (isLikeButtonClicked == true) {
                isLikeButtonClicked = false
            //    dbAdapterUser.removeFollower(userFirma, docID, authAdapter.currentUser?.uid)
                likeButton.background = context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.ic_like ) }
            } else {
            //    dbAdapterUser.addFollower(docID, authAdapter.currentUser?.uid)
                isLikeButtonClicked = true
                likeButton.background = context?.let { it1 -> ContextCompat.getDrawable(it1, R.drawable.ic_like_onclick) }
            }
        })
    }

}