package sk.upjs.ics.android.teazoneinc.Firebase.DataHolderClasses.Post

import java.time.format.DateTimeFormatter

class DataPost{
    var creatorID : String?=null
    var content : String?=null
    var likes : Int?=null
    var comments : Int?=null

    constructor()

    constructor(creatorID:String,content:String,likes:Int,comments:Int){
        this.creatorID=creatorID
        this.content=content
        this.likes=likes
        this.comments=comments
    }
}