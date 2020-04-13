package sk.upjs.ics.android.teazoneinc.Activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_set_username.*
import sk.upjs.ics.android.teazoneinc.Dialogs.BottomSheetDialogTypPodniku
import sk.upjs.ics.android.teazoneinc.R


class SetUsernameFragment : Fragment(), BottomSheetDialogTypPodniku.BottomSheetListener{

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_set_username, container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnShowOptions.setOnClickListener{
            val bottomSheet = BottomSheetDialogTypPodniku(this)
            bottomSheet.show(fragmentManager, "BottomSheetDialogTypPodniku")
        }
    }

    override fun onOptionClick(text: String) {
        //change text on each item click
        Log.i("Haha", text)
        tvTypPodnikuChosen.text = text

    }


}