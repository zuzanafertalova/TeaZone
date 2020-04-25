package sk.upjs.ics.android.teazoneinc.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_profile_from_search.*
import kotlinx.android.synthetic.main.fragment_fragment_profile.*
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvEmail
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvUsername
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.sendData
import sk.upjs.ics.android.teazoneinc.Adapters.ViewPagerAdapter
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataFirma
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataUser
import sk.upjs.ics.android.teazoneinc.Dialogs.BottomSheetPostReview
import sk.upjs.ics.android.teazoneinc.Dialogs.DialogMenu
import sk.upjs.ics.android.teazoneinc.Dialogs.DialogOtvaracieHodiny
import sk.upjs.ics.android.teazoneinc.ProfileScreenFragments.*
import sk.upjs.ics.android.teazoneinc.R
import kotlinx.android.synthetic.main.activity_profile_from_search.btnNapojovylistok as btnNapojovylistok1
import kotlinx.android.synthetic.main.fragment_fragment_profile.tvPopisPodniku as tvPopisPodniku1

class ProfileFromSearchActivity : AppCompatActivity(), BottomSheetPostReview.BottomSheetListener {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()
    val storageAdapter = StorageAdapter()
    var isBtnClicked: Boolean = false

    var userUser = DataUser()
    var userFirma = DataFirma()

    companion object {
        lateinit var docID: String
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_from_search)
        docID = intent.getStringExtra("objectID")
        setData()
        setOnClickBtnWriteReview()
        btnOtvaracieHodiny2OnClick()
        buttonMenu2OnClick()
    }

    private fun setData() {
        dbAdapterUser.setProfileData(docID, object : sendData {
            override fun send(user: DataUser?, userFir: DataFirma?) {
                user?.let {
                    userUser = it
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

    private fun setBtnFollowIfFollowed() {
        if (userFirma.followersIDs.contains(authAdapter.currentUser?.uid)) {
            isBtnClicked = true
            btnFollow.background = resources.getDrawable(R.drawable.ic_tea_cup_onclick)
            textView_sledovat.text = "Sledované"
        }
    }

    private fun setDataUserUserToFields() {
        btnFollow.visibility = View.GONE
        btnWriteReview.visibility = View.GONE
        tvPosts.visibility = View.GONE
        textView10.visibility = View.GONE
        btnNapojovylistok.visibility = View.GONE
        tvTypPodniku2.visibility = View.GONE
        btnOtvaracieHodiny2.visibility = View.GONE
        tvPopisPodniku.visibility = View.GONE
        tvAdresaPodniku2.visibility = View.GONE
        textView_sledovat.visibility = View.GONE

        storageAdapter.getProfilePic(userUser.profilePic!!,ivProfilePicFromSearch)
        tvUsername.text = userUser.username
        tvEmail.text = userUser.email
        tvFollowing_Followers.text = "Sleduje"
        val titles = ArrayList<String>()
        titles.add("0")
        titles.add(userUser.following.toString())
        setUserViewPager(titles)
    }

    private fun setDataFirmaUserToFields() {

        storageAdapter.getProfilePic(userFirma.profilePic!!,ivProfilePicFromSearch)
        tvTypPodniku2.text = userFirma.typPodniku
        tvUsername.text = userFirma.username
        tvEmail.text = userFirma.email
        tvFollowing_Followers.text = "Sledovatelia"
        if (!userFirma.description.isNullOrEmpty()){
            tvPopisPodniku.text=userFirma.description
        }

        val titles = ArrayList<String>()
        titles.add("0")
        titles.add("0")
        titles.add(userFirma.followers.toString())
        setFirmaViewPager(titles)
        setOnClickBtnFollow()
    }


    private fun setOnClickBtnWriteReview() {
        btnWriteReview.setOnClickListener(View.OnClickListener {
            val bottomSheet = BottomSheetPostReview(this)
            bottomSheet.show(supportFragmentManager, "BottomSheetDialogPostReview")
        })
    }


    private fun btnOtvaracieHodiny2OnClick() {
        btnOtvaracieHodiny2.setOnClickListener {
            val inflater = layoutInflater
            val dialog = DialogOtvaracieHodiny(inflater).onCreateDialog(this, inflater,  userFirma.openHours)
            dialog.show()
        }
    }

    fun buttonMenu2OnClick() {
        btnNapojovylistok.setOnClickListener {
            val inflater = layoutInflater
            val dialog = DialogMenu().onCreateDialog(this)
            dialog.show()
        }
    }

    private fun setOnClickBtnFollow() {
        btnFollow.setOnClickListener(View.OnClickListener {
            if (isBtnClicked == true) {
                isBtnClicked = false
                dbAdapterUser.removeFollower(userFirma, docID, authAdapter.currentUser?.uid)
                btnFollow.background = resources.getDrawable(R.drawable.ic_tea_cup)
                textView_sledovat.text = "Sledovať"
            } else {
                dbAdapterUser.addFollower(docID, authAdapter.currentUser?.uid)
                isBtnClicked = true
                textView_sledovat.text = "Sledované"
                btnFollow.background = resources.getDrawable(R.drawable.ic_tea_cup_onclick)
            }
        })
    }


    private fun setUserViewPager(titles: ArrayList<String>) {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addManagerProfile(UserReviewsFragment(), titles[0])
        viewPagerAdapter.addManagerProfile(FollowersFragment(), titles[1])
        viewPagerProfile.adapter = viewPagerAdapter
        tabsProfile.setupWithViewPager(viewPagerProfile)
    }

    private fun setFirmaViewPager(titles: ArrayList<String>) {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addManagerProfile(FirmaReviewsFragment(), titles[0])
        viewPagerAdapter.addManagerProfile(PostsFragment(), titles[1])
        viewPagerAdapter.addManagerProfile(FollowersFragment(), titles[2])
        viewPagerProfile.adapter = viewPagerAdapter
        tabsProfile.setupWithViewPager(viewPagerProfile)
    }

    override fun onOptionClick(text: String) {
        TODO("Not yet implemented")
    }
}
