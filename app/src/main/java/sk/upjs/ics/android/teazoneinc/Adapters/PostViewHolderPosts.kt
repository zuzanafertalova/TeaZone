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
import sk.upjs.ics.android.teazoneinc.Dialogs.BottomSheetComments
import sk.upjs.ics.android.teazoneinc.Dialogs.BottomSheetCommentsPosts
import sk.upjs.ics.android.teazoneinc.R
import java.text.SimpleDateFormat
import java.util.ArrayList

class PostsAdapterFromSearch(context: Context?,fragmentManager: FragmentManager) : RecyclerView.Adapter<PostViewHolder>() {

    val context = context
    val fragmentManager=fragmentManager
    var postList = ArrayList<DataPost>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_posts_profile, parent, false)
        return PostViewHolder(view)
    }
    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postList[position],context,fragmentManager)
    }

    fun setNewData(postList: ArrayList<DataPost>) {
        this.postList = postList
        notifyDataSetChanged()
    }


}