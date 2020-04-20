package sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users

class DataFirma{
    var typPodniku: String?=null
    var docID: String?=null
    var email : String?=null
    var username : String?=null
    var profilePic : String? = null
    var following : Int?=null
    var followers : Int?=null
    var reviews : Int? = null
    var posts = ArrayList<String>()
    var ICO: String?=null
    var followingIDs = ArrayList<String>()
    var followersIDs = ArrayList<String>()

    constructor(docID: String, email : String, username : String?, following : Int, followers : Int,reviews:Int, ICO: String, profilePic : String, typPodniku : String){
        this.docID=docID
        this.email=email
        this.username=username
        this.following=following
        this.followers=followers
        this.reviews=reviews
        this.ICO=ICO
        this.profilePic = profilePic
        this.typPodniku = typPodniku
    }

    constructor(){

    }
}