package sk.upjs.ics.android.teazoneinc.Dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_filters.*
import kotlinx.android.synthetic.main.bottom_sheet_filters.view.*
import sk.upjs.ics.android.teazoneinc.HomeScreenFragments.SearchFragment
import sk.upjs.ics.android.teazoneinc.R

class BottomSheetFilters(private var mBottomSheetListener: SearchFragment) : BottomSheetDialogFragment() {

    var filterList = ArrayList<String>()
    var isKaviarenClicked = false
    var isRestauraciaClicked = false
    var isBarClicked = false
    var isKrcmaClicked = false
    var isCukrarenClicked = false
    var isRychleObcerstvenieClicked = false
    var isVinarenClicked=false
    var isCajovnaClicked = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v =  inflater.inflate(R.layout.bottom_sheet_filters, container, false)

        // To handle clicks
        v.tvKaviarenFilter.setOnClickListener {
            if (isKaviarenClicked==false){
                isKaviarenClicked = true
                tvKaviarenFilter.setBackgroundResource(R.drawable.search_rectangle_green)
                addFilter("Kaviareň")
            }
            else{
                isKaviarenClicked = false
                removeFilter("Kaviareň")
            }
        }
        v.tvRestauraciaFilter.setOnClickListener {
            if (isRestauraciaClicked==false){
                isRestauraciaClicked = true
                tvRestauraciaFilter.setBackgroundResource(R.drawable.search_rectangle_green)
                addFilter("Reštaurácia")
            }
            else{
                isRestauraciaClicked = false
                removeFilter("Reštaurácia")
            }
        }
        v.tvBarFilter.setOnClickListener {
            if (isBarClicked == false){
                isBarClicked = true
                tvBarFilter.setBackgroundResource(R.drawable.search_rectangle_green)
                addFilter("Bar")
            }
            else{
                isBarClicked = false
                removeFilter("Bar")
            }
        }
        v.tvKrcmaFilter.setOnClickListener {
            if (isKrcmaClicked == false){
                isKrcmaClicked = true
                tvKrcmaFilter.setBackgroundResource(R.drawable.search_rectangle_green)
                addFilter("Krčma")
            }
            else{
                isKrcmaClicked = false
                removeFilter("Krčma")
            }
        }
        v.tvCukrarenFilter.setOnClickListener {
            if (isCukrarenClicked == false){
                isCukrarenClicked = true
                tvCukrarenFilter.setBackgroundResource(R.drawable.search_rectangle_green)
                addFilter("Cukráreň")
            }
            else{
                isCukrarenClicked = false
                removeFilter("Cukráreň")
            }
        }
        v.tvRychleObcerstvenieFilter.setOnClickListener {
            if (isRychleObcerstvenieClicked == false){
                isRychleObcerstvenieClicked = true
                tvRychleObcerstvenieFilter.setBackgroundResource(R.drawable.search_rectangle_green)
                addFilter("Rýchle občerstvenie")
            }
            else{
                isRychleObcerstvenieClicked = false
                removeFilter("Rýchle občerstvenie")
            }
        }
        v.tvVinarenFilter.setOnClickListener {
            if (isVinarenClicked==false){
                isVinarenClicked=true
                tvVinarenFilter.setBackgroundResource(R.drawable.search_rectangle_green)
                addFilter("Vináreň")
            }
            else{
                isVinarenClicked=false
                removeFilter("Vináreň")
            }
        }
        v.tvCajovnaFilter.setOnClickListener {
            if (isCajovnaClicked == false){
                isCajovnaClicked = true
                tvCajovnaFilter.setBackgroundResource(R.drawable.search_rectangle_green)
                addFilter("Čajovňa")
            }
            else{
                isCajovnaClicked = false
                removeFilter("Čajovňa")
            }
        }

        v.btnConfirmFilters.setOnClickListener{
            mBottomSheetListener.onOptionClick(filterList)
            dismiss()
        }
        return v
    }

    fun addFilter(filter:String){
        filterList.add(filter)
    }

    fun removeFilter(filter:String){
        filterList.remove(filter)
    }

    interface BottomSheetListener {
        fun onOptionClick(filterList:ArrayList<String>)
    }
}