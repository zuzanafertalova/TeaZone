package sk.upjs.ics.android.teazoneinc.Adapters

import android.provider.ContactsContract
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.collection.R
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost


class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var tvContentView: TextView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.tvContent)
    private var tvLikesCountView: TextView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.tvLikesCount)
    private var tvCommentCounts: TextView = itemView.findViewById(sk.upjs.ics.android.teazoneinc.R.id.tvCommentCount)


    fun bind(post: DataPost) {
        tvContentView.text = post.content
        tvLikesCountView.text = post.likesCount.toString()
        tvCommentCounts.text=post.commentsCount.toString()
    }
}