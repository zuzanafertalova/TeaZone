package sk.upjs.ics.android.teazoneinc.SettingsScreenFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import sk.upjs.ics.android.teazoneinc.R
import java.util.*


class ChangeAddressFragment : Fragment() {

    private var placesClient: PlacesClient? = null
    private val placeFields: List<Place.Field> = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS)
    private var places_fragment: AutocompleteSupportFragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_change_address, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPlaces()
        setUpPlaceAutoCompleteStart()
    }

    fun initPlaces() {
        Places.initialize(activity!!, getString(R.string.API_key_places))
        placesClient = Places.createClient(context!!)
    }

    fun setUpPlaceAutoCompleteStart() {
        places_fragment = childFragmentManager
            .findFragmentById(R.id.findDestination_autocomplete) as AutocompleteSupportFragment?
        places_fragment!!.setPlaceFields(placeFields)
        places_fragment!!.setOnPlaceSelectedListener(object : PlaceSelectionListener{
            override fun onPlaceSelected(place: Place) {
                Toast.makeText(activity, "" + place.name, Toast.LENGTH_SHORT).show()
               var changeAddress = place.name            }

            override fun onError(p0: Status) {
                TODO("Not yet implemented")
            }


        })
    }

}