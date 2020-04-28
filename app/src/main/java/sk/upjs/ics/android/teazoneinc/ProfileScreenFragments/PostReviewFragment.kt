package sk.upjs.ics.android.teazoneinc.ProfileScreenFragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_post_review.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterReview
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview
import sk.upjs.ics.android.teazoneinc.R
import sk.upjs.ics.android.teazoneinc.Activities.ProfileFromSearchActivity


class PostReviewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_review, container, false)
    }

}
