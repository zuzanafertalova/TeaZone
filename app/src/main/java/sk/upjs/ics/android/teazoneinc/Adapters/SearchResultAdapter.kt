package sk.upjs.ics.android.teazoneinc.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import sk.upjs.ics.android.teazoneinc.R


class SearchResultAdapter(private val onResultsClick: OnResultsClick) : RecyclerView.Adapter<SearchResultAdapter.XdrHolder>() {
    private var results = ArrayList<String>()
    fun setNewData(newData: ArrayList<String>) {
        results = newData
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XdrHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return XdrHolder(
            view,
            onResultsClick
        )
    }
    override fun getItemCount(): Int {
        return results.size
    }
    override fun onBindViewHolder(holder: XdrHolder, position: Int) {
        holder.bindData(results[position])
    }
    class XdrHolder(itemView: View, private val onResultsClick: OnResultsClick): RecyclerView.ViewHolder(itemView) {
        fun bindData(prispevok: String) {
            itemView.menoPodniku.text = prispevok
            itemView.setOnClickListener {
                onResultsClick.setOnProfileClickListener(prispevok)
            }
        }
    }
    interface OnResultsClick {
        fun setOnProfileClickListener(meno:String?)
    }
}