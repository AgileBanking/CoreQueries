package admin
import grails.plugins.rest.client.RestBuilder
import grails.converters.*

class RenderService {
    static transactional = false
    
    def serviceMethod(params) {
        def comp1 = entities.Component.findByName(params.sourceComponent)
        def baseURL = comp1.baseURL
        def rest = new RestBuilder()
        def resp = rest.get(baseURL + "$params.sourceURI") {
            accept "application/json"
            contentType "application/json"
            } 
        params.reqID = UUID.randomUUID().toString()
        params.timestamp = new Date().toString()
        def answer = ["header":params, "body":resp.json]
        
        def comp2 = entities.Component.findByName("Auditor")
        if (comp2 && entities.Component.findByName("Auditor").isActive) {
            // store in the auditdb (CouchDB)
            answer.header.audited="true"
            def restAudit = new RestBuilder()
            def url = entities.Component.findByName("Auditor").baseURL + "/" + params.reqID
            def respAudit = restAudit.put(url){
                contentType "application/json"
                json {answer}
            }
        }
        else { 
            answer.header.audited="false"
        }
        return answer as JSON        
    }
}
