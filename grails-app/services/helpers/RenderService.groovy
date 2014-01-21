package helpers
import grails.plugins.rest.client.RestBuilder
import grails.converters.*
import java.security.MessageDigest 

class RenderService {
    static transactional = false
    
    def prepareAnswer(params, request) {
        def baseURL = entities.Component.findByName(params.sourceComponent).baseURL
        params.reqID = UUID.randomUUID().toString()
        params.Date = new Date().toString()
        // prepare hyperlinks    
        def href = "$params.URL"
        def links = ["self": ["href": "$params.URL"]]
        if (href.contains("?")) {
            links.self += ["alt_href": "$href" + "&withlinks=false"]
        }
        else {
            links.self += ["alt_href": "$href" + "?withlinks=false"]
        }
        if (params.links != null) { links += params.links}
        // clean params
        params.remove("links")
        params.remove("URL")
        // define variables
        def answer = [:]
        def xref = ""
        def resp, s
        def rest = new RestBuilder()
        try {        
            resp = rest.get("$baseURL$params.sourceURI") { 
                accept "application/json"
                contentType "application/json"
                } 
//            def xj = resp.json[0] as JSON
            params.status = resp.status 
//            if (params.status == 404) {
//                return 
//            }
            if (resp.status < 300 && resp.json.class != null) { 
                resp.json.remove("class") 
            }
            if (params.hide) {
                params.hide.each {
                    resp.json.remove("$it") 
                }
                params.remove("hide")
            }
            // If not modified return nothing  
            def md5= MD5(resp.json.toString())
            def rETag = request.getHeader("If-None-Match")
//            println "rETag: $rETag, pTag: $md5" 
            params.ETag = md5 
            if ("$rETag" == "$md5") { 
                params.status = 304
                return 
            } 
            
        } 
        catch(Exception e2) {
            def xe2 = e2.toString() //.message.split(':')
            params.status = 503
            answer = ["status":params.status, "possibleCause": "Unavailable Domain Server $params.sourceComponent", "message":[xe2]] 
            return answer as JSON
        }       

        if (params.withlinks == "false" ) {
            params.notes = "To show 'links' include in the headers or in request 'withlinks=true'."
            answer = ["header":params, "body":resp.json]
        }
        else {
            params.notes = "To hide 'links' include in the headers or in request 'withlinks=false'."
            answer = ["header":params, "body":resp.json, "links": links ]
//            answer = ["header":params, "request": request, "links": links, "body":resp.json]
        }
        
        // Keep Audit in CouchDB
        try {
            if (entities.Component.findByName("Auditor").isActive) {
                // store in the auditdb (CouchDB)
                def restAudit = new RestBuilder()
                def url = entities.Component.findByName("Auditor").baseURL + "/$params.reqID"
                answer.links += ["audit":["href" : "$url"]]
//                answer.header.auditRec = "$url"
                def respAudit = restAudit.put("$url"){
                    contentType "application/json"
                    json {answer}
                }
            }            
        }
        catch(Exception e3) {
            answer.header.error="$e3.message" 
        } 
        return answer as JSON      
    }
    
    def URL(request) {
        def x = request.getRequestURL() 
        return x.substring(0,x.indexOf('.dispatch')) - '/grails'	        
    }   
    
    def hostApp(request) {
        def appName = entities.Component.findByName("CoreQueries").baseURL 
        def x = request.getRequestURL()  
        return x.substring(0,x.indexOf("$appName") + appName.size())  
    }      
    
    def MD5(String s) {
        MessageDigest digest = MessageDigest.getInstance("MD5")
        digest.update(s.bytes);
        new BigInteger(1, digest.digest()).toString(16).padLeft(32, '0')        
    }
}