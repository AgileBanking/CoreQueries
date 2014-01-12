package services.parties
import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder

class LegalGroupTypeController {
    static allowedMethods = [
        index:              "GET",
        get:                "GET", 
        schema:             "GET",
        create:             "GET",
        listMyLegalGroups:  "GET",
        list:               "GET"]
        
    def index() { redirect(action: "list", params: params) }
    
    def RenderService
          
    def get(Long id) {
        if (params.id==null || !params.id.isLong() ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$params.id"]]
            render answer as JSON
        }
        else {
            def uri = "/$params.controller/show/$id" + ".json" //internal request to domains
            params.sourceComponent="Parties"
            params.collection = false
            params.hostApp = RenderService.hostApp(request) 
            includeLinks(params)
            params.sourceURI = uri
            params.hideclass = true
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }
        } 
          
    def listMyLegalGroups(Long id) {
        if (params.id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/legalGroupType/$params.controller/$id" + ".json" 
            params.sourceComponent="Parties"
            params.collection = true
            params.sourceURI="$uri" 
            params.hideclass = true
            params.hostApp = RenderService.hostApp(request)
            includeLinks(params)
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }       
    }
    
    def list() {
        params.sourceComponent="Parties"
        params.collection = true
        params.hostApp = RenderService.hostApp(request)
        includeLinks(params)
        params.URL =  RenderService.URL(request) 
        params.hideclass = true
        params.sourceURI="/$params.controller/index.json"
        render RenderService.serviceMethod(params)        
        }      
    
    def relatedLinks() {
        params.hostApp = RenderService.hostApp(request)
        includeLinks(params)
        def result = [:]
        result.controller = params.controller 
        params.links += ["self": ["href": "$params.hostApp/$result.controller/relatedLinks"]]
        result.links= params.links
        render result as JSON
    }

    def schema() {
        def uri = "/admin/JSD?ComponentName=Parties&ClassName=" + params.controller.capitalize() //internal request to domains
        params.sourceComponent="Parties"
        params.collection = false
        params.hostApp = RenderService.hostApp(request) 
        params.sourceURI="$uri"  
        params.hideclass = true
        params.URL =  RenderService.URL(request) 
        includeLinks(params)      
        render RenderService.serviceMethod(params)
    }
    
    def create() {
        def uri = "/$params.controller/create.json" //internal request to domains
        params.sourceComponent="Parties"
        params.collection = false
        params.hostApp = RenderService.hostApp(request) 
        params.URL =  RenderService.URL(request) 
        includeLinks(params)
        params.hide = ["id", "version"]
        params.sourceURI = "$uri"  
        params.hideclass = true
        params.URL =  RenderService.URL(request) 
        render RenderService.serviceMethod(params)         
    }
    
    private includeLinks(params) {
        if (params.withlinks==null) { params.withlinks = request.getHeader('withlinks')}
        if (params.withlinks=="true" || params.withlinks==null ) { 
            params.links  = ["list":["template": true, "fields": ["max"], "href":"$params.hostApp/$params.controller/list", "description":"List " ]]
            params.links += ["get":["template": true, "fields": ["id"], "href": "$params.hostApp/$params.controller/get?id={id}"]]
            params.links += ["schema": ["href": "$params.hostApp/$params.controller/schema"]]
            params.links += ["create": ["href": "$params.hostApp/$params.controller/create", "notes":"Returns an empty instance of editable fields."]]
            params.links += ["save":["template":true, "method":"PUT", "href": entities.Component.findByName('CoreUpdates').baseURL + "/$params.controller/save", \
                "body":"@create", "notes":"If you have not in cache, get the body from the 'create'."]]
                        params.links += ["relatedLinks": ["href": "$params.hostApp/$params.controller/relatedLinks", "notes":"Returns a list with the most frequent links."]]
            if (params.id==null) {
                params.links += ["myLegalGroups":["template": true, "fields": "id","href": "$params.hostApp/$params.controller/listMyLegalGroups?id={id}"]]           
            }
            else {
                params.links += ["myLegalGroups":["href": "$params.hostApp/$params.controller/listMyLegalGroups?id=$params.id"]]            
            }
        }        
    }    
}