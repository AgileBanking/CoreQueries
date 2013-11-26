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
            def resp = rest.get("$baseURL$params.sourceURI") {
                accept "application/json"
                contentType "application/json"
                } 
                if (resp.json.class) {resp.json.remove("class")}
                try {
                    resp.json.each {it.remove("class")}
                }
                catch(e1) {}
                answer = ["header":params, "body":resp.json]
        } 
        catch(e) {
            return [error: "$e" ] as JSON// 404 Not Found
        }
//        println "resp: $resp.toString()" 
        
        // Keep Audit in CouchDB
        try {
            if (entities.Component.findByName("Auditor").isActive) {
                // store in the auditdb (CouchDB)
//                answer.header.auditDB="available"
                answer.header.audited="true"
                def restAudit = new RestBuilder()
                def url = entities.Component.findByName("Auditor").baseURL 
                def respAudit = restAudit.put("$url/$params.reqID"){
                    contentType "application/json"
                    json {answer}
                }
            } 
            else {
//                answer.header.auditDB="unavailable"
                answer.header.audited="false"
            }            
        }
        catch(e) {
//            answer.header.auditDB="unavailable"
            answer.header.audited="false"
        }
        return answer as JSON        
    }
}
