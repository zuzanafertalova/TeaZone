package sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users

import android.bluetooth.BluetoothProfile

class DataUser{
    var docID : String?=null
    var email:String?=null
    var username : String?=null
    var profilePic : String? = null
    var following: Int?=null
    var reviews : Int? =null
    var followingIDs =ArrayList<String>()

    constructor(docID : String, email:String, username : String, following: Int, reviews:Int, profilePic : String){
        this.docID=docID
        this.email=email
        this.username=username
        this.following=following
        this.reviews=reviews
        this.profilePic = profilePic
    }
    constructor(){

    }
}