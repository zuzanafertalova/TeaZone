package sk.upjs.ics.android.teazoneinc.HomeScreenFragments.ProfileFragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.EventListener
import sk.upjs.ics.android.teazoneinc.Activities.ProfileFromSearchActivity
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterReview
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview
import sk.upjs.ics.android.teazoneinc.R
import java.util.*
import kotlin.collections.ArrayList

class ReviewsFragment : Fragment() {

    val dbAdapterReview = DbAdapterReview()
    var reviewList = ArrayList<DataReview>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setReviewsList()
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

}
