package sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.InputStream



class StorageAdapter{
    private var storage = Firebase.storage
    var storageRef = storage.reference
    var profilePicRef: StorageReference? = storageRef.child("ProfilePics")
    var postPicRef: StorageReference? = storageRef.child("PostPics")

//    @GlideModule public class MyAppGlideModule : AppGlideModule() {
//        override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
//            registry.append(StorageReference::class.java , InputStream::class.java, FirebaseImageLoader.Factory())
//        }
//    }


    fun getProfilePic(pic : String, imageView: CircleImageView){
        val picRef = profilePicRef?.child(pic)

        picRef?.getBytes(1024*1024)
            ?.addOnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
                imageView.setImageBitmap(bitmap)
            }
    }

    fun getPostPic(pic : String, imageView : ImageView){
        val picRef = postPicRef?.child(pic)

        picRef?.getBytes(1024*1024)
            ?.addOnSuccessListener {
            val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
            imageView.setImageBitmap(bitmap)
        }
    }


}