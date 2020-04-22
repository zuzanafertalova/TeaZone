package sk.upjs.ics.android.teazoneinc.SettingsScreenFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_change_typ_podniku.*
import sk.upjs.ics.android.teazoneinc.Dialogs.BottomSheetTypPodniku
import sk.upjs.ics.android.teazoneinc.R

class ChangeTypPodnikuFragment : Fragment(), BottomSheetTypPodniku.BottomSheetListener{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_change_typ_podniku, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnShowOptions.setOnClickListener{
            val bottomSheet = BottomSheetTypPodniku(this)
            fragmentManager?.let { it1 -> bottomSheet.show(it1, "BottomSheetDialogTypPodniku") }
        }
    }

    override fun onOptionClick(text: String) {
        Log.i("Zvolený typ podniku: ", text)
        tvTypPodnikuChosen.text = text
    }
}