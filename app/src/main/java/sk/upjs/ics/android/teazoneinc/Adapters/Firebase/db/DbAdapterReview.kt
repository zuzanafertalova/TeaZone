package sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db

import android.provider.ContactsContract
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview

class DbAdapterReview {

    private val db = FirebaseFirestore.getInstance()

    fun setPostToDatabase (map : DataReview){
        db.collection("Reviews").add(map)
    }

    fun getReviewsFirmaList(recieverID:String, eventListener: EventListener<ArrayList<DataReview>>){
        val reviewsList = ArrayList<DataReview>()
        db.collection("Reviews").whereEqualTo("recieverID",recieverID).get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    reviewsList.add(setDocumentToDataClass(document))
                    eventListener.onEvent(reviewsList,null)
                }
            }
    }

    fun getReviewsUserList(creatorID:String, eventListener: EventListener<ArrayList<DataReview>>){
        val reviewsList = ArrayList<DataReview>()
        db.collection("Reviews").whereEqualTo("creatorID",creatorID).get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    reviewsList.add(setDocumentToDataClass(document))
                    eventListener.onEvent(reviewsList,null)
                }
            }
    }

    fun setDocumentToDataClass(document:DocumentSnapshot):DataReview{
        var review = DataReview()
        review.creatorID = document.getString("creatorID")
        review.recieverID = document.getString("recieverID")
        review.content = document.getString("content")
        document.getLong("rating")?.let { review.rating =it.toInt() }

        return review
    }





}