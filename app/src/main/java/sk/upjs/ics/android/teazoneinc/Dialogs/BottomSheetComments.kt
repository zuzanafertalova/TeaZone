package sk.upjs.ics.android.teazoneinc.Dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_comment.*
import sk.upjs.ics.android.teazoneinc.Adapters.PostViewHolder
import sk.upjs.ics.android.teazoneinc.Adapters.SearchResultAdapter
import sk.upjs.ics.android.teazoneinc.R

class BottomSheetComments(private var mBottomSheetListener: PostViewHolder) : BottomSheetDialogFragment() {

    internal var  adapter: PostViewHolder? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v =  inflater.inflate(R.layout.bottom_sheet_comment, container, false)


        return v
    }

}