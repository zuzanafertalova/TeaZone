package sk.upjs.ics.android.teazoneinc.HomeScreenFragments.ProfileFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.fragment_followers.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.FollowersAdapter
import sk.upjs.ics.android.teazoneinc.R
import java.util.*
import kotlin.collections.ArrayList

class FollowersProfileFragment : Fragment() {

    val dbAdapterUser= DbAdapterUser()
    private var followersFollowingIDList = ArrayList<String>()
    private var followersFollowingList = ArrayList<String>()
    internal var adapter: FollowersAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        getFollowersList(EventListener{ list, _->
            followersFollowingIDList=list!!
            dbAdapterUser.getFollowersFollowingUsername(list!!, EventListener{usernameList,_->
                followersFollowingList=usernameList!!
                addFollower()
            })
        })

    }

    private fun addFollower(){
        setRecyclerData(followersFollowingList)
    }

    private fun setUpRecyclerView(){
        rvFollowers.layoutManager = LinearLayoutManager(context)
        adapter = FollowersAdapter()
        rvFollowers.adapter = adapter
    }

    fun getFollowersList(eventListener: EventListener<ArrayList<String>>){
            if (dbAdapterUser.getStatusOfLoggedUser().equals("User")){
                dbAdapterUser.getFollowingList(DbAdapterUser.userUser.docID!!, EventListener{ followingList, _->
                    eventListener.onEvent(followingList,null)
                })
            }
            else{
                dbAdapterUser.getFollowersList(DbAdapterUser.userFirma.docID!!, EventListener{ followersList, _->
                    eventListener.onEvent(followersList,null)
                })
            }
    }

    private fun setRecyclerData(followersFollowingList:ArrayList<String>){
        adapter?.setNewData(followersFollowingList)
    }
}