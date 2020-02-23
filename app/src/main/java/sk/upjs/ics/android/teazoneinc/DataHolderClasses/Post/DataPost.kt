package sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue

class DataPost{
    var postID : String? = null
    var creatorID : String?=null
    var content : String?=null
    var likesCount : Int?=null
    var commentsCount : Int?=null
    var comments = ArrayList<String>()
    var timeStamp : FieldValue?= null

    constructor()

    constructor(creatorID:String,content:String,likes:Int,comments:Int){
        this.creatorID=creatorID
        this.content=content
        this.likesCount=likes
        this.commentsCount=comments
        this.timeStamp=FieldValue.serverTimestamp()
    }
}