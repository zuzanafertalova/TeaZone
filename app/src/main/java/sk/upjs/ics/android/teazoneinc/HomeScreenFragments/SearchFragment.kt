package sk.upjs.ics.android.teazoneinc.HomeScreenFragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_fragment_search.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterPost
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser

import sk.upjs.ics.android.teazoneinc.R

class SearchFragment : Fragment() {

    val dbAdapterPost= DbAdapterPost()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonPost()
    }

    fun setButtonPost(){
        btnSetPost.setOnClickListener(View.OnClickListener {
            val text =  tvPostContent.text.toString()
            dbAdapterPost.setPost(text)
        })
    }

}
