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
//                answer.header.audited="true"
                def restAudit = new RestBuilder()
                def url = entities.Component.findByName("Auditor").baseURL + "/$params.reqID"
                answer.header.auditRec = "$url"
                def respAudit = restAudit.put("$url"){
                    contentType "application/json"
                    json {answer}
                }
            } 
//            else {
//                answer.header.auditDB="unavailable"
//                answer.header.audited="false"
//            }            
        }
        catch(e) {
//            answer.header.auditDB="unavailable"
            answer.header.error=e.toString()
        }
        return answer as JSON        
    }
    
    def URL(request) {
        def x = request.getRequestURL()
        return x.substring(0,x.indexOf('.dispatch')) - '/grails'
//        
//        def helper = new org.springframework.web.util.UrlPathHelper()
//        return helper.getContextPath(request).substring(1)
        
//        def x = request.context.toString()
//        return x.substring(x.indexOf('Context[/')+'Context[/'.size(),x.size() -1)	        
    }
}
