package sk.upjs.ics.android.teazoneinc.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
        fun bindData(review : DataReview) {
            itemView.tvReview.text = review.content
            itemView.tvMenoUseraReviews.text= review.creatorID
        }
    }

}