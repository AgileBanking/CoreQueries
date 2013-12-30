package services.parties
import grails.converters.JSON
class LegalGroupTypeController {
    static allowedMethods = [
        get: "GET", 
        list:'GET']
        
    def index() { redirect(action: "list", params: params) }
    
    def RenderService

    def get(Long id) {
        // ../CoreQueries/legalGroupType/get?id=10
        if (params.id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/legalGroupType/show/$id" + ".json" //internal request to domains
            params.sourceComponent="Parties"
            params.collection = false
            params.hostApp = RenderService.host(request) 
            params.links = [
                ["rel":"myLegalGroups", "href": "$params.hostApp/legalGroupType/listMyLegalGroups?id=$id"]
            ]
            params.sourceURI="$uri"  
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
            params.hostApp = RenderService.host(request)
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }       
    }
    
    def list() {
        // ../legalGroupType/list
        params.sourceComponent="Parties"
        params.collection = true
        params.hostApp = RenderService.host(request)
        params.URL =  RenderService.URL(request) 
        params.hideclass = true
        params.sourceURI="/legalGroupType/index.json"
        render RenderService.serviceMethod(params)        
        }      
}

