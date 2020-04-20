package sk.upjs.ics.android.teazoneinc.ProfileScreenFragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.EventListener
import sk.upjs.ics.android.teazoneinc.Activities.ProfileFromSearchActivity
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview

import sk.upjs.ics.android.teazoneinc.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class PostsFragment : Fragment() {

    val dbAdapterPost = DbAdapterPost()
    var postList = ArrayList<DataPost>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_posts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        dbAdapterPost.getPostsList(ProfileFromSearchActivity.docID, EventListener{ list, _ ->
//            list?.let { postList = it }
//        })
    }


}
