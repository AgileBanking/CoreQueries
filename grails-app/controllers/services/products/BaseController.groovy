package services.products

import grails.plugins.rest.client.RestBuilder
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.*
import grails.converters.JSON

abstract class BaseController {

    static allowedMethods = [
        index:              "GET",
        get:                "GET", 
        schema:             "GET",
        create:             "GET",
        relatedLinks:       "GET",
        shortList:          "GET",
        list:               "GET"]
        
    def index() { redirect(action: "shortList", params: params) }
    
    private sourceComponent() {"Products"}
//    def casheControl() {"public, max-age=5" } // 72000 for 20 hours with "If-None-Match"
//    def casheControl() {"private, no-cache, no-store" } with "If-None-Match"
    def casheControl() {"public, max-age=300" } // re-read it after 5' with "If-None-Match" 
    
    def RenderService
    def BuildLinksService
    def AccessControlService
    def SysConfigService
          
    def get(String id) { 
        if (params.id==null || !params.id.isLong() ) {
            response.status = 400 // 400 Bad Request`
            def answer = ["error":["status":"400", "id":"$params.id"]]
            render answer as JSON
        }
        else {
            params.sourceComponent=sourceComponent()
            params.collection = false
            params.host = RenderService.hostApp(request) 
            params.withlinks = params.withlinks ? params.withlinks.toLowerCase() : "true" 
            if (params.withlinks == "true") {
                params.links = BuildLinksService.controllerLinks(params, request) 
                params.links += extraLinks()
            }
            params.hideclass = true
            params.URL =  RenderService.URL(request) 
            params.recStatus = (params.recStatus ? params.recStatus.toLowerCase() : "Active").capitalize()  
            params.sourceURI = "/$params.controller/show.json?id=$id&recStatus=$params.recStatus"   //internal request to domains
            params.URL += "?id=$id&recStatus=$params.recStatus" 
            renderNow()
//            render (text:RenderService.prepareAnswer(params, request), status:params.status, ETag:params.ETag)      
            }
        } 
          
    def list() {
        params.sourceComponent=sourceComponent()
        params.collection = true
        params.host = RenderService.hostApp(request)
        params.withlinks = params.withlinks ? params.withlinks.toLowerCase() : "true" 
        if (params.withlinks == "true"  ) {
            params.links = BuildLinksService.controllerLinks(params, request)  
            params.links += extraLinks()            
        }
        params.URL =  RenderService.URL(request) 
        params.hideclass = true
        params.max = Math.min(params.max ? params.int('max') : 10, 100)  
        params.offset = params.offset ? params.int('offset') : 0
        params.recStatus = (params.recStatus ? params.recStatus.toLowerCase() : "Active").capitalize() 
        params.sourceURI="/$params.controller/index.json?max=$params.max&offset=$params.offset&recStatus=$params.recStatus"
        renderNow()
//        render (text:RenderService.prepareAnswer(params, request), status:params.status, ETag:params.ETag)      
        }      

    def shortList() {
        // ../CoreQueries/currency/shortList
        params.withlinks = params.withlinks ? params.withlinks.toLowerCase() : "true" 
        params.URL =  RenderService.URL(request)  
        params.sourceComponent=sourceComponent()
        params.host = RenderService.hostApp(request)
        params.recStatus = (params.recStatus ? params.recStatus.toLowerCase() : "Active").capitalize() 
        params.sourceURI="/$params.controller/shortList?recStatus=$params.recStatus" 
        if (params.withlinks == "true"  ) {
            params.links = BuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()            
        }
        renderNow()        
        }    

    def schema() {
        def uri = "/admin/JSD?ComponentName=Parties&ClassName=" + params.controller.capitalize() //internal request to domains
        params.sourceComponent=sourceComponent()
        params.collection = false
        params.host = RenderService.hostApp(request) 
        params.sourceURI="$uri"  
        params.hideclass = true
        params.URL =  RenderService.URL(request) 
        params.withlinks = params.withlinks ? params.withlinks.toLowerCase() : "true" 
        if (params.withlinks == "true"  ) {
            params.links = BuildLinksService.controllerLinks(params, request) 
            params.links += extraLinks()
        }
        renderNow()    
    }
    
    def create() {
        def uri = "/$params.controller/create.json" //internal request to domains
        params.sourceComponent=sourceComponent()
        params.collection = false
        params.host = RenderService.hostApp(request) 
        params.URL =  RenderService.URL(request) 
        params.withlinks = params.withlinks ? params.withlinks.toLowerCase() : "true" 
        if (params.withlinks == "true"  ) {
            params.links = BuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()
        }
        params.hide = ["id", "version", "dateCreated", "lastUpdated", "recStatus"]
        params.sourceURI = "$uri"  
        params.hideclass = true
        params.URL =  RenderService.URL(request) 
        println "1. hide: $params.hide" 
        renderNow()    
    }
    
    def relatedLinks() {
        params.host = RenderService.hostApp(request)
        def result = [:]
        result.controller = params.controller 
        params.links = BuildLinksService.controllerLinks(params, request)
        params.links += ["self": ["href": "$params.host/$result.controller/relatedLinks"]]
        params.links += extraLinks()
        result.links= params.links            
        render result as JSON
    }     
    
    // returns an array with extra links to be attached in the response by the caller
    def extraLinks() {
        return [:]
    }
    
    private renderNow() {
        params."Cashe-Control" = casheControl()
        response.setHeader("Cache-Control",params."Cashe-Control")
        response.setHeader("ETag",params.ETag)          
        def answer = RenderService.prepareAnswer(params, request)   
        if ( params.status == 304) { // Not Modified: return the shortest possible answer without decoration
            render status:"$params.status"
            return
        }        
        
        // Keep Audit
        try {
            def auditor = SysConfigService.getComponent("Auditor")
            if (auditor.component.isActive) { 
                // store in the auditdb (CouchDB)
                def restAudit = new RestBuilder()
                def url = auditor.component.baseURL + "/$params.reqID"
                answer.header.auditRec = "$url"
                def respAudit = restAudit.put("$url"){
                    contentType "application/json"
                    json {["header":answer.header, "body":answer.body]} 
                }
                answer.links += ["audit":["href" : "$url"]]
            }            
        }
        catch(Exception e3) {
            answer.header.error="$e3.message" 
        }
        // sort entries keeping the top entries as ordered
        answer.header = answer.header.sort {it.key}
        answer.body = answer.body.sort {it.key}
        if (answer.links) {answer.links = answer.links.sort {it.key}}
        
        render (contentType: "application/json", text:answer as JSON, status:params.status, ETag:params.ETag,"Cache-Control":params."Cashe-Control")         
    }
}
