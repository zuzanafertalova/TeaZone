package sk.upjs.ics.android.teazoneinc.HomeScreenFragments.ProfileFragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.bottom_sheet_comment.*
import kotlinx.android.synthetic.main.fragment_post_review.*
import kotlinx.android.synthetic.main.fragment_reviews.*
import sk.upjs.ics.android.teazoneinc.Activities.ProfileFromSearchActivity
import sk.upjs.ics.android.teazoneinc.Adapters.CommentAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterReview
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.ReviewsAdapter
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataComment
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview
import sk.upjs.ics.android.teazoneinc.R
import java.util.*
import kotlin.collections.ArrayList

class ReviewsFragment : Fragment() {

    val dbAdapterReview = DbAdapterReview()
    internal var  adapter: ReviewsAdapter? = null
    private var reviewList = ArrayList<DataReview>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setReviewsList()
        addReview()
    }

    private fun addReview(){
        setRecyclerData(reviewList)
    }



    private fun setUpRecyclerView() {
        rvReviews.layoutManager = LinearLayoutManager(context)
        adapter = ReviewsAdapter()
        rvReviews.adapter = adapter
    }


    fun setReviewsList(){
        DbAdapterUser.userUser.docID?.let {docID ->
            dbAdapterReview.getReviewsUserList(docID, EventListener{ list, _ ->
                list?.let { reviewList = it }
            })
        }
        DbAdapterUser.userFirma.docID?.let {docID ->
            dbAdapterReview.getReviewsFirmaList(docID, EventListener{ list, _ ->
                list?.let { reviewList = it }
            })
        }
    }

    private fun setRecyclerData(reviewList:ArrayList<DataReview>){
        adapter?.setNewData(reviewList)
    }

}
