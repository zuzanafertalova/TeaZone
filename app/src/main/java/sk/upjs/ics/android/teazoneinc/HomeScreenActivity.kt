package sk.upjs.ics.android.teazoneinc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.fragment_home_screen.*
import kotlinx.android.synthetic.main.fragment_set_username2.*
import sk.upjs.ics.android.teazoneinc.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.HomeScreenFragment
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.ProfileFragment
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.SearchFragment
import sk.upjs.ics.android.teazoneinc.ViewPagerAdaPter.ViewPagerAdapter

class HomeScreenActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()
    val dbAdapterPost = DbAdapterPost()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        setViewPager()

//        setClickBtnPost()
//        getPost()
    }

    fun setViewPager(){
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addMananger(SearchFragment())
        viewPagerAdapter.addMananger(HomeScreenFragment())
        viewPagerAdapter.addMananger(ProfileFragment())
        viewPager.adapter=viewPagerAdapter
        viewPager.currentItem=1
        tabs.setupWithViewPager(viewPager)
        tabs.getTabAt(0)?.setIcon(R.drawable.ic_find_24dp)
        tabs.getTabAt(1)?.setIcon(R.drawable.ic_home)
        tabs.getTabAt(2)?.setIcon(R.drawable.ic_person_24dp)
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
