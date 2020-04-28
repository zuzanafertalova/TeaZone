package sk.upjs.ics.android.teazoneinc.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_followers.view.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.R

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersFragmentHolder>() {

    var followersFollowingList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersFragmentHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_followers, parent, false)
        return FollowersFragmentHolder(view)
    }

    override fun getItemCount(): Int {
        return followersFollowingList.size
    }

    override fun onBindViewHolder(holder: FollowersFragmentHolder, position: Int) {
        holder.bindData(followersFollowingList[position])
    }

    fun setNewData(followersList : ArrayList<String>){
        this.followersFollowingList = followersList
        notifyDataSetChanged()
    }

    class FollowersFragmentHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var dbAdapterUser = DbAdapterUser()
        fun  bindData(s: String) {
            itemView.tvMenoUseraFollowers.text = s
        }
    }

}