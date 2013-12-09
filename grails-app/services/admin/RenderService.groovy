package admin
import grails.plugins.rest.client.RestBuilder
import grails.converters.*

class RenderService {
    static transactional = false
    
    def serviceMethod(params) {
        def comp1 = entities.Component.findByName(params.sourceComponent)
        def baseURL = comp1.baseURL 
//        println "serviceURL=$params.serviceURL" 
        params.reqID = UUID.randomUUID().toString()
        params.timestamp = new Date().toString() 
        params.links = []
        params.links += ["rel": "self", "href": "$params.URL"]
        params.remove("URL")
        def answer = ""
        def xref = ""
        def resp 
        def rest = new RestBuilder()
//        println "ref: $params.ref"
        try {
            resp = rest.get("$baseURL$params.sourceURI") {
                accept "application/json"
                contentType "application/json"
                } 
            if (resp.json.class) {
                resp.json.remove("class")
            }
            try {
//                println "Let's try now"
//                println "resp.json=> $resp.json" 
                resp.json.each {
                    xref = "$it".split("=")[0]
//                    println "xref -> $xref" 
//                    if (params.ref.indexOf(xref)> -1) { 
//                        println "    This is -> " + xref
//                    }
                    if (params.ref.get(xref)) {
//                        println "This is -> " + xref + " for " + params.ref.get(xref) //.split("=")[0] 
                        if (params.ref.get(xref) == "1") {
//                            println "add Link for $xref" 
//                            println resp.json."$xref".id
                            params.links += ["rel": "$xref", "href" : "$params.serviceURL/$xref/get?id=" + resp.json."$xref".id ]
                        }
                    }
                }
            }
            catch(Exception e1) {}
//                // Build references (HATEOAS)
//                "links": [ 
//                    {"rel": "self", "href":"http://example.org/entity/1"},
//                    {"rel": "friends", "href":"http://example.org/entity/1/friends"}, ... 
//                 ]
                if (params.ref) {
                    params.ref.each {
//                      ref = "/$it/get?id=" + resp.json."$it".id 
//                        params.links += ["rel": "$it", "href": "/$it/get?id=1" ]
                    }                                
                }

        } 
        catch(Exception e) {
            return [error:"$e.message" ] as JSON 
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
}
