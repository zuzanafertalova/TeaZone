package sk.upjs.ics.android.teazoneinc.Adapters

import android.content.Context
import android.provider.ContactsContract
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.collection.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost


class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val storageAdapter = StorageAdapter()

    private var tvContentView: TextView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.tvContent)
    private var tvLikesCountView: TextView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.tvLikesCount)
    private var tvCommentCounts: TextView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.tvCommentCount)
    private var tvFirmaUsername : TextView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.tvFirmaUsername)
    private var ivPostPic : ImageView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.ivPostPic)


    fun bind(post: DataPost, context: Context?) {
        tvContentView.text = post.content
        tvLikesCountView.text = post.likesCount.toString()
        tvCommentCounts.text=post.commentsCount.toString()
        tvFirmaUsername.text=post.creatorUsername

        storageAdapter.getPostPic("ja.jpg", ivPostPic)
    }
}