package sk.upjs.ics.android.teazoneinc.SettingsScreenFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_settings_user.*
import kotlinx.android.synthetic.main.fragment_change_password.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter

import sk.upjs.ics.android.teazoneinc.R

/**
 * A simple [Fragment] subclass.
 */
class ChangePasswordFragment : Fragment() {

    val authAdapter = AuthAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? {
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun btnChangePasswordSet(){
        btnChangePassword.setOnClickListener(View.OnClickListener {
            if (tvCurrPassword.text.isNotEmpty() && tvNewPassword.text.isNotEmpty() && tvNewPasswordConfirm.text.isNotEmpty()){
                authAdapter.reauthenticate(tvCurrPassword.text.toString(), EventListener{ isTrue , _ ->
                    if (isTrue!!){
                        if (tvNewPassword.text.equals(tvNewPasswordConfirm.text)){
                            authAdapter.changePassword(tvNewPassword.text.toString(), EventListener{isTrue, _ ->
                                if (isTrue!!){
                                    Toast.makeText(context, "Heslo uspesne zmenene", Toast.LENGTH_SHORT).show()
                                    fragmentChangePassword.view?.visibility = View.GONE
                                }
                            })
                        }
                        else{
                            Toast.makeText(context, "Hesla sa nezhoduju", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(context, "Sucastne heslo je nespravne", Toast.LENGTH_SHORT).show()
                    }
                })
            }

        })
    }

}
