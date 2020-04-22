package sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*


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

        val bytes: Long= 1024*1024*5
        picRef?.getBytes(bytes)
            ?.addOnSuccessListener {
            val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
            imageView.setImageBitmap(bitmap)
        }
    }

    fun uploadPostPic(imageViewPic: ImageView) : String{
        imageViewPic.isDrawingCacheEnabled = true
        imageViewPic.buildDrawingCache()
        val bitmap = (imageViewPic.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uuid = UUID.randomUUID().toString()
        val uploadRef : StorageReference = postPicRef?.child(uuid)!!

        var uploadTask = uploadRef?.putBytes(data)
        uploadTask?.addOnFailureListener {
            // Handle unsuccessful uploads
        }?.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }

        return uuid
    }

    fun uploadProfilePic(imageViewPic: ImageView) : String{
        imageViewPic.isDrawingCacheEnabled = true
        imageViewPic.buildDrawingCache()
        val bitmap = (imageViewPic.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val uuid = UUID.randomUUID().toString()
        val uploadRef : StorageReference = profilePicRef?.child(uuid)!!

        var uploadTask = uploadRef?.putBytes(data)
        uploadTask?.addOnFailureListener {
            // Handle unsuccessful uploads
        }?.addOnSuccessListener {
            Log.w("dajaky tag","breakpointto je")
            // ...
        }

        return uuid
    }


}