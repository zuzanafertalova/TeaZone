package sk.upjs.ics.android.teazoneinc.Activities


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile_from_search.*
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvEmail
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvUsername
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.sendData
import sk.upjs.ics.android.teazoneinc.Adapters.ViewPagerAdapter
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataFirma
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataUser
import sk.upjs.ics.android.teazoneinc.ProfileScreenFragments.FirmaReviewsFragment
import sk.upjs.ics.android.teazoneinc.ProfileScreenFragments.FollowersFragment
import sk.upjs.ics.android.teazoneinc.ProfileScreenFragments.PostReviewFragment
import sk.upjs.ics.android.teazoneinc.ProfileScreenFragments.UserReviewsFragment
import sk.upjs.ics.android.teazoneinc.R


class ProfileFromSearchActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()
    var isBtnClicked : Boolean =false

    var userUser=DataUser()
    var userFirma=DataFirma()

    companion object {
        lateinit var docID : String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_from_search)
        docID = intent.getStringExtra("objectID")
        setData()
        setOnClickBtnWriteReview()



    }

    private fun setData(){
        dbAdapterUser.setProfileData(docID,object : sendData {
            override fun send(user: DataUser? , userFir: DataFirma?) {
                user?.let {
                    userUser=it
                    setDataUserUserToFields()
                }
                userFir?.let {
                    userFirma = it
                    setDataFirmaUserToFields()
                    setBtnFollowIfFollowed()
                }
            }

        })
    }

    private fun setBtnFollowIfFollowed(){
        if (userFirma.followersIDs.contains(authAdapter.currentUser?.uid)){
            isBtnClicked=true
            btnFollow.background = resources.getDrawable(R.drawable.ic_tea_cup_onclick)
            textView_sledovat.text="Sledované"
        }
    }

    private fun setDataUserUserToFields(){
        btnFollow.visibility=View.GONE
        btnWriteReview.visibility=View.GONE
        fragmentWriteReview.view?.visibility= View.GONE

        textView_sledovat.visibility = View.GONE
        tvUsername.text = userUser.username
        tvEmail.text=userUser.email
        tvFollowing_Followers.text = "Following"
        val titles = ArrayList<String>()
        titles.add("0")
        titles.add(userUser.following.toString())
        setUserViewPager(titles)
    }

    private fun setDataFirmaUserToFields(){
        fragmentWriteReview.view?.visibility=View.GONE

        tvUsername.text = userFirma.username
        tvEmail.text=userFirma.email
        tvFollowing_Followers.text = "Followers"
        val titles = ArrayList<String>()
        titles.add("0")
        titles.add(userFirma.followers.toString())
        setFirmaViewPager(titles)
        setOnClickBtnFollow()
    }

    private fun setOnClickBtnWriteReview(){
        btnWriteReview.setOnClickListener(View.OnClickListener {
            if (fragmentWriteReview.isVisible){
                fragmentWriteReview.view?.visibility = View.GONE
            }
            else {
                fragmentWriteReview.view?.visibility = View.VISIBLE
            }
        })
    }

    private fun setOnClickBtnFollow(){
        btnFollow.setOnClickListener(View.OnClickListener {
            if (isBtnClicked==true){
                isBtnClicked=false
                dbAdapterUser.removeFollower(userFirma, docID,authAdapter.currentUser?.uid)
                btnFollow.background = resources.getDrawable(R.drawable.ic_tea_cup)
            }
            else{
                dbAdapterUser.addFollower(docID,authAdapter.currentUser?.uid)
                isBtnClicked=true
                btnFollow.background = resources.getDrawable(R.drawable.ic_tea_cup_onclick)
            }
        })
    }



    private fun setUserViewPager(titles: ArrayList<String>){
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addManagerProfile(UserReviewsFragment(),titles[0])
        viewPagerAdapter.addManagerProfile(FollowersFragment(),titles[1])
        viewPagerProfile.adapter=viewPagerAdapter
        tabsProfile.setupWithViewPager(viewPagerProfile)
    }

    private fun setFirmaViewPager(titles: ArrayList<String>){
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addManagerProfile(FirmaReviewsFragment(),titles[0])
        viewPagerAdapter.addManagerProfile(FollowersFragment(),titles[1])
        viewPagerProfile.adapter=viewPagerAdapter
        tabsProfile.setupWithViewPager(viewPagerProfile)
    }
}
