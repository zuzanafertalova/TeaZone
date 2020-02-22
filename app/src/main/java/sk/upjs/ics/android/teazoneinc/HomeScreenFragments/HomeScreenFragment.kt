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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_home_screen.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.PostViewHolder
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost

import sk.upjs.ics.android.teazoneinc.R
import java.util.*

class HomeScreenFragment : Fragment() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()
    val dbAdapterPost = DbAdapterPost()

    private lateinit var mAdapter: FirestorePagingAdapter<DataPost, PostViewHolder>
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mPostsCollection = mFirestore.collection("Posts")
    private val mQuery = mPostsCollection.orderBy("content", Query.Direction.DESCENDING)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_screen, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")) {
//            tvDajTu.text=DbAdapterUser.userUser.username
//        }
//        else{ tvDajTu.text=DbAdapterUser.userFirma.username }

        // Init RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        setupAdapter()

        // Add Random Posts
        fab_add.setOnClickListener {
            addRandomPosts()
        }

        // Refresh Action on Swipe Refresh Layout
        swipeRefreshLayout.setOnRefreshListener {
            mAdapter.refresh()
        }
    }


    private fun addRandomPosts() {
        createPosts().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    activity,
                    "Posts Added!",
                    Toast.LENGTH_SHORT
                ).show()

                // Refresh Adapter
                mAdapter.refresh()
            }
        }
    }

    private fun createPosts(): Task<Void> {
        val writeBatch = mFirestore.batch()

        for (i in 0..30) {
            val authorName = "Author $i"
            val message = "Hi There! This is message $i. Happy Coding!"

            val id = String.format(Locale.getDefault(), "post_%03d", i)
            val post = DataPost(authorName,message, 0,0)

            writeBatch.set(mPostsCollection.document(id), post)
        }

        return writeBatch.commit()
    }

    private fun setupAdapter() {

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
            override fun onBindViewHolder(p0: PostViewHolder, p1: Int, p2: DataPost) {
                p0.bind(p2)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
                val view = layoutInflater.inflate(R.layout.post_item_view, parent, false)
                return PostViewHolder(view)
            }

            override fun onError(e: Exception) {
                super.onError(e)
                Log.e("MainActivity", e.message)
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
