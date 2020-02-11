package sk.upjs.ics.android.teazoneinc.Firebase.User

class DataUser{
    var docID : String?=null
    var email:String?=null
    var username : String?=null
    var following: Int?=null

    constructor(docID : String, email:String, username : String, following: Int){
        this.docID=docID
        this.email=email
        this.username=username
        this.following=following
    }
    constructor(){

    }
}