package sk.upjs.ics.android.teazoneinc.ProfileScreenFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.fragment_posts_from_search.*
import sk.upjs.ics.android.teazoneinc.Activities.ProfileFromSearchActivity
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.PostsAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.PostsAdapterFromSearch
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost

import sk.upjs.ics.android.teazoneinc.R
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class PostsFromSearchFragment : Fragment() {

    val dbAdapterPost = DbAdapterPost()
    internal var  adapter: PostsAdapterFromSearch? = null
    var postList = ArrayList<DataPost>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts_from_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbAdapterPost.getPostsList(ProfileFromSearchActivity.docID!!, EventListener{ list, _ ->
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
        rvPostsFromSearch.layoutManager = LinearLayoutManager(context)
        adapter = PostsAdapterFromSearch(context,fragmentManager!!)
        rvPostsFromSearch.adapter = adapter
    }


    private fun setRecyclerData(postsList:ArrayList<DataPost>){
        adapter?.setNewData(postsList)
    }

}
