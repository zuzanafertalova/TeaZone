package sk.upjs.ics.android.teazoneinc.HomeScreenFragments


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.algolia.search.saas.Client
import com.algolia.search.saas.Query
import kotlinx.android.synthetic.main.fragment_fragment_search.*
import org.json.JSONArray
import org.json.JSONException
import sk.upjs.ics.android.teazoneinc.Activities.ProfileFromSearchActivity
import sk.upjs.ics.android.teazoneinc.Adapters.SearchResultAdapter
import sk.upjs.ics.android.teazoneinc.Dialogs.BottomSheetFilters
import sk.upjs.ics.android.teazoneinc.R
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : Fragment(), BottomSheetFilters.BottomSheetListener {

    internal var client = Client("085AYVSODT", "22c915636c1f40328cbb89a1da7a531a")
    internal var index = client.getIndex("FirmaUsers_Users")
    internal var  adapter: SearchResultAdapter? = null
    var filterString = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickSearch()
        setEditText()
        setUpRecyclerView()
        btnFiltreOnClick()

    }

    private fun setUpRecyclerView() {
        searchingRecyclerView.layoutManager = LinearLayoutManager(context)
        adapter = SearchResultAdapter(object : SearchResultAdapter.OnResultsClick{
            override fun setOnProfileClickListener(objectID: String?) {
                editText_search.clearFocus()
                val intent = Intent(context, ProfileFromSearchActivity::class.java)
                intent.putExtra("objectID",objectID)
                startActivity(intent)
            }
        })
        searchingRecyclerView.adapter = adapter

    }

    fun setEditText(){
        editText_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                search(p0.toString())
            }
        })
    }

    private fun setOnClickSearch(){
        editText_search.setOnFocusChangeListener(OnFocusChangeListener { _ , hasFocus ->
            if(hasFocus){
                editText_search.background = resources.getDrawable(R.drawable.search_rectangle_green)
            }
            else {
                editText_search.background = resources.getDrawable(R.drawable.search_rectangle_white)
            }
        })
    }

    fun btnFiltreOnClick() {
        btnFiltre.setOnClickListener(View.OnClickListener {
            val bottomSheet = BottomSheetFilters(this)
            fragmentManager?.let { it1 -> bottomSheet.show(it1, "BottomSheetFilters") }
        })
    }

    override fun onOptionClick(filterList:ArrayList<String>) {
        setFiltersString(filterList)
    }

    private fun setFiltersString(filterList: ArrayList<String>){
        filterString="("
        for (filter in filterList){
            val singleFilter = "typPodniku:$filter OR "
            filterString += singleFilter
        }
        filterString.removeRange(filterString.length-4, filterString.length-1)
        filterString+= ")"
    }

    fun search(content: String) {
        val query = Query(content)
            .setAttributesToRetrieve("username","objectID")
            .setHitsPerPage(50).setFilters(filterString)
        index.searchAsync(query) { jsonObject, e ->
            try {
                val hits = jsonObject!!.getJSONArray("hits")
                setValuesToList(hits)

            } catch (ex: JSONException) {
                ex.printStackTrace()
            }
        }
    }

    @Throws(JSONException::class)
    fun setValuesToList(hits: JSONArray) {
        val usernames = ArrayList<String>()
        val objectIDs = ArrayList<String>()
        for (i in 0 until hits.length()) {
            val jsonObject = hits.getJSONObject(i)
            val username = jsonObject.getString("username")
            val objectID = jsonObject.getString("objectID")
            usernames.add(username)
            objectIDs.add(objectID)
        }
        adapter?.setNewData(usernames,objectIDs)
    }


//    fun setEditText(){
//        editText_search.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                search(p0.toString())
//            }
//        })
//    }
//

//    fun search(content: String) {
//        val query = Query(content)
//            .setAttributesToRetrieve("username")
//            .setHitsPerPage(50)
//        index.searchAsync(query) { jsonObject, e ->
//            try {
//                val hits = jsonObject!!.getJSONArray("hits")
//                setValuesToList(hits)
//
//            } catch (ex: JSONException) {
//                ex.printStackTrace()
//            }
//        }
//    }
//
//    @Throws(JSONException::class)
//    fun setValuesToList(hits: JSONArray) {
//        val list = ArrayList<String>()
//        for (i in 0 until hits.length()) {
//            val jsonObject = hits.getJSONObject(i)
//            val username = jsonObject.getString("username")
//            list.add(username)
//        }
//        context?.let {
//            val arrayAdapter = ArrayAdapter(it,R.layout.support_simple_spinner_dropdown_item,list)
//            list_view.adapter = arrayAdapter
//        }
//    }








//    fun setButtonPost(){
//        btnSetPost.setOnClickListener(View.OnClickListener {
//            val text =  tvPostContent.text.toString()
//            dbAdapterPost.setPost(text)
//        })
//    }

}
