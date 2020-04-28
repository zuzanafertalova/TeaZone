package sk.upjs.ics.android.teazoneinc.Dialogs

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_add_post.*
import kotlinx.android.synthetic.main.bottom_sheet_add_post.view.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.R

class BottomSheetAddPost(private var mBottomSheetListener: BottomSheetListener) :
    BottomSheetDialogFragment() {

    private val dbAdapterPost = DbAdapterPost()
    private val storageAdapter = StorageAdapter()

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var mImageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.bottom_sheet_add_post, container, false)

        v.btnChoosePic.setOnClickListener {
            ivChoosenPic.visibility = View.VISIBLE
            openPicChooser()
        }

        v.btnPostPost.setOnClickListener {
            var picID: String? = null
            if (ivChoosenPic.getDrawable() == null) {

            }
            else {
                picID = storageAdapter.uploadPostPic(ivChoosenPic)
            }
            dbAdapterPost.setPost(v.tvPostContent.text.toString(), picID)

            dismiss() //dismiss bottom sheet when item click
        }

        return v
    }

    fun openPicChooser() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            mImageUri = data.data!!
            ivChoosenPic.setImageURI(mImageUri)
        }
    }

    interface BottomSheetListener {
        fun onOptionClick(text: String)
    }

}