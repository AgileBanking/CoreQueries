package services.commons

import grails.converters.JSON
abstract class BaseController {
    static allowedMethods = [
        index:              "GET",
        get:                "GET", 
        schema:             "GET",
        create:             "GET",
        listMyLegalGroups:  "GET",
        relatedLinks:       "GET",
        shortList:          "GET",
        list:               "GET"]
        
    def index() { redirect(action: "shortList", params: params) }
    
    private sourceComponent() {"Commons"}
    
    def RenderService
    def BuildLinksService
          
    def get(Long id) { 
        if (params.id==null || !params.id.isLong() ) {
            response.status = 400 // 400 Bad Request`
            def answer = ["error":["status":"400", "id":"$params.id"]]
            render answer as JSON
        }
        else {
            params.sourceURI = "/$params.controller/show/$id" + ".json" //internal request to domains
            params.sourceComponent=sourceComponent()
            params.collection = false
            params.host = RenderService.hostApp(request) 
            params.links = BuildLinksService.controllerLinks(params, request) 
            params.links += extraLinks()
            params.hideclass = true
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render (text:RenderService.prepareAnswer(params, request), status:params.status, ETag:params.ETag)      
            }
        } 
          
    def list() {
        params.sourceComponent=sourceComponent()
        params.collection = true
        params.host = RenderService.hostApp(request)
        params.links = BuildLinksService.controllerLinks(params, request)  
        params.links += extraLinks()
        params.URL =  RenderService.URL(request) 
        params.hideclass = true
        params.max = Math.min(params.max ? params.int('max') : 10, 100)  
        params.offset = params.offset ? params.int('offset') : 0
        params.sourceURI="/$params.controller/index.json?max=$params.max&offset=$params.offset"
        render (text:RenderService.prepareAnswer(params, request), status:params.status, ETag:params.ETag)      
        }      

    def shortList() {
        // ../CoreQueries/currency/shortList
        params.URL =  RenderService.URL(request) 
        params.sourceComponent=sourceComponent()
        params.host = RenderService.hostApp(request)
        params.sourceURI="/$params.controller/shortList"
        params.links = BuildLinksService.controllerLinks(params, request)
        params.links += extraLinks()
        render (text:RenderService.prepareAnswer(params, request), status:params.status, ETag:params.ETag)            
        }    

    def schema() {
        def uri = "/admin/JSD?ComponentName=Parties&ClassName=" + params.controller.capitalize() //internal request to domains
        params.sourceComponent=sourceComponent()
        params.collection = false
        params.host = RenderService.hostApp(request) 
        params.sourceURI="$uri"  
        params.hideclass = true
        params.URL =  RenderService.URL(request) 
        params.links = BuildLinksService.controllerLinks(params, request) 
        params.links += extraLinks()
        render (text:RenderService.prepareAnswer(params, request), status:params.status, ETag:params.ETag)     
    }
    
    def create() {
        def uri = "/$params.controller/create.json" //internal request to domains
        params.sourceComponent=sourceComponent()
        params.collection = false
        params.host = RenderService.hostApp(request) 
        params.URL =  RenderService.URL(request) 
        params.links = BuildLinksService.controllerLinks(params, request)
        params.links += extraLinks()
        params.hide = ["id", "version"]
        params.sourceURI = "$uri"  
        params.hideclass = true
        params.URL =  RenderService.URL(request) 
        render (text:RenderService.prepareAnswer(params, request), status:params.status, ETag:params.ETag)     
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
     
    // returns an array with extra links to be attached in the response by the caller
    def extraLinks() {
        def links = [:]
    }
}
