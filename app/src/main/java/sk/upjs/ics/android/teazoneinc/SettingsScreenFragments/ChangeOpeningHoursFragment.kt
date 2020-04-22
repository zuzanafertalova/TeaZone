package sk.upjs.ics.android.teazoneinc.SettingsScreenFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sk.upjs.ics.android.teazoneinc.R

class ChangeOpeningHoursFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_change_opening_hours, container, false)
    }
}