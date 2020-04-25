package sk.upjs.ics.android.teazoneinc.ProfileScreenFragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser

import sk.upjs.ics.android.teazoneinc.R

/**
 * A simple [Fragment] subclass.
 */
class FollowersFragment : Fragment() {

    val dbAdapterUser=DbAdapterUser()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFollowersList()
    }

    fun getFollowersList(){

        dbAdapterUser
    }


}
