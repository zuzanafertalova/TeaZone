package sk.upjs.ics.android.teazoneinc.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_fragment_profile.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbInterface
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.sendData
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataFirma
import sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users.DataUser
import sk.upjs.ics.android.teazoneinc.R

class ProfileFromSearchActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_from_search)

        setData()

    }

    fun setData(){
        val docID = intent.getStringExtra("objectID")
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
    }

    fun setDataFirmaUserToFields(userFirma: DataFirma){
        tvUsername.text = userFirma.username
        tvEmail.text=userFirma.email
        tvFollowing.text=userFirma.following.toString()
        tvFollowers.text=userFirma.followers.toString()
    }
}
