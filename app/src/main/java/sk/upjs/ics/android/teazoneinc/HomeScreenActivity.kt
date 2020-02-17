package sk.upjs.ics.android.teazoneinc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.fragment_set_username2.*
import sk.upjs.ics.android.teazoneinc.Firebase.DataHolderClasses.Post.DataPost
import sk.upjs.ics.android.teazoneinc.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapterUser

class HomeScreenActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()
    val dbAdapterPost = DbAdapterPost()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        btnSetUsername.setOnClickListener(View.OnClickListener {
            val username=tvSetUsername.text.toString()
            authAdapter.currentUser?.let {
                dbAdapterUser.setUsername(it,username)
                fragmentSetUserame.view?.visibility = View.GONE
            }
        })
        setUsernameFragment()
//        setClickBtnPost()
//        getPost()
    }


    fun setUsernameFragment(){
        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")){
            if (!DbAdapterUser.userUser.username.equals("")) {
                fragmentSetUserame.view?.visibility = View.GONE
            }
        }
        else{
            if (!DbAdapterUser.userFirma.username.equals("")) {
                fragmentSetUserame.view?.visibility = View.GONE
            }
        }

    }

//    fun setClickBtnPost(){
//        btnPost.setOnClickListener(View.OnClickListener {
//            dbAdapterPost.setPost(tvPost.text.toString())
//        })
//    }
//
//    fun setClickBtnComment(post: DataPost){
//        btnComment.setOnClickListener(View.OnClickListener {
//            post.postID?.let {
//                dbAdapterPost.addComment(tvComment.text.toString(),it)
//            }
//        })
//    }
//
//    fun getPost(){
//        var post=DataPost()
//        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")) {
//            dbAdapterPost.getPost(EventListener{post9,_->
//                post9?.let {
//                    post=post9
//                    setPostToSeeIt(post)
//                    setClickBtnComment(post)
//                }
//            })
//        }
//        else{ dbAdapterPost.getPostFirma(EventListener{post9,_->
//            post9?.let {
//                post=post9
//                setPostToSeeIt(post)
//                setClickBtnComment(post)
//            }
//        }) }
//    }
//
//    fun setPostToSeeIt(post:DataPost){
//        tvPost.text=post.content
//    }

}
