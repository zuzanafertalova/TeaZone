package sk.upjs.ics.android.teazoneinc.SettingsScreenFragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_change_menu.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.Storage.StorageAdapter
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import sk.upjs.ics.android.teazoneinc.R

class ChangeMenuFragment : Fragment() {
    private var fileUri : Uri? = null
    private val storageAdapter=StorageAdapter()
    private val dbAdapterUser = DbAdapterUser()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_change_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnLoadFileSetClick()
        btnUploadFileSetClick()
    }


    private fun btnLoadFileSetClick(){
        btnLoadFile.setOnClickListener {
            openPDFChooser()
        }

    }

    private fun openPDFChooser(){
        var intent = Intent()
        intent.setType("application/pdf")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Vyberte PDF"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==1 && resultCode== Activity.RESULT_OK && data !=null && data.data != null){
            fileUri=data.data!!
            tvLoadedFile.text = "Súbor bol nahratý."
        }
    }

    private fun btnUploadFileSetClick(){
        btnUploadFile.setOnClickListener{
            if (fileUri==null){
                Toast.makeText(context,"Prosím nahrajte súbor",Toast.LENGTH_SHORT).show()
            }
            else{
                storageAdapter.uploadPDFFile(fileUri!!, EventListener{menuName,_->
                    if (menuName==null){
                        Toast.makeText(context,"Nastala chyba!",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        fragmentChangeMenu.view?.visibility = View.GONE
                        Toast.makeText(context,"Úspešne nahraté.",Toast.LENGTH_SHORT).show()
                        dbAdapterUser.updateMenu(menuName!!)
                    }
                })
            }
        }
    }
}