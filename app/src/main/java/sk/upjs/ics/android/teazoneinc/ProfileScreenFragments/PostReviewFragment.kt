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

    private val starsButtons  = ArrayList<Button>()
    private var review = DataReview()

    private val authAdapter = AuthAdapter()
    private val dbAdapterReview = DbAdapterReview()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonsToList()
        setButtonsOnClick()
        setButtonPostClick()
    }

    fun setButtonsToList(){
        starsButtons.add(btnStar1)
        starsButtons.add(btnStar2)
        starsButtons.add(btnStar3)
        starsButtons.add(btnStar4)
        starsButtons.add(btnStar5)
    }

    private fun setButtonsOnClick(){
        for (i in 0..4){
            starsButtons[i].setOnClickListener(View.OnClickListener {
                review.rating=i+1
                resetStars()
                for (j in 0..i){
                    starsButtons[j].setBackgroundResource(R.drawable.ic_star_black_24dp)
                }
            })
        }
    }

    private fun setButtonPostClick(){
        btnPostReview.setOnClickListener(View.OnClickListener {
            setDatasToPost()
            resetStars()
            tvReviewContent.setText("")
        })
    }

    private fun resetStars(){
        for(k in 0..4){
            starsButtons[k].setBackgroundResource(R.drawable.ic_star_border_black_24dp)
        }
    }

    private fun setDatasToPost(){
        review.content = tvReviewContent.text.toString()
        review.creatorID = authAdapter.currentUser?.uid
        review.recieverID = ProfileFromSearchActivity.docID
        dbAdapterReview.setPostToDatabase(review)
    }

}
