package services.parties
import grails.converters.JSON
class LegalGroupController {
    static allowedMethods = [
        get: "GET",
        list:"GET"]
        
    def index() { redirect(action: "list", params: params) }
    
    def RenderService
    
    def get(Long id) {
        // ../Parties/legalGroup/get?id=1
        if (id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/legalGroup/show/$id" + ".json" //internal request to domains
            params.sourceComponent="Parties"
            params.sourceURI="$uri" 
            params.collection = false
            params.hideclass = true
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }
        }      
        
    def list() {
        // ../legalGroup/list
        params.sourceComponent="Parties"
        params.hideclass = true
        params.collection = true
        params.URL =  RenderService.URL(request) 
        params.sourceURI="/legalGroup/index.json"
        render RenderService.serviceMethod(params)        
        }
}