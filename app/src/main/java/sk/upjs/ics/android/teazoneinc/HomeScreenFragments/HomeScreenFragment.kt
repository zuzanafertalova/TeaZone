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


        if (dbAdapterUser.getStatusOfLoggedUser().equals("User")) {
            tvDajTu.text=DbAdapterUser.userUser.username
        }
        else{ tvDajTu.text=DbAdapterUser.userFirma.username }
    }



}
