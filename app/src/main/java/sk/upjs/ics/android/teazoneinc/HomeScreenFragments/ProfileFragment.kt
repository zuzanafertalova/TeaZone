package sk.upjs.ics.android.teazoneinc.HomeScreenFragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_fragment_profile.*
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvEmail
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvFollowers
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvFollowing
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvUsername
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Activities.HomeScreenActivity
import sk.upjs.ics.android.teazoneinc.Activities.LoginActivity
import sk.upjs.ics.android.teazoneinc.Adapters.ViewPagerAdapter
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.ProfileFragments.ReviewsFragment
import sk.upjs.ics.android.teazoneinc.ProfileScreenFragments.FollowersFragment

import sk.upjs.ics.android.teazoneinc.R

class ProfileFragment : Fragment() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()
    val dbAdapterPost = DbAdapterPost()
    val homeScreenActivity= HomeScreenActivity()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogOutSetClick()
        setUserToTextFields()
    }

    fun btnLogOutSetClick(){
        btnLogOut.setOnClickListener(View.OnClickListener {
            authAdapter.logOut()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        })
    }

    fun setUserToTextFields(){
        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")) {
            tvUsername.text = DbAdapterUser.userUser.username
            tvEmail.text=DbAdapterUser.userUser.email
            tvFollowing.text=DbAdapterUser.userUser.following.toString()
            tvFollowing_FollowersProfileFragment.text = "Following"

            val titles = ArrayList<String>()
            titles.add("0")
            titles.add(DbAdapterUser.userUser.following.toString())
            setUserViewPager(titles)
        }
        else{
            tvUsername.text = DbAdapterUser.userFirma.username
            tvEmail.text=DbAdapterUser.userFirma.email
            tvFollowing.text=DbAdapterUser.userFirma.following.toString()
            tvFollowers.text=DbAdapterUser.userFirma.followers.toString()
            tvFollowing_FollowersProfileFragment.text="Followers"

            val titles = ArrayList<String>()
            titles.add("0")
            titles.add(DbAdapterUser.userFirma.followers.toString())
            setFirmaViewPager(titles)
        }
    }


    fun setUserViewPager(titles: ArrayList<String>){
        val viewPagerAdapterProfleFragment : ViewPagerAdapter
        fragmentManager?.let {
            viewPagerAdapterProfleFragment = ViewPagerAdapter(it)
            viewPagerAdapterProfleFragment.addManagerProfile(ReviewsFragment(),titles[0])
            viewPagerAdapterProfleFragment.addManagerProfile(FollowersFragment(),titles[1])
            viewPagerProfileFragment.adapter=viewPagerAdapterProfleFragment
            tabsProfileFragment.setupWithViewPager(viewPagerProfileFragment)
        }
    }

    fun setFirmaViewPager(titles: ArrayList<String>){
        val viewPagerAdapterProfileFragment : ViewPagerAdapter
        fragmentManager?.let {
            viewPagerAdapterProfileFragment = ViewPagerAdapter(it)
            viewPagerAdapterProfileFragment.addManagerProfile(ReviewsFragment(),titles[0])
            viewPagerAdapterProfileFragment.addManagerProfile(FollowersFragment(),titles[1])
            viewPagerProfileFragment.adapter=viewPagerAdapterProfileFragment
            tabsProfileFragment.setupWithViewPager(viewPagerProfileFragment)
        }
    }


}
