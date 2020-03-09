package sk.upjs.ics.android.teazoneinc.DataHolderClasses.Users

class DataUser{
    var docID : String?=null
    var email:String?=null
    var username : String?=null
    var following: Int?=null
    var reviews : Int? =null
    var followingIDs =ArrayList<String>()

    constructor(docID : String, email:String, username : String, following: Int, reviews:Int){
        this.docID=docID
        this.email=email
        this.username=username
        this.following=following
        this.reviews=reviews
    }
    constructor(){

    }
}