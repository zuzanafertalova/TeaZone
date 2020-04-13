package sk.upjs.ics.android.teazoneinc.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_settings_user.*
import kotlinx.android.synthetic.main.fragment_change_password.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.R
import java.util.*

class SettingsActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_user)

        fragmentChangePassword.view?.visibility = View.GONE
        btnOpenChangeFragmentSet()
    }

    private fun btnOpenChangeFragmentSet(){
        buttonChangePassword.setOnClickListener(View.OnClickListener {
            fragmentChangePassword.view?.visibility = View.VISIBLE
            btnChangePasswordSet()
        })
    }

    private fun btnChangePasswordSet(){
        btnChangePassword.setOnClickListener(View.OnClickListener {
            if (tvCurrPassword.text.isNotEmpty() && tvNewPassword.text.isNotEmpty() && tvNewPasswordConfirm.text.isNotEmpty()){
                authAdapter.reauthenticate(tvCurrPassword.text.toString(), EventListener{ isTrue, _ ->
                    if (isTrue!!){
                        if (tvNewPassword.text.toString().equals(tvNewPasswordConfirm.text.toString())){
                            authAdapter.changePassword(tvNewPassword.text.toString(), EventListener{isTrue, _ ->
                                if (isTrue!!){
                                    Toast.makeText(this, "Heslo uspesne zmenene", Toast.LENGTH_SHORT).show()
                                    fragmentChangePassword.view?.visibility = View.GONE
                                }
                            })
                        }
                        else{
                            Toast.makeText(this, "Hesla sa nezhoduju", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(this, "Sucastne heslo je nespravne", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        })
    }


}
