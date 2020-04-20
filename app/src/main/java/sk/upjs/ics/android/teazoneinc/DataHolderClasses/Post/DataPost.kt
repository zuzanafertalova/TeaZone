package sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import java.util.*
import kotlin.collections.ArrayList

class DataPost{
    var postID : String? = null
    var creatorID : String?=null
    var creatorUsername : String? = null
    var content : String?=null
    var likesCount : Int?=null
    var commentsCount : Int?=null
    var likes = ArrayList<String>()
    var postPic : String? = null
    var creatorProfilePic : String? = null
    var dateTime: String? = null
    var timeStamp : Date?= null

    constructor()

    constructor(creatorID:String, creatorUsername: String,content:String,likes:Int,comments:Int, creatorProfilePic : String, dateTime:String){
        this.creatorID=creatorID
        this.creatorUsername=creatorUsername
        this.content=content
        this.likesCount=likes
        this.commentsCount=comments
        this.creatorProfilePic = creatorProfilePic
        this.dateTime = dateTime
//        this.timeStamp=FieldValue.serverTimestamp()
    }
}