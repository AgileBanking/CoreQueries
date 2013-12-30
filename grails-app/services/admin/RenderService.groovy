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
        
        if (params.links == null) { params.links = []}
        params.links += ["rel": "self", "href": "$params.URL"]
        params.remove("URL")
        def answer = ""
        def xref = ""
        def resp 
        def rest = new RestBuilder()
        try {
            resp = rest.get("$baseURL$params.sourceURI") { 
                accept "application/json"
                contentType "application/json"
                } 
            if (resp.json.class) {
                resp.json.remove("class")
            }
            try {                
                resp.json.each {
//                    println "$it" 
                    if (params.hideclass==null || params.hideclass){
                        it.remove("class") 
                    }
//                    xref = "$it".split("=")[0]
//                    println "$xref" 
//                    if (params.ref.get(xref)) {
//                        if (params.ref.get(xref) == "1") {
//                            params.links += ["rel": "$xref", "href" : "$params.serviceURL/$xref/get?id=" + resp.json."$xref".id ]
//                        }
//                    } 
                }              
            }
            catch(Exception e1) {}
//                // Build references (HATEOAS)
//                if (params.ref) {
//                    params.ref.each {
//                      ref = "/$it/get?id=" + resp.json."$it".id 
//                        params.links += ["rel": "$it", "href": "/$it/get?id=1" ]
//                    }                                
//                }

        } 
        catch(Exception e2) {
            return [error:"$e2.message" ] as JSON 
        }
        answer = ["header":params, "body":resp.json]
        // Keep Audit in CouchDB
        try {
            if (entities.Component.findByName("Auditor").isActive) {
                // store in the auditdb (CouchDB)
                def restAudit = new RestBuilder()
                def url = entities.Component.findByName("Auditor").baseURL + "/$params.reqID"
                params.links += ["rel": "audit", "href" : "$url" ]
//                answer.header.auditRec = "$url"
                def respAudit = restAudit.put("$url"){
                    contentType "application/json"
                    json {answer}
                }
            }            
        }
        catch(Exception e) {
//            answer.header.auditDB="unavailable"
            header.header.error="$e.message" 
        }
        
        return answer as JSON      
    }
    
    def URL(request) {
        def x = request.getRequestURL() 
        return x.substring(0,x.indexOf('.dispatch')) - '/grails'	        
    }   
    
    def host(request) {
        def appName = "CoreQueries" 
        def x = request.getRequestURL() 
        return x.substring(0,x.indexOf("$appName") + appName.size())  
    }      
}