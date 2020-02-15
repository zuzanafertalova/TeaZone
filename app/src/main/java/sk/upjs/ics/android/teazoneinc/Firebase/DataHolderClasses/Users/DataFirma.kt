package sk.upjs.ics.android.teazoneinc.Firebase.DataHolderClasses.Users

class DataFirma{
    var docID: String?=null
    var email : String?=null
    var username : String?=null
    var following : Int?=null
    var followers : Int?=null
    var ICO: String?=null

    constructor(docID: String, email : String, username : String?, following : Int, followers : Int, ICO: String){
        this.docID=docID
        this.email=email
        this.username=username
        this.following=following
        this.followers=followers
        this.ICO=ICO
    }

    constructor(){

    }
}