package sk.upjs.ics.android.teazoneinc.Dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_filters.*
import kotlinx.android.synthetic.main.bottom_sheet_filters.view.*
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.SearchFragment
import sk.upjs.ics.android.teazoneinc.R

class BottomSheetFilters(private var mBottomSheetListener: SearchFragment) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v =  inflater.inflate(R.layout.bottom_sheet_filters, container, false)

        // To handle clicks
        v.tvKaviarenFilter.setOnClickListener {
            tvKaviarenFilter.setBackgroundResource(R.drawable.search_rectangle_green)
        }
        v.tvRestauraciaFilter.setOnClickListener {
            tvRestauraciaFilter.setBackgroundResource(R.drawable.search_rectangle_green)
        }
        v.tvBarFilter.setOnClickListener {
            tvBarFilter.setBackgroundResource(R.drawable.search_rectangle_green)
        }
        v.tvKrcmaFilter.setOnClickListener {
            tvKrcmaFilter.setBackgroundResource(R.drawable.search_rectangle_green)
        }
        v.tvCukrarenFilter.setOnClickListener {
            tvCukrarenFilter.setBackgroundResource(R.drawable.search_rectangle_green)
        }
        v.tvRychleObcerstvenieFilter.setOnClickListener {
            tvRychleObcerstvenieFilter.setBackgroundResource(R.drawable.search_rectangle_green)
        }
        v.tvVinarenFilter.setOnClickListener {
            tvVinarenFilter.setBackgroundResource(R.drawable.search_rectangle_green)
        }
        v.tvCajovnaFilter.setOnClickListener {
            tvCajovnaFilter.setBackgroundResource(R.drawable.search_rectangle_green)
        }

        v.btnConfirmFilters.setOnClickListener{
            dismiss()
        }
        return v
    }
}