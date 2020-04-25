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

    var reviewList = ArrayList<DataReview>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsFragmentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_reviews, parent, false)
        return ReviewsFragmentHolder(view)
    }
    override fun getItemCount(): Int {
        return reviewList.size
    }
    override fun onBindViewHolder(holder: ReviewsAdapter.ReviewsFragmentHolder, position: Int) {
        holder.bindData(reviewList[position])
    }
    fun setNewData(reviewList: ArrayList<DataReview>) {
        this.reviewList = reviewList
        notifyDataSetChanged()
    }

    class ReviewsFragmentHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val starsButtons  = ArrayList<Button>()

        fun bindData(review : DataReview) {
            itemView.tvReview.text = review.content
            itemView.tvMenoUseraReviews.text= review.creatorID
            setButtonsToList(itemView)

            val rating = review.rating
            for (i in 0 .. rating-1){
                starsButtons[i].setBackgroundResource(R.drawable.ic_star_black_24dp)
            }
        }

        fun setButtonsToList(itemView: View){
            itemView.btnStarReview1.setBackgroundResource(R.drawable.ic_star_border_black_24dp)
            itemView.btnStarReview2.setBackgroundResource(R.drawable.ic_star_border_black_24dp)
            itemView.btnStarReview3.setBackgroundResource(R.drawable.ic_star_border_black_24dp)
            itemView.btnStarReview4.setBackgroundResource(R.drawable.ic_star_border_black_24dp)
            itemView.btnStarReview5.setBackgroundResource(R.drawable.ic_star_border_black_24dp)
            starsButtons.add(itemView.btnStarReview1)
            starsButtons.add(itemView.btnStarReview2)
            starsButtons.add(itemView.btnStarReview3)
            starsButtons.add(itemView.btnStarReview4)
            starsButtons.add(itemView.btnStarReview5)
        }
    }


}