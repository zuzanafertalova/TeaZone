package sk.upjs.ics.android.teazoneinc.HomeScreenFragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_fragment_profile.*
import sk.upjs.ics.android.teazoneinc.Activities.HomeScreenActivity
import sk.upjs.ics.android.teazoneinc.Activities.LoginActivity
import sk.upjs.ics.android.teazoneinc.Activities.SettingsActivity
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.ViewPagerAdapter
import sk.upjs.ics.android.teazoneinc.Dialogs.DialogOtvaracieHodiny
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.ProfileFragments.ReviewsFragment
import sk.upjs.ics.android.teazoneinc.ProfileScreenFragments.FollowersFragment
import sk.upjs.ics.android.teazoneinc.R


class ProfileFragment : Fragment() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()
    val dbAdapterPost = DbAdapterPost()
    val homeScreenActivity = HomeScreenActivity()
    val storageAdapter = StorageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLogOutSetClick()
        setUserToTextFields()
        btnSettingsSetClick()
        btnOtvaracieHodinyOnClick()
    }

    fun btnLogOutSetClick() {

        btnLogOut.setOnClickListener(View.OnClickListener {
            authAdapter.logOut()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        })
    }

    fun btnOtvaracieHodinyOnClick() {
        btnOtvaracieHodiny.setOnClickListener {
            activity?.let {
                val dialog = DialogOtvaracieHodiny().onCreateDialog(it)
                dialog.show()
            }
        }
    }

    fun btnSettingsSetClick() {
        btnSettings.setOnClickListener {
            val intent2 = Intent(activity, SettingsActivity::class.java)
            startActivity(intent2)
        }
    }

    fun setUserToTextFields() {
        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")) {
            tvUsername.text = DbAdapterUser.userUser.username
            tvEmail.text = DbAdapterUser.userUser.email
            tvFollowing_FollowersProfileFragment.text = "Sleduje"
            storageAdapter.getProfilePic(DbAdapterUser.userUser.profilePic!!, ivProfile_image)
            tvAdresaPodniku.visibility = View.GONE
            tvPopisPodniku.visibility = View.GONE
            btnOtvaracieHodiny.visibility = View.GONE
            tvTypPodniku.visibility = View.GONE
            btnNapojovylistok.visibility = View.GONE
            btnAddPost.visibility = View.GONE
            tvPostsProfileFragment.visibility = View.GONE
            val titles = ArrayList<String>()
            titles.add(DbAdapterUser.userUser.reviews.toString())
            titles.add(DbAdapterUser.userUser.following.toString())
            setUserViewPager(titles)
        } else {
            tvUsername.text = DbAdapterUser.userFirma.username
            tvEmail.text = DbAdapterUser.userFirma.email
            tvFollowing_FollowersProfileFragment.text = "Sledovatelia"
            storageAdapter.getProfilePic(DbAdapterUser.userFirma.profilePic!!, ivProfile_image)
            tvTypPodniku.text = DbAdapterUser.userFirma.typPodniku
            val titles = ArrayList<String>()
            titles.add(DbAdapterUser.userFirma.reviews.toString())
            titles.add(DbAdapterUser.userFirma.followers.toString())
            setFirmaViewPager(titles)
        }
    }


    fun setUserViewPager(titles: ArrayList<String>) {
        val viewPagerAdapterProfleFragment: ViewPagerAdapter
        fragmentManager?.let {
            viewPagerAdapterProfleFragment = ViewPagerAdapter(it)
            viewPagerAdapterProfleFragment.addManagerProfile(ReviewsFragment(), titles[0])
            viewPagerAdapterProfleFragment.addManagerProfile(FollowersFragment(), titles[1])
            viewPagerProfileFragment.adapter = viewPagerAdapterProfleFragment
            tabsProfileFragment.setupWithViewPager(viewPagerProfileFragment)
        }
    }

    fun setFirmaViewPager(titles: ArrayList<String>) {
        val viewPagerAdapterProfileFragment: ViewPagerAdapter
        fragmentManager?.let {
            viewPagerAdapterProfileFragment = ViewPagerAdapter(it)
            viewPagerAdapterProfileFragment.addManagerProfile(ReviewsFragment(), titles[0])
            viewPagerAdapterProfileFragment.addManagerProfile(FollowersFragment(), titles[1])
            viewPagerProfileFragment.adapter = viewPagerAdapterProfileFragment
            tabsProfileFragment.setupWithViewPager(viewPagerProfileFragment)
        }
    }


}
