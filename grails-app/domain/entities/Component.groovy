package entities
//import grails.plugins.rest.client.RestBuilder
class Component {   
    String      name
    String      appVersion
    boolean     supportAdmin = true
    String      baseURL
    boolean     isActive = true
    String      notes
    Date        dateCreated
    Date        lastUpdated
//    String      recStatus = "Active"
    
    String toString()  {"$name"}
    
    static constraints = {
        name            (unique:true)
        appVersion      (nullable:false)
        supportAdmin    ()
        baseURL         (unique:true)
        isActive        ()
        notes           (nullable:true, maxSize:10240)
        dateCreated     () // implied => editable:false, bindable: false
        lastUpdated     () // implied => editable:false, bindable: false
//        recStatus(editable:false, nullable:false, inList:["Active", "Draft","Deleted"])
    }
    
    def beforeUpdate() {
        // remove unwanted slash from the end of URL
        baseURL= baseURL.trim()
        if (baseURL[-1]=="/") { baseURL=baseURL[0..baseURL.size()-2]}
    }
}
