package sk.upjs.ics.android.teazoneinc.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.fragment_firstsettings.*
import kotlinx.android.synthetic.main.fragment_set_username.tvSetUsername
import sk.upjs.ics.android.teazoneinc.Adapters.AlgoliaSearchAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.HomeScreenFragment
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.ProfileFragment
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.SearchFragment
import sk.upjs.ics.android.teazoneinc.Adapters.ViewPagerAdapter
import sk.upjs.ics.android.teazoneinc.Dialogs.BottomSheetTypPodniku
import sk.upjs.ics.android.teazoneinc.R

class HomeScreenActivity : AppCompatActivity(), BottomSheetTypPodniku.BottomSheetListener {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        if(dbAdapterUser.getStatusOfLoggedUser().equals("User")){
            tvChangeTypPodniku2.visibility = View.GONE
            btnShowOptions2.visibility = View.GONE
            tvTypPodnikuChosen2.visibility = View.GONE
        }


        setUsernameFragment()
        setUpOpenBottomSheetTypPodniku()
        btnSetUsernameSetClick()
//        algoliaSearchAdapter.transferCollectionToAlgolia("FirmaUsers")
//        setClickBtnPost()
//        getPost()
    }



    fun setUsernameFragment(){
        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")){
            if (!DbAdapterUser.userUser.username.equals("")) {
                fragmentSetUserame.view?.visibility = View.GONE
                setViewPager()
            }
        }
        else{
            if (!DbAdapterUser.userFirma.username.equals("")) {
                fragmentSetUserame.view?.visibility = View.GONE
                setViewPager()
            }
        }
    }

    fun btnSetUsernameSetClick(){
        btnSetFirstSettings.setOnClickListener(View.OnClickListener {
            val username=tvSetUsername.text.toString()
            val typPodniku = tvTypPodnikuChosen2.text.toString()
            if (dbAdapterUser.getStatusOfLoggedUser().equals("User")){
                if (!username.equals("")){
                    authAdapter.currentUser?.let {
                        dbAdapterUser.setFirstSettingsUser(it, username, EventListener { username, _ ->
                            username?.let {
                                setViewPager()
                                fragmentSetUserame.view?.visibility = View.GONE
                            }
//                        dbAdapterUser.setTypPodniku(it, tvTypPodnikuChosen.text.toString())
                        })
                    }
                }
                else{ Toast.makeText(this,"Nastavte si prosím uživateľské meno",Toast.LENGTH_SHORT).show()
                }
            }
            else{
                if (!username.equals("") && !typPodniku.equals("")){
                    authAdapter.currentUser?.let {
                        dbAdapterUser.setFirstSettingsFirma(it, username, typPodniku, EventListener { username, _ ->
                            username?.let {
                                setViewPager()
                                fragmentSetUserame.view?.visibility = View.GONE
                            }
//                        dbAdapterUser.setTypPodniku(it, tvTypPodnikuChosen.text.toString())
                        })
                    }
                }
                else{
                    Toast.makeText(this,"Vyplnte prosím všetky polia",Toast.LENGTH_SHORT).show()
                }
            }

        })
    }

    fun setUpOpenBottomSheetTypPodniku(){
        btnShowOptions2.setOnClickListener{
            val bottomSheet = BottomSheetTypPodniku(this)
            supportFragmentManager?.let { it1 -> bottomSheet.show(it1, "BottomSheetDialogTypPodniku") }
        }
    }

    override fun onOptionClick(text: String) {
        tvTypPodnikuChosen2.text = text
    }

    fun setViewPager(){
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addManagerProfile(SearchFragment(),"")
        viewPagerAdapter.addManagerProfile(HomeScreenFragment(),"")
        viewPagerAdapter.addManagerProfile(ProfileFragment(),"")
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
