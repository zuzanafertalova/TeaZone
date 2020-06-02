package sk.upjs.ics.android.teazoneinc.HomeScreenFragments


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.algolia.search.saas.Client
import com.algolia.search.saas.Query
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_fragment_search.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import sk.upjs.ics.android.teazoneinc.Activities.ProfileFromSearchActivity
import sk.upjs.ics.android.teazoneinc.Adapters.SearchResultAdapter
import sk.upjs.ics.android.teazoneinc.Dialogs.BottomSheetFilters
import sk.upjs.ics.android.teazoneinc.R
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class SearchFragment : Fragment(), BottomSheetFilters.BottomSheetListener {

    private var disposables = CompositeDisposable()

    internal var client = Client("085AYVSODT", "22c915636c1f40328cbb89a1da7a531a")
    internal var index = client.getIndex("FirmaUsers_Users")
    internal var  adapter: SearchResultAdapter? = null
    var filterListHere = ArrayList<String>()
    var filterString = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickSearch()
//        setEditText()
        setObservableSearchView()
        setUpRecyclerView()
        btnFiltreOnClick()

    }

    private fun setObservableSearchView(){
        var observableQueryText = Observable.create(ObservableOnSubscribe<String> { emitter ->
            editText_search.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {

                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (!emitter.isDisposed){
                        emitter.onNext(p0.toString())
                    }
                }
            })
        })
            .debounce(500,TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())

        observableQueryText.subscribe(object : Observer<String> {
            override fun onSubscribe(d: @NonNull Disposable?) {
                Log.d("TAAAAAAAAG", "onSubscribe: called.")
                disposables.add(d!!)
            }

            override fun onNext(search: @NonNull String?) {
                search(search!!)
            }

            override fun onError(e: @NonNull Throwable?) {
                Log.e("TAAAAAAAAg", "error", e)
            }

            override fun onComplete() {}
        })
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
            val bottomSheet = BottomSheetFilters(this, filterListHere)
            fragmentManager?.let { it1 -> bottomSheet.show(it1, "BottomSheetFilters") }
        })
    }

    override fun onOptionClick(filterList:ArrayList<String>) {
        setFiltersString(filterList)
    }

    private fun setFiltersString(filterList: ArrayList<String>){
        filterString=""
        filterListHere=filterList
        var kolkoBolo = 0
        val kolkoJe = filterList.size
        filterString="("
        for (filter in filterList){
            kolkoBolo++
            if(kolkoBolo==kolkoJe){
                val singleFilter = "typPodniku:$filter"
                filterString += singleFilter
            }
            else{
                val singleFilter = "typPodniku:$filter OR "
                filterString += singleFilter
            }
        }
        filterString+= ")"
    }

    fun search(content: String) {

        val query = Query(content)
            .setAttributesToRetrieve("username","objectID")
            .setHitsPerPage(50).setFilters(filterString)
        index.searchAsync(query) { jsonObject, e ->
            try {
                jsonObject.let {
                    it?.let {
                        val hits = it.getJSONArray("hits")
                        setValuesToList(hits)
                    }

                }
//                 = jsonObject.getJSONArray("hits")


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

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
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
