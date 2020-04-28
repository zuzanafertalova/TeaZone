package sk.upjs.ics.android.teazoneinc.HomeScreenFragments.ProfileFragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_home_screen.*
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.fragment_reviews.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.PostViewHolder
import sk.upjs.ics.android.teazoneinc.Adapters.PostsAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.ReviewsAdapter
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview

import sk.upjs.ics.android.teazoneinc.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class PostsFragment : Fragment() {

    val dbAdapterPost = DbAdapterPost()
    internal var  adapter: PostsAdapter? = null
    var postList = ArrayList<DataPost>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbAdapterPost.getPostsList(DbAdapterUser.userFirma.docID!!, EventListener{ list, _ ->
            list?.let {
                postList = it
                setUpRecyclerView()
                addReview()
            }

        })



    }

    private fun addReview(){
        setRecyclerData(postList)
    }

    private fun setUpRecyclerView() {
        rvPosts.layoutManager = LinearLayoutManager(context)
        adapter = PostsAdapter(context,fragmentManager!!)
        rvPosts.adapter = adapter
    }


    private fun setRecyclerData(postsList:ArrayList<DataPost>){
        adapter?.setNewData(postsList)
    }


}


