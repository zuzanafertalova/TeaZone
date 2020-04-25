package sk.upjs.ics.android.teazoneinc.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_post_review.*
import kotlinx.android.synthetic.main.fragment_post_review.view.*
import kotlinx.android.synthetic.main.list_item_reviews.view.*
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview
import sk.upjs.ics.android.teazoneinc.R

class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ReviewsFragmentHolder>() {

    private val starsButtons  = ArrayList<Button>()
    var reviewList = ArrayList<DataReview>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsFragmentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_reviews, parent, false)
        setButtonsToList(view)
        return ReviewsFragmentHolder(view)
    }
    override fun getItemCount(): Int {
        return reviewList.size
    }
    override fun onBindViewHolder(holder: ReviewsAdapter.ReviewsFragmentHolder, position: Int) {
        holder.bindData(reviewList[position],starsButtons)
    }
    fun setNewData(reviewList: ArrayList<DataReview>) {
        this.reviewList = reviewList
        notifyDataSetChanged()
    }

    class ReviewsFragmentHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindData(review : DataReview, starsButtons:ArrayList<Button>) {
            itemView.tvReview.text = review.content
            itemView.tvMenoUseraReviews.text= review.creatorID

            val rating = review.rating
            for (i in 0 .. rating){
                starsButtons[i].setBackgroundResource(R.drawable.ic_star_black_24dp)
            }
        }
    }

    fun setButtonsToList(itemView: View){
        starsButtons.add(itemView.btnStar1)
        starsButtons.add(itemView.btnStar2)
        starsButtons.add(itemView.btnStar3)
        starsButtons.add(itemView.btnStar4)
        starsButtons.add(itemView.btnStar5)
    }

}