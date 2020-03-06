package sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db

import com.google.firebase.firestore.FirebaseFirestore
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview

class DbAdapterReview {

    private val db = FirebaseFirestore.getInstance()

    fun setPostToDatabase (map : DataReview){
        db.collection("Reviews").add(map)
    }




}