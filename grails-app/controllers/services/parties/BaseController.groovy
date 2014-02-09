package services.parties

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
    
    private sourceComponent() {"Parties"}
    def casheControl() {"private, no-cache, no-store" } 
    
    def RenderService
    def BuildLinksService
          
    def get(String id) { 
        // URI: /person/get?id=Pdf6d9cc2-3efd-45b8-a502-f70dfc66ea9f
        println "id[1]=$params.id"
        if (params.id==null ) {
            response.status = 400 // 400 Bad Request`
            def answer = ["error":["status":"400", "id":"$params.id"]]
            render answer as JSON
        }
        else {
            println "id[2]=$params.id" 
            params.sourceURI = "/$params.controller/show/$params.id" + ".json" //internal request to domains
            println params.sourceURI
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
            params.URL += "?id=$params.id"
            renderNow()
//            render (text:RenderService.prepareAnswer(params, request), status:params.status, ETag:params.ETag)      
            }
        } 
          
    def list() {
        params.sourceComponent=sourceComponent()
        params.collection = true
        params.host = RenderService.hostApp(request)
        params.withlinks = params.withlinks ? params.withlinks.toLowerCase() : "true" 
        if (params.withlinks == "true") {
            params.links = BuildLinksService.controllerLinks(params, request)  
            params.links += extraLinks()
        }
        params.URL =  RenderService.URL(request) 
        params.hideclass = true
        params.max = Math.min(params.max ? params.int('max') : 10, 100)  
        params.offset = params.offset ? params.int('offset') : 0
        params.sourceURI="/$params.controller/index.json?max=$params.max&offset=$params.offset"
        renderNow()
//        render (text:RenderService.prepareAnswer(params, request), status:params.status, ETag:params.ETag)      
        }      

    def shortList() {
        // ../CoreQueries/currency/shortList
        params.URL =  RenderService.URL(request) 
        params.sourceComponent=sourceComponent()
        params.host = RenderService.hostApp(request)
        params.sourceURI="/$params.controller/shortList"
        params.withlinks = params.withlinks ? params.withlinks.toLowerCase() : "true" 
        if (params.withlinks == "true") {
            params.links = BuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()
        }
        renderNow()
//        render (text:RenderService.prepareAnswer(params, request), status:params.status, ETag:params.ETag)            
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
        if (params.withlinks == "true") {
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
        if (params.withlinks == "true") {
            params.links = BuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()
        }
        params.hide = ["id", "lastUpdated", "dateCreated", "version", "recStatus"]
        params.sourceURI = "$uri"  
        params.hideclass = true
        params.URL =  RenderService.URL(request) 
        renderNow()    
    }
    
    def relatedLinks() {
        params.host = RenderService.hostApp(request)
        params.links = BuildLinksService.controllerLinks(params, request)
        def result = [:]
        result.controller = params.controller 
        params.links += ["self": ["href": "$params.host/$result.controller/relatedLinks"]]
        params.links += extraLinks()
        result.links= params.links
        render result as JSON
    }     

    private renderNow() {
        def answer = RenderService.prepareAnswer(params, request)   
        if ( params.status == 304) { // Not Modified: return the shortest possible answer without decoration
            render status:"$params.status"
            return
        }
        params."Cashe-Control" = casheControl()
        response.setHeader("Cache-Control",params."Cashe-Control")
        response.setHeader("ETag",params.ETag)          
        render (contentType: "application/json", text:answer, status:params.status, ETag:params.ETag,"Cache-Control":params."Cashe-Control")         
    }
    
    // returns an array with extra links to be attached in the response by the caller
    def extraLinks() {
        def links = [:]
    }
}