package sk.upjs.ics.android.teazoneinc.ProfileScreenFragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.EventListener
import sk.upjs.ics.android.teazoneinc.Activities.ProfileFromSearchActivity
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterReview
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview

import sk.upjs.ics.android.teazoneinc.R
import java.util.*
import kotlin.collections.ArrayList

class UserReviewsFragment : Fragment() {

    val dbAdapterReview = DbAdapterReview()
    var reviewList = ArrayList<DataReview>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbAdapterReview.getReviewsUserList(ProfileFromSearchActivity.docID, EventListener{ list, _ ->
            list?.let { reviewList = it }
        })
    }


}
