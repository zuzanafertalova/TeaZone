package sk.upjs.ics.android.teazoneinc.HomeScreenFragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_home_screen.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.PostViewHolder
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost

import sk.upjs.ics.android.teazoneinc.R
import java.util.*
import kotlin.collections.ArrayList

class HomeScreenFragment : Fragment() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()
    val dbAdapterPost = DbAdapterPost()
    val storageAdapter=StorageAdapter()

    private lateinit var mAdapter: FirestorePagingAdapter<DataPost, PostViewHolder>
    private val mFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")) {
//            tvDajTu.text=DbAdapterUser.userUser.username
//        }
//        else{ tvDajTu.text=DbAdapterUser.userFirma.username }

        setScreen()
    }

    private fun setScreen() {
        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")) {
            if (!DbAdapterUser.userUser.followingIDs.isEmpty()) {
                tvNoPosts.visibility = View.GONE

                // Init RecyclerView
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(context)
                val list: MutableList<String> = DbAdapterUser.userUser.followingIDs

                setupAdapter(list)

                // Refresh Action on Swipe Refresh Layout
                swipeRefreshLayout.setOnRefreshListener {
                    mAdapter.refresh()
                }
            }
        }
        else {
            if (!DbAdapterUser.userFirma.followingIDs.isEmpty()) {
                tvNoPosts.visibility = View.GONE
                // Init RecyclerView
                recyclerView.setHasFixedSize(true)
                recyclerView.layoutManager = LinearLayoutManager(context)
                val list: MutableList<String> = DbAdapterUser.userFirma.followingIDs

                setupAdapter(list)

                // Refresh Action on Swipe Refresh Layout
                swipeRefreshLayout.setOnRefreshListener {
                    mAdapter.refresh()
                }
            }
        }
    }

    private fun setupAdapter(list: MutableList<String>) {



        val mPostsCollection = mFirestore.collection("Posts").whereIn("creatorID", list)
        val mQuery = mPostsCollection.orderBy("timeStamp", Query.Direction.DESCENDING)

        // Init Paging Configuration
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPrefetchDistance(2)
            .setPageSize(2)
            .build()

        // Init Adapter Configuration
        val options = FirestorePagingOptions.Builder<DataPost>()
            .setLifecycleOwner(this)
            .setQuery(mQuery, config, DataPost::class.java)
            .build()

        // Instantiate Paging Adapter
        mAdapter = object : FirestorePagingAdapter<DataPost, PostViewHolder>(options) {
            var posts = java.util.ArrayList<DataPost>()
            override fun onBindViewHolder(p0: PostViewHolder, p1: Int, p2: DataPost) {
                posts.add(p2)
                p0.bind(posts[p1], context, fragmentManager!!)
                if (!p2.postPic.equals(null)) {
                    storageAdapter.getPostPic(p2, EventListener{post,_->
                        updatePostWithPhoto(post!!)
                    })
                }
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
                val view = layoutInflater.inflate(R.layout.post_item_view, parent, false)
                return PostViewHolder(view)
            }

            override fun onError(e: Exception) {
                super.onError(e)
                Log.e("MainActivity", e.message)
            }

            fun updatePostWithPhoto(post:DataPost){
                posts.set(posts.indexOf(post),post)
                notifyItemChanged(posts.indexOf(post))
            }

            override fun onLoadingStateChanged(state: LoadingState) {
                when (state) {
                    LoadingState.LOADING_INITIAL -> {
                        swipeRefreshLayout.isRefreshing = true
                    }

                    LoadingState.LOADING_MORE -> {
                        swipeRefreshLayout.isRefreshing = true
                    }

                    LoadingState.LOADED -> {
                        swipeRefreshLayout.isRefreshing = false
                    }

                    LoadingState.ERROR -> {
                        Toast.makeText(
                            activity,
                            "Error Occurred!",
                            Toast.LENGTH_SHORT
                        ).show()
                        swipeRefreshLayout.isRefreshing = false
                    }

                    LoadingState.FINISHED -> {
                        swipeRefreshLayout.isRefreshing = false
                    }
                }
            }

        }

        // Finally Set the Adapter to RecyclerView
        recyclerView.adapter = mAdapter

    }
}