package sk.upjs.ics.android.teazoneinc.Firebase.DataHolderClasses.Post

class DataPost{
    var postID : String? = null
    var creatorID : String?=null
    var content : String?=null
    var likesCount : Int?=null
    var commentsCount : Int?=null
    var comments = ArrayList<String>()

    constructor()

    constructor(creatorID:String,content:String,likes:Int,comments:Int){
        this.creatorID=creatorID
        this.content=content
        this.likesCount=likes
        this.commentsCount=comments
    }
}