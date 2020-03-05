package sk.upjs.ics.android.teazoneinc.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import sk.upjs.ics.android.teazoneinc.R


class SearchResultAdapter(private val onResultsClick: OnResultsClick) : RecyclerView.Adapter<SearchResultAdapter.XdrHolder>() {
    private var usernamesList = ArrayList<String>()
    private var objectIDsList = ArrayList<String>()
    fun setNewData(usernames: ArrayList<String>, objectIDs: ArrayList<String>) {
        usernamesList = usernames
        objectIDsList = objectIDs
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): XdrHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return XdrHolder(view, onResultsClick)
    }
    override fun getItemCount(): Int {
        return usernamesList.size
    }
    override fun onBindViewHolder(holder: XdrHolder, position: Int) {
        holder.bindData(usernamesList[position],objectIDsList[position])
    }
    class XdrHolder(itemView: View, private val onResultsClick: OnResultsClick): RecyclerView.ViewHolder(itemView) {
        fun bindData(username: String,objectID:String) {
            itemView.menoPodniku.text = username
            itemView.setOnClickListener {
                onResultsClick.setOnProfileClickListener(objectID)
            }
        }
    }
    interface OnResultsClick {
        fun setOnProfileClickListener(docID:String?)
    }
}