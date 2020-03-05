package sk.upjs.ics.android.teazoneinc.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_home_screen.*
import kotlinx.android.synthetic.main.activity_profile_from_search.*
import kotlinx.android.synthetic.main.fragment_fragment_profile.*
import kotlinx.android.synthetic.main.fragment_fragment_profile.ciara
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvEmail
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvFollowHide
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvFollowers
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvFollowing
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvUsername
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbInterface
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.sendData
import sk.upjs.ics.android.teazoneinc.Adapters.ViewPagerAdapter
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataFirma
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataUser
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.HomeScreenFragment
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.ProfileFragment
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.SearchFragment
import sk.upjs.ics.android.teazoneinc.ProfileScreenFragments.FollowersFragment
import sk.upjs.ics.android.teazoneinc.ProfileScreenFragments.ReviewsFragment
import sk.upjs.ics.android.teazoneinc.R

class ProfileFromSearchActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()
    lateinit var docID : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_from_search)
        docID = intent.getStringExtra("objectID")

        setData()
        setOnClickBtnFollow()

    }

    fun setData(){
        dbAdapterUser.setProfileData(docID,object : sendData {
            override fun send(user: DataUser? , userFirma: DataFirma?) {
                user?.let { setDataUserUserToFields(user) }
                userFirma?.let { setDataFirmaUserToFields(userFirma) }
            }

        })
    }

    fun setDataUserUserToFields(user: DataUser){
        tvUsername.text = user.username
        tvEmail.text=user.email
        tvFollowing.text=user.following.toString()
        tvFollowHide.visibility= View.GONE
        ciara.visibility= View.GONE
        val titles = ArrayList<String>()
        titles.add("0")
        titles.add(tvFollowing.text.toString())
        setViewPager(titles)
    }

    fun setDataFirmaUserToFields(userFirma: DataFirma){
        tvUsername.text = userFirma.username
        tvEmail.text=userFirma.email
        tvFollowing.text=userFirma.following.toString()
        tvFollowers.text=userFirma.followers.toString()
        val titles = ArrayList<String>()
        titles.add("0")
        titles.add(tvFollowers.text.toString())
        setViewPager(titles)
    }

    fun setOnClickBtnFollow(){
        btnFollow.setOnClickListener(View.OnClickListener {
            if (dbAdapterUser.getStatusOfLoggedUser().equals("User")){
                DbAdapterUser.userUser.docID?.let { dbAdapterUser.addFollower(docID,it) }
            }
            else{
                DbAdapterUser.userFirma.docID?.let { dbAdapterUser.addFollower(docID,it) }
            }

        })
    }

    fun setViewPager(titles: ArrayList<String>){
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addManagerProfile(ReviewsFragment(),titles[0])
        viewPagerAdapter.addManagerProfile(FollowersFragment(),titles[1])
        viewPagerProfile.adapter=viewPagerAdapter
        viewPagerProfile.currentItem=1
        tabsProfile.setupWithViewPager(viewPagerProfile)
    }
}
