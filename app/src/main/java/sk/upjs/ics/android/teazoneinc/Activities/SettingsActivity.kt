package sk.upjs.ics.android.teazoneinc.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.db.DbAdapterUser
import android.widget.Toast
import com.google.firebase.firestore.EventListener
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_change_describ.*
import kotlinx.android.synthetic.main.fragment_change_opening_hours.*
import kotlinx.android.synthetic.main.fragment_change_password.*
import kotlinx.android.synthetic.main.fragment_change_typ_podniku.*
import kotlinx.android.synthetic.main.fragment_fragment_profile.*
import kotlinx.android.synthetic.main.fragment_fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_set_username.*
import sk.upjs.ics.android.teazoneinc.Adapters.Firebase.authentication.AuthAdapter
import sk.upjs.ics.android.teazoneinc.Dialogs.DialogOdstranitUcet
import sk.upjs.ics.android.teazoneinc.Dialogs.DialogOtvaracieHodiny
import sk.upjs.ics.android.teazoneinc.R

class SettingsActivity : AppCompatActivity() {

    val authAdapter = AuthAdapter()
    val dbAdapterUser = DbAdapterUser()
    var isButtonChangeUsernameClicked : Boolean = false
    var isButtonChangePasswordClicked : Boolean = false
    var isButtonChangeProfilePicClicked : Boolean = false
    var isButtonChangeDescribClicked : Boolean = false
    var isButtonChangeAddressClicked : Boolean = false
    var isButtonChangeTypPodnikuClicked : Boolean = false
    var isButtonChangeMenuClicked : Boolean = false
    var isButtonChangeOpeningHoursClicked : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        if(dbAdapterUser.getStatusOfLoggedUser().equals("User")){
            buttonChangeDescrib.visibility = View.GONE
            buttonChangeAddress.visibility = View.GONE
            buttonChangeTypPodniku.visibility = View.GONE
            buttonChangeMenu.visibility = View.GONE
            buttonChangeOpeningHours.visibility  =View.GONE
        }

        fragmentChangePassword.view?.visibility = View.GONE
        fragmentChangeUsername.view?.visibility = View.GONE
        fragmentChangeProfilePic.view?.visibility = View.GONE
        fragmentChangeDescrib.view?.visibility = View.GONE
        fragmentChangeAddress.view?.visibility  =View.GONE
        fragmentChangeTypPodniku.view?.visibility = View.GONE
        fragmentChangeMenu.view?.visibility = View.GONE
        fragmentChangeOpeningHours.view?.visibility = View.GONE

        btnOpenChangeFragmentSet()
        btnSetUsernameClick()
        buttonDeleteAccountOnClick()
        btnSetTypPodnikuClick()
//        btnSetDescribClick()
    }

    private fun btnOpenChangeFragmentSet() {
        buttonChangePassword.setOnClickListener(View.OnClickListener {
            if(isButtonChangePasswordClicked == false){
                isButtonChangePasswordClicked = true
                fragmentChangePassword.view?.visibility = View.VISIBLE
                btnChangePasswordSet()
            }else{
                isButtonChangePasswordClicked = false
                fragmentChangePassword.view?.visibility = View.GONE
            }

        })
        buttonChangeUsername.setOnClickListener(View.OnClickListener {
            if(isButtonChangeUsernameClicked == false){
                isButtonChangeUsernameClicked = true
                fragmentChangeUsername.view?.visibility = View.VISIBLE
            }else{
                isButtonChangeUsernameClicked = false
                fragmentChangeUsername.view?.visibility = View.GONE
            }
        })
        buttonChangeProfilePic.setOnClickListener(View.OnClickListener {
            if(isButtonChangeProfilePicClicked == false){
                isButtonChangeProfilePicClicked = true
                fragmentChangeProfilePic.view?.visibility = View.VISIBLE
            }else{
                isButtonChangeProfilePicClicked = false
                fragmentChangeProfilePic.view?.visibility = View.GONE
            }
        })
        buttonChangeDescrib.setOnClickListener(View.OnClickListener {
            if(isButtonChangeDescribClicked == false){
                isButtonChangeDescribClicked = true
                fragmentChangeDescrib.view?.visibility = View.VISIBLE
                btnSetDescribClick()
            }else{
                isButtonChangeDescribClicked = false
                fragmentChangeDescrib.view?.visibility = View.GONE
            }
        })
        buttonChangeAddress.setOnClickListener(View.OnClickListener {
            if(isButtonChangeAddressClicked == false){
                isButtonChangeAddressClicked = true
                fragmentChangeAddress.view?.visibility = View.VISIBLE
            }else{
                isButtonChangeAddressClicked = false
                fragmentChangeAddress.view?.visibility = View.GONE
            }
        })
        buttonChangeTypPodniku.setOnClickListener(View.OnClickListener {
            if(isButtonChangeTypPodnikuClicked == false){
                isButtonChangeTypPodnikuClicked = true
                fragmentChangeTypPodniku.view?.visibility = View.VISIBLE
            }else{
                isButtonChangeTypPodnikuClicked = false
                fragmentChangeTypPodniku.view?.visibility = View.GONE
            }
        })
        buttonChangeMenu.setOnClickListener(View.OnClickListener {
            if(isButtonChangeMenuClicked == false){
                isButtonChangeMenuClicked = true
                fragmentChangeMenu.view?.visibility = View.VISIBLE
            }else{
                isButtonChangeMenuClicked = false
                fragmentChangeMenu.view?.visibility = View.GONE
            }
        })
        buttonChangeOpeningHours.setOnClickListener(View.OnClickListener {
            if(isButtonChangeOpeningHoursClicked == false){
                isButtonChangeOpeningHoursClicked = true
                fragmentChangeOpeningHours.view?.visibility = View.VISIBLE
            }else{
                isButtonChangeOpeningHoursClicked = false
                fragmentChangeOpeningHours.view?.visibility = View.GONE
            }
        })


    }

    private fun buttonDeleteAccountOnClick() {
        buttonDeleteAccount.setOnClickListener {
                val inflater = layoutInflater
                val dialog = DialogOdstranitUcet().onCreateDialog(this, inflater)

                dialog.show()
        }
    }

    private fun btnChangePasswordSet() {
        btnChangePassword.setOnClickListener(View.OnClickListener {
            if (tvCurrPassword.text.isNotEmpty() && tvNewPassword.text.isNotEmpty() && tvNewPasswordConfirm.text.isNotEmpty()) {
                authAdapter.reauthenticate(
                    tvCurrPassword.text.toString(),
                    EventListener { isTrue, _ ->
                        if (isTrue!!) {
                            if (tvNewPassword.text.toString().equals(tvNewPasswordConfirm.text.toString())) {
                                authAdapter.changePassword(
                                    tvNewPassword.text.toString(),
                                    EventListener { isTrue, _ ->
                                        if (isTrue!!) {
                                            Toast.makeText(this, "Heslo úspešne zmenené.", Toast.LENGTH_SHORT).show()
                                            fragmentChangePassword.view?.visibility = View.GONE
                                        }
                                    })
                            } else {
                                Toast.makeText(this, "Heslá sa nezhodujú.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "Súčasné heslo je nesprávne.", Toast.LENGTH_SHORT).show()
                        }
                        fragmentChangePassword.view?.visibility = View.GONE
                    })
            }

        })
    }

    private fun btnSetTypPodnikuClick(){
        btnSaveTypPodniku.setOnClickListener{
            if(tvTypPodnikuChosen.text.isNotEmpty()){
                dbAdapterUser.setTypPodniku(authAdapter.currentUser!!,tvTypPodnikuChosen.text.toString())
                fragmentChangeTypPodniku.view?.visibility = View.GONE
            }
        }
    }

    private fun btnSetUsernameClick(){
        btnSetUsername.setOnClickListener(View.OnClickListener {
            if (tvSetUsername.text.isNotEmpty()){
                dbAdapterUser.changeUsername(authAdapter.currentUser!!,tvSetUsername.text.toString())
                fragmentChangeUsername.view?.visibility = View.GONE

            }
        })
    }
//    private fun btnSetOpeningHoursClick(){
//        btnSetOpeningHours.setOnClickListener{
//            if (tiChangeOpeningHours.text.isNotEmpty()){
//
//            }
//        }
//    }

    private fun btnSetDescribClick(){
        btnSetDescrib.setOnClickListener{
            if(tiChangeDescrib.text.isNotEmpty()){
                fragmentChangeDescrib.view?.visibility = View.GONE
                if (tiChangeDescrib.text.isNotEmpty()){
                    dbAdapterUser.setDescription(authAdapter.currentUser!!,tiChangeDescrib.text.toString())
                }
                else Toast.makeText(this,"Vyplnte pole prosím",Toast.LENGTH_SHORT).show()
            }
        }
    }

}
