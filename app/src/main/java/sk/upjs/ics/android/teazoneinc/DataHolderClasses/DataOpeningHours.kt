package sk.upjs.ics.android.teazoneinc.DataHolderClasses

open class DataOpeningHours {

    var creatorID: String? = null
    var po = HashMap<String,String>()
    var ut = HashMap<String,String>()
    var st = HashMap<String,String>()
    var št = HashMap<String,String>()
    var pi = HashMap<String,String>()
    var so = HashMap<String,String>()
    var ne = HashMap<String,String>()

    constructor(creatorID : String,
                Po : HashMap<String,String>,
                Ut : HashMap<String,String>,
                St : HashMap<String,String>,
                Št : HashMap<String,String>,
                Pi : HashMap<String,String>,
                So : HashMap<String,String>,
                Ne : HashMap<String,String>){
        this.creatorID=creatorID
        this.po = Po
        this.ut=Ut
        this.st=St
        this.št= Št
        this.pi = Pi
        this.so=So
        this.ne = Ne
    }
    constructor(){

    }
}