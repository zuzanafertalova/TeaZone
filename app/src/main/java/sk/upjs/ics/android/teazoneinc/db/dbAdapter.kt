package sk.upjs.ics.android.teazoneinc.db

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore

class Data {
    private val db = FirebaseFirestore.getInstance()
    fun getData(document: String, collections: String, eventListener: EventListener<DocumentSnapshot>) {
        db.collection(collections).document(document)
            .get()
            .addOnSuccessListener {
                eventListener.onEvent(it, null)
            }
            .addOnFailureListener { exception ->
                eventListener.onEvent(null, null)
                Log.w("TU SOMMMMMMM", "Error getting documents.", exception)
            }
    }
}