package sk.upjs.ics.android.teazoneinc.Dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import kotlinx.android.synthetic.main.bottom_sheet_add_post.view.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.R

class BottomSheetAddPost(private var mBottomSheetListener: BottomSheetListener) : BottomSheetDialogFragment() {

    val dbAdapterPost = DbAdapterPost()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.bottom_sheet_add_post, container, false)

        // To handle clicks
//        v.tvKaviaren.setOnClickListener {
//            mBottomSheetListener.onOptionClick("Kaviareň")
//            dismiss() //dismiss bottom sheet when item click
//        }

        v.btnPostPost.setOnClickListener {
            dbAdapterPost.setPost(v.tvPostContent.text.toString())
            dismiss() //dismiss bottom sheet when item click
        }


        return v
    }

    interface BottomSheetListener{
        fun onOptionClick(text:String)
    }

}