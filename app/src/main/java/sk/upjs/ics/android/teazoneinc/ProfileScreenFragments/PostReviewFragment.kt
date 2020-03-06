package sk.upjs.ics.android.teazoneinc.ProfileScreenFragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_post_review.*

import sk.upjs.ics.android.teazoneinc.R

class PostReviewFragment : Fragment() {

    private val starsButtons  = ArrayList<Button>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_review, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonsToList()
    }

    fun setButtonsToList(){
        starsButtons.add(btnStar1)
        starsButtons.add(btnStar2)
        starsButtons.add(btnStar3)
        starsButtons.add(btnStar4)
        starsButtons.add(btnStar5)
    }

    fun setBtnsOnClick(){

    }



}
