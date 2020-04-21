package sk.upjs.ics.android.teazoneinc.Dialogs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.bottom_sheet_comment.*
import kotlinx.android.synthetic.main.fragment_fragment_search.*
import kotlinx.android.synthetic.main.list_item_comment.*
import sk.upjs.ics.android.teazoneinc.Activities.ProfileFromSearchActivity
import sk.upjs.ics.android.teazoneinc.Adapters.CommentAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.PostViewHolder
import sk.upjs.ics.android.teazoneinc.Adapters.SearchResultAdapter
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post.DataComment
import sk.upjs.ics.android.teazoneinc.R

class BottomSheetComments(private var mBottomSheetListener: PostViewHolder, postID: String) : BottomSheetDialogFragment() {

    internal var  adapter: CommentAdapter? = null
    private var dbAdapterPost = DbAdapterPost()
    private var dbAdapterUser = DbAdapterUser()
    private val postID = postID
    private var postList = ArrayList<DataComment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v =  inflater.inflate(R.layout.bottom_sheet_comment, container, false)



        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        commentsList(postID)
        addComment()
    }

    private fun addComment(){
        btnAddComment.setOnClickListener{
            if(tiComment.text.isNotEmpty()){
                if(dbAdapterUser.getStatusOfLoggedUser().equals("User")){
                    dbAdapterPost.createComment(postID, DbAdapterUser.userUser.docID, DbAdapterUser.userUser.username, tiComment.text.toString(), EventListener{comment, _ ->
                        postList.add(comment!!)
                        setRecyclerData(postList)
                    })
                }else {
                    dbAdapterPost.createComment(postID, DbAdapterUser.userFirma.docID, DbAdapterUser.userFirma.username, tiComment.text.toString(), EventListener { comment, _ ->
                        postList.add(comment!!)
                        setRecyclerData(postList)
                    })
                }
            }
        }
    }
    private fun setUpRecyclerView() {
        rvComments.layoutManager = LinearLayoutManager(context)
        adapter = CommentAdapter()
        rvComments.adapter = adapter
    }

    private fun commentsList(postID: String){

        dbAdapterPost.getCommentList(postID, EventListener{ list, _ ->
            list?.let {
                postList = it
                setRecyclerData(postList)
            }
        })
    }

    private fun setRecyclerData(commentList:ArrayList<DataComment>){
        adapter?.setNewData(commentList)
    }

}