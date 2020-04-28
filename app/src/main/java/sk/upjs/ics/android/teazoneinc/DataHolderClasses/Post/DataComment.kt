package sk.upjs.ics.android.teazoneinc.DataHolderClasses.Post

import com.google.firebase.firestore.FieldValue
import java.util.*

class DataComment {
        var commentID: String? = null
        var creatorID: String? = null
        var creatorUsername: String? = null
        var content: String? = null
    var timeStamp : Date?= null

        constructor()

        constructor(creatorID: String, creatorUsername: String, content: String) {
            this.creatorID = creatorID
            this.creatorUsername = creatorUsername
            this.content = content
        }

}