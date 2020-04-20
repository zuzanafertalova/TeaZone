package sk.upjs.ics.android.teazoneinc.Dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_comment.*
import kotlinx.android.synthetic.main.fragment_fragment_search.*
import sk.upjs.ics.android.teazoneinc.Activities.ProfileFromSearchActivity
import sk.upjs.ics.android.teazoneinc.Adapters.CommentAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.PostViewHolder
import sk.upjs.ics.android.teazoneinc.Adapters.SearchResultAdapter
import sk.upjs.ics.android.teazoneinc.R

class BottomSheetComments(private var mBottomSheetListener: PostViewHolder) : BottomSheetDialogFragment() {

    internal var  adapter: CommentAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v =  inflater.inflate(R.layout.bottom_sheet_comment, container, false)


        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        var comment = ArrayList<String>()
        comment.add("Pekný koment. :) ")
        comment.add("JUJ")
        comment.add("Ďalší super koment, som super proste.")
        adapter?.setNewData(comment)
    }

    private fun setUpRecyclerView() {
        rvComments.layoutManager = LinearLayoutManager(context)
        adapter = CommentAdapter()
        rvComments.adapter = adapter
    }



}