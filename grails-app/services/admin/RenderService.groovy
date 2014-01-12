package admin
import grails.plugins.rest.client.RestBuilder
import grails.converters.*

class RenderService {
    static transactional = false
    
    def serviceMethod(params, request) {
        def baseURL = entities.Component.findByName(params.sourceComponent).baseURL
        params.reqID = UUID.randomUUID().toString()
        params.timestamp = new Date().toString()
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
        def answer = ""
        def xref = ""
        def resp 
        def rest = new RestBuilder()
        def s
        try {        
            resp = rest.get("$baseURL$params.sourceURI") { 
                accept "application/json"
                contentType "application/json"
                } 
            params.status = resp.status.toString() 
            if (resp.status < 300 && resp.json.class != null) { 
                resp.json.remove("class") 
            }
            if (params.hide) {
                params.hide.each {
                    resp.json.remove("$it")
                }
                params.remove("hide")
            }
        } 
        catch(Exception e2) {
            //answer = ["status":"$resp.status" , "message": "$e2.message", "sourceURI":"$baseURL$params.sourceURI"]  
            def xe2 = e2.toString() //.message.split(':')
            answer = ["message": [xe2]] 
            return answer as JSON
        }       

        if (params.withlinks == "false" ) {
            params.notes = "To show 'links' include in the headers or in request 'withlinks=true'."
            answer = ["header":params, "body":resp.json]
        }
        else {
            params.notes = "To hide 'links' include in the headers or in request 'withlinks=false'."
            answer = ["header":params, "links": links, "body":resp.json]
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
}