package sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db

import com.google.firebase.firestore.FirebaseFirestore
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review.DataReview

class DbAdapterReview {

    private val db = FirebaseFirestore.getInstance()

    fun setPostToDatabase (docID : String, map : DataReview){
        db.collection("FirmaUsers").document(docID).collection("Reviews").add(map)
    }




}