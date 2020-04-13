package sk.upjs.ics.android.teazoneinc.Dialogs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.view.*
import sk.upjs.ics.android.teazoneinc.R

class BottomSheetDialogTypPodniku(private var mBottomSheetListener: BottomSheetListener) : BottomSheetDialogFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.bottom_sheet, container, false)

        // To handle clicks
        v.tvKaviaren.setOnClickListener {
            mBottomSheetListener.onOptionClick("Kaviareň")
            dismiss() //dismiss bottom sheet when item click
        }
        v.tvRestauracia.setOnClickListener {
            mBottomSheetListener.onOptionClick("Reštaurácia")
            dismiss()
        }
        v.tvBar.setOnClickListener {
            mBottomSheetListener.onOptionClick("Bar")
            dismiss()
        }
        v.tvKrcma.setOnClickListener {
            mBottomSheetListener.onOptionClick("Krčma")
            dismiss() //dismiss bottom sheet when item click
        }
        v.tvCukraren.setOnClickListener {
            mBottomSheetListener.onOptionClick("Cukráreň")
            dismiss()
        }
        v.tvRychleObcerstvenie.setOnClickListener {
            mBottomSheetListener.onOptionClick("Rýchle občerstvenie")
            dismiss()
        }
        v.tvVinaren.setOnClickListener {
            mBottomSheetListener.onOptionClick("Vináreň")
            dismiss() //dismiss bottom sheet when item click
        }
        v.tvCajovna.setOnClickListener {
            mBottomSheetListener.onOptionClick("Čajovňa")
            dismiss()
        }

        return v
    }

    interface BottomSheetListener{
        fun onOptionClick(text:String)
    }

}