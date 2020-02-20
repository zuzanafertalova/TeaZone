package sk.upjs.ics.android.teazoneinc.HomeScreenFragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home_screen.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser

import sk.upjs.ics.android.teazoneinc.R

class HomeScreenFragment : Fragment() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()
    val dbAdapterPost = DbAdapterPost()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_screen, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        btnSetUsername.setOnClickListener(View.OnClickListener {
//            val username=tvSetUsername.text.toString()
//            authAdapter.currentUser?.let {
//                dbAdapterUser.setUsername(it,username)
//                fragmentSetUserame.view?.visibility = View.GONE
//            }
//        })
        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")) {
            tvDajTu.text=DbAdapterUser.userUser.username
        }
        else{ tvDajTu.text=DbAdapterUser.userFirma.username }
    }

//    fun setUsernameFragment(){
//        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")){
//            if (!DbAdapterUser.userUser.username.equals("")) {
//                fragmentSetUserame.view?.visibility = View.GONE
//            }
//        }
//        else{
//            if (!DbAdapterUser.userFirma.username.equals("")) {
//                fragmentSetUserame.view?.visibility = View.GONE
//            }
//        }
//
//    }


}
