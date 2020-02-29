package sk.upjs.ics.android.teazoneinc.HomeScreenFragments.SearchFragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import sk.upjs.ics.android.teazoneinc.R

/**
 * A simple [Fragment] subclass.
 */
class ProfileInSearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_in_search, container, false)
    }


}
