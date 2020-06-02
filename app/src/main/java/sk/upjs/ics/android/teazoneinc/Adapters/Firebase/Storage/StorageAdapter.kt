package sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage

import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataPost
import java.io.ByteArrayOutputStream
import java.util.*


class StorageAdapter{
    private var storage = Firebase.storage
    var storageRef = storage.reference
    val profilePicRef: StorageReference? = storageRef.child("ProfilePics")
    val postPicRef: StorageReference? = storageRef.child("PostPics")
    val menuRef:StorageReference? = storageRef.child("Menus")
    val authAdapter = AuthAdapter()

//    @GlideModule public class MyAppGlideModule : AppGlideModule() {
//        override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
//            registry.append(StorageReference::class.java , InputStream::class.java, FirebaseImageLoader.Factory())
//        }
//    }


    fun getProfilePic(pic : String, imageView: CircleImageView){
        val picRef = profilePicRef?.child(pic)

        val bytes: Long= 1024*1024*5
        picRef?.getBytes(bytes)
            ?.addOnSuccessListener {
                val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
                imageView.setImageBitmap(bitmap)
            }
    }

    fun getPostPic(post:DataPost,eventListener: EventListener<DataPost>){
        val picRef = postPicRef?.child(post.postPic!!)

        val bytes: Long= 1024*1024*5
        picRef?.getBytes(bytes)
            ?.addOnSuccessListener {
                post.photoBytes=it
                eventListener.onEvent(post, null)
            val bitmap = BitmapFactory.decodeByteArray(it,0,it.size)
//            imageView.setImageBitmap(bitmap)
        }
    }

    fun getPostPicForOtherThanHome(postPic:String, imageView : ImageView){
        val picRef = postPicRef?.child(postPic!!)

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

    fun uploadPDFFile(uri: Uri,eventListener: EventListener<String>){
        val menuName = authAdapter.currentUser?.uid+"Menu.pdf"
        menuRef?.child(menuName)?.putFile(uri)
            ?.addOnSuccessListener {
                eventListener.onEvent(menuName,null)
            }
            ?.addOnProgressListener {

            }
            ?.addOnFailureListener {
                eventListener.onEvent(null,null)
            }
    }

    fun downloadDPFFile(menuName:String,context: Context,firmaUsername:String){
        menuRef?.child(menuName)?.downloadUrl
            ?.addOnSuccessListener {
            downloadFile(context,firmaUsername+"Menu",".pdf", Environment.DIRECTORY_DOWNLOADS,it.toString())
        }
    }

    private fun downloadFile(context: Context,fileName:String,fileExtension: String,destinationDirectory:String,url:String){

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val uri = Uri.parse(url)
        val request = DownloadManager.Request(uri)

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension)

        downloadManager.enqueue(request)
    }

    fun deletePostPics(postPics:ArrayList<String>,eventListener: EventListener<Boolean>){
        var kolkoBolo = 0
        for (postPic in postPics){
            val kolkoJe = postPics.size
            deleteSinglePostPic(postPic, EventListener{staloSA,_ ->
                if (staloSA!!){
                    kolkoBolo++
                    if (kolkoBolo==kolkoJe){
                        eventListener.onEvent(true,null)
                    }
                }
            })
        }
    }
    fun deleteSinglePostPic(postPic:String, eventListener: EventListener<Boolean>){
        postPicRef?.child(postPic)?.delete()
            ?.addOnSuccessListener {
                Log.w("podarilo sa pomazat","podarilo sa vymazat")
                eventListener.onEvent(true,null)
            }
    }

    fun deleteProfilePic(profilePic:String){
        profilePicRef?.child(profilePic)?.delete()
            ?.addOnSuccessListener {
            Log.w("ATATATAT", "PODADAAWAD")
        }
    }

}