package sk.upjs.ics.android.teazoneinc.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_comment.view.*
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataComment
import sk.upjs.ics.android.teazoneinc.R
import java.text.SimpleDateFormat


class CommentAdapter() : RecyclerView.Adapter<CommentAdapter.CommentBottomSheetHolder>() {

    private var commentList = ArrayList<DataComment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentBottomSheetHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_comment, parent, false)
        return CommentBottomSheetHolder(view)
    }
    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentBottomSheetHolder, position: Int) {
        holder.bindData(commentList[position])
    }

    fun setNewData(commentList: ArrayList<DataComment>) {
        this.commentList = commentList
        notifyDataSetChanged()
    }

    class CommentBottomSheetHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindData(comment: DataComment) {
            itemView.tvCommentContent.text = comment.content
            itemView.tvCommentUsername.text=comment.creatorUsername
            val dateFormat = SimpleDateFormat("dd.MM.yyyy hh:mm")
            itemView.tvCommentDateTime.text=dateFormat.format(comment.timeStamp)
        }
    }

}