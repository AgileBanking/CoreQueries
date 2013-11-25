package admin
import grails.plugins.rest.client.RestBuilder
import grails.converters.*

class RenderService {
    static transactional = false
    
    def serviceMethod(params) {
        def comp1 = entities.Component.findByName(params.sourceComponent)
        def baseURL = comp1.baseURL
        params.reqID = UUID.randomUUID().toString()
        params.timestamp = new Date().toString() 
        def answer = ""
        def rest = new RestBuilder()
        try {
//            println baseURL + "$params.sourceURI"
            def resp = rest.get(baseURL + "$params.sourceURI") {
                accept "application/json"
                contentType "application/json"
                } 
                answer = ["header":params, "body":resp.json]
        } 
        catch(e) {
            return [error: "$e.cause.message" ] as JSON// 404 Not Found
        }
//        println "resp: $resp.toString()" 
        
        // Keep Audit in CouchDB
        try {
            def comp2 = entities.Component.findByName("Auditor")
            if (comp2 && entities.Component.findByName("Auditor").isActive) {
                // store in the auditdb (CouchDB)
                answer.header.auditDB="available"
                answer.header.audited="true"
                def restAudit = new RestBuilder()
                def url = entities.Component.findByName("Auditor").baseURL 
//                println answer as JSON
                print "Auditdb Url: $url"
                def respAudit = restAudit.post(url){
                    contentType "application/json"
                    json {answer}
                }
//                println "respAudit.json: $respAudit.json" 
                answer.header.reqID = "$respAudit.json.id"
//                println "id=$respAudit.json.id, $answer.header.reqID" 
            } 
            else { 
                answer.header.auditDB="available"
                answer.header.audited="false"
            }            
        }
        catch(e) {
            answer.header.auditDB="unavailable"
            answer.header.audited="false"
        }
        return answer as JSON        
    }
}
