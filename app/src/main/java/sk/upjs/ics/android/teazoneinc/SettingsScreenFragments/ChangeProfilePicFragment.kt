package sk.upjs.ics.android.teazoneinc.SettingsScreenFragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.bottom_sheet_add_post.*
import kotlinx.android.synthetic.main.fragment_change_profile_pic.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.R

class ChangeProfilePicFragment : Fragment() {

    private val storageAdapter = StorageAdapter()
    private val dbAdapterUser = DbAdapterUser()
    private val authAdapter=AuthAdapter()

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var mImageUri: Uri

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return inflater.inflate(R.layout.fragment_change_profile_pic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBtnLoadProfilePicClick()
        onBtnSaveProfilePicClick()
    }

    fun onBtnSaveProfilePicClick(){
        btnSaveProfilePic.setOnClickListener{
            var picID: String? = null
            if (ivChangeProfilePic.getDrawable() == null) {
            }
            else {
                picID = storageAdapter.uploadProfilePic(ivChangeProfilePic)
            }

            dbAdapterUser.updateProfilePic(authAdapter.currentUser!!.uid,picID!!)

        }
    }

    fun onBtnLoadProfilePicClick(){
        btnLoadProfilePic.setOnClickListener{
            openPicChooser()
        }
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
            ivChangeProfilePic.setImageURI(mImageUri)
        }
    }
}