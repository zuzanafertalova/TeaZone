package sk.upjs.ics.android.teazoneinc.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview
import sk.upjs.ics.android.teazoneinc.Dialogs.BottomSheetComments
import sk.upjs.ics.android.teazoneinc.Dialogs.BottomSheetCommentsPosts
import sk.upjs.ics.android.teazoneinc.R
import java.text.SimpleDateFormat

class PostsAdapter(context: Context?,fragmentManager: FragmentManager) : RecyclerView.Adapter<PostsAdapter.PostsFragmentHolder>() {

    val context = context
    val fragmentManager=fragmentManager
    var postList = ArrayList<DataPost>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsFragmentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_posts_profile, parent, false)
        return PostsFragmentHolder(view)
    }
    override fun getItemCount(): Int {
        return postList.size
    }
    override fun onBindViewHolder(holder: PostsFragmentHolder, position: Int) {
        holder.bindData(postList[position],context,fragmentManager)
    }
    fun setNewData(postList: ArrayList<DataPost>) {
        this.postList = postList
        notifyDataSetChanged()
    }

    class PostsFragmentHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val storageAdapter = StorageAdapter()
        val dbAdapterUser = DbAdapterUser()
        val dbAdapterPost = DbAdapterPost()
        val authAdapter = AuthAdapter()



        private var tvContentView: TextView = itemView.findViewById(R.id.tvContent)
        private var tvLikesCountView: TextView = itemView.findViewById(R.id.tvLikesCount)
        private var tvCommentCounts: TextView = itemView.findViewById(R.id.tvCommentCount)
        private var tvFirmaUsername: TextView = itemView.findViewById(R.id.tvFirmaUsername)
        private var ivPostPic: ImageView = itemView.findViewById(R.id.ivPostPic)
        private var ivProfilePic: CircleImageView = itemView.findViewById(R.id.ivProfilePic)
        private var commentButton: Button = itemView.findViewById(R.id.commentButton)
        private var tvTimeAdded : TextView = itemView.findViewById(R.id.casPridania)

        fun bindData(post: DataPost, context: Context?, fragmentManager: FragmentManager) {

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
                storageAdapter.getPostPicForOtherThanHome(post.postPic!!, ivPostPic)
            }
        }

        private fun setCommentButtonOnClick(context: Context?,postID : String, fragmentManager: FragmentManager) {
            showBottomSheetComments(fragmentManager,postID)
        }

        fun showBottomSheetComments(fragmentManager: FragmentManager,postID: String) {
            commentButton.setOnClickListener(View.OnClickListener {
                val bottomSheet = BottomSheetCommentsPosts(this,postID)
                bottomSheet.show(fragmentManager, "BottomSheetComments")
            })
        }
    }


}