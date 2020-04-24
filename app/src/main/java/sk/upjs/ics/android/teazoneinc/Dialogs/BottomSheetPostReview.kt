package sk.upjs.ics.android.teazoneinc.Dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_post_review.*
import sk.upjs.ics.android.teazoneinc.Activities.ProfileFromSearchActivity
import sk.upjs.ics.android.teazoneinc.R

class BottomSheetPostReview(private var mBottomSheetListener: ProfileFromSearchActivity) : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.bottom_sheet_post_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnPostReview.setOnClickListener{
            dismiss()
        }
    }

    interface BottomSheetListener {
        fun onOptionClick(text: String)
    }
}