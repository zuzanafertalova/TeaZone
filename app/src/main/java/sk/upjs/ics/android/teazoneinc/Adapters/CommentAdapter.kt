package sk.upjs.ics.android.teazoneinc.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_item_comment.view.*
import sk.upjs.ics.android.teazoneinc.R


class CommentAdapter() : RecyclerView.Adapter<CommentAdapter.CommentBottomSheetHolder>() {

    private var commentList = ArrayList<String>()

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

    fun setNewData(commentList: ArrayList<String>) {
        this.commentList = commentList
        notifyDataSetChanged()
    }

    class CommentBottomSheetHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindData(comment: String) {
            itemView.tvObsahCommentu.text = comment
        }
    }

}