package sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users

class DataFirma{
    var docID: String?=null
    var email : String?=null
    var username : String?=null
    var following : Int?=null
    var followers : Int?=null
    var ICO: String?=null
    var followingIDs = ArrayList<String>()
    var followersIDs = ArrayList<String>()

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