package sk.upjs.ics.android.teazoneinc.DataHolderClasses.Review

class DataReview{
    var reviewID : String? = null
    var creatorID : String?=null
    var content : String?=null
    var rating : Int = 0
//    var timeStamp : FieldValue?= null

    constructor()

    constructor(creatorID:String,content:String,rating : Int){
        this.creatorID=creatorID
        this.content=content
        this.rating = rating
//        this.timeStamp=FieldValue.serverTimestamp()
    }
}