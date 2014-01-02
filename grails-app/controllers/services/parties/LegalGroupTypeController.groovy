package services.parties
import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder

class LegalGroupTypeController {
    static allowedMethods = [
        index: "GET",
        get: "GET", 
        schema: "GET",
        create: "GET",
        listMyLegalGroups: "GET",
        list:'GET']
        
    def index() { redirect(action: "list", params: params) }
    
    def RenderService

                
    def schema() {
        def uri = "/admin/JSD?ComponentName=Parties&ClassName=LegalGroupType" //internal request to domains
        params.sourceComponent="Parties"
        params.collection = false
        params.hostApp = RenderService.hostApp(request) 
        params.sourceURI="$uri"  
        params.hideclass = true
        params.URL =  RenderService.URL(request) 
        if (params.withlinks==null) { params.withlinks = request.getHeader('withlinks')}
        if (params.withlinks=="true" || params.withlinks==null ) { 
            params.links  = ["list":["template": true, "fields": ["max"], "href":"$params.hostApp/legalGroupType/list", "description":"List " ]]
            params.links += ["get":["template": true, "fields": ["id"], "href": "$params.hostApp/legalGroupType/get?id={id}"]]
            params.links += ["save":["template":true, "method":"PUT", "href": entities.Component.findByName('CoreUpdates').baseURL + "/legalGroupType/save", \
                "body":"@create", "notes":"If you have not in cache, get the body from the 'create'."]]
            params.links += ["create": ["href": "$params.hostApp/legalGroupType/create", "notes":"Returns an empty instance of editable fields."]]
            params.links += ["myLegalGroups":["template": true, "fields": ["id"],"href": "$params.hostApp/legalGroupType/listMyLegalGroups?id={id}"]]
        }        
        render RenderService.serviceMethod(params)
    }
    
    def create() {
        def uri = "/legalGroupType/create.json" //internal request to domains
        params.sourceComponent="Parties"
        params.collection = false
        params.hostApp = RenderService.hostApp(request) 
        params.URL =  RenderService.URL(request) 
        if (params.withlinks==null) { params.withlinks = request.getHeader('withlinks')}    
        if (params.withlinks=="true" || params.withlinks==null ) {         
            params.links  = ["get":["template": true, "fields": "id", "href": "$params.hostApp/legalGroupType/get?id={id}"]]
            params.links += ["list": ["href": "$params.hostApp/legalGroupType/list"]]
            params.links += ["schema": ["href": "$params.hostApp/legalGroupType/schema"]]
            params.links += ["save":["template":true, "method":"PUT", "href": entities.Component.findByName('CoreUpdates').baseURL + "/legalGroupType/save", \
                "body":"@create", "notes":"If you have not in cache, get the body from the 'create'."]]
            params.links += ["create": ["href": "$params.hostApp/legalGroupType/create"]]        
            params.links += ["myLegalGroups":["template": true, "fields": "id","href": "$params.hostApp/legalGroupType/listMyLegalGroups?id={id}"]]
        }
        params.hide = ["id", "version"]
        params.sourceURI = "$uri"  
        params.hideclass = true
        params.URL =  RenderService.URL(request) 
        render RenderService.serviceMethod(params)         
    }
    
    def get(Long id) {
        // ../CoreQueries/legalGroupType/get?id=10
         
        if (params.id==null || !params.id.isLong() ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$params.id"]]
            render answer as JSON
        }
        else {
            println "id=$id"
            def uri = "/legalGroupType/show/$id" + ".json" //internal request to domains
            params.sourceComponent="Parties"
            params.collection = false
            params.hostApp = RenderService.hostApp(request) 
            if (params.withlinks==null) { params.withlinks = request.getHeader('withlinks')}    
            if (params.withlinks=="true" || params.withlinks==null ) { 
                params.links  = ["list": ["href": "$params.hostApp/legalGroupType/list"]]
                params.links += ["get":["template": true, "fields": "id", "href": "$params.hostApp/legalGroupType/get?id={id}"]]
                params.links += ["save":["template":true, "method":"PUT", "href": entities.Component.findByName('CoreUpdates').baseURL + "/legalGroupType/save", \
                    "body":"@create", "notes":"If you have not in cache, get the body from the 'create'."]]
                params.links += ["create": ["href": "$params.hostApp/legalGroupType/create"]]            
                params.links += ["myLegalGroups":["href": "$params.hostApp/legalGroupType/listMyLegalGroups?id=$id"]]
            }
            params.sourceURI = uri
            params.hideclass = true
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }
        } 
          
    def listMyLegalGroups(Long id) {
        // ../CoreQueries/legalGroupType/listMyLegalGroups?id=10
        if (params.id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/legalGroupType/listMyLegalGroups/$id" + ".json" //internal request to domains
            params.sourceComponent="Parties"
            params.collection = true
            params.sourceURI="$uri" 
            params.hideclass = true
            params.hostApp = RenderService.hostApp(request)
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }       
    }
    
    def list() {
        // ../legalGroupType/list
        params.sourceComponent="Parties"
        params.collection = true
        params.hostApp = RenderService.hostApp(request)
        if (params.withlinks==null) { params.withlinks = request.getHeader('withlinks')}    
        if (params.withlinks=="true" || params.withlinks==null ) {         
            params.links  = ["schema": ["href":params.hostApp + \
                                "/admin/JSD?ComponentName=$params.sourceComponent&ClassName=LegalGroupType"]]  
            params.links += ["save":["template":true, "method":"PUT", "href": entities.Component.findByName('CoreUpdates').baseURL + "/legalGroupType/save", \
                "body":"@create", "notes":"If you have not in cache, get the body from the 'create'."]]                                        
            params.links += ["create":["href": "$params.hostApp/legalGroupType/create"]]   
            params.links += ["get":["template": true, "fields": "id", "href": "$params.hostApp/legalGroupType/get?id={id}"]]
            params.links += ["getLegalGroup":["template": true, "fields": "id", "href": "$params.hostApp/legalGroup/get?id={id}"]]        
        }
        params.URL =  RenderService.URL(request) 
        params.hideclass = true
        params.sourceURI="/legalGroupType/index.json"
        render RenderService.serviceMethod(params)        
        }      
}