@artifact.package@class @artifact.name@ {   
    
    Date        dateCreated
    Date        lastUpdated
//    String      recStatus = "Active"
    
    static mapping = {
//        recStatus index:'RecordStatus_Idx'
    }    
    
    static constraints = {
        
        dateCreated() // implied => editable:false, bindable: false
        lastUpdated() // implied => editable:false, bindable: false
//        recStatus(editable:false, nullable:false, inList:["Active", "Draft","Deleted"])
    }
}
