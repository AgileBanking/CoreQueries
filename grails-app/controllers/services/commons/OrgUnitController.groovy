package services.commons
import grails.converters.JSON
class OrgUnitController {

    static allowedMethods = [
        get: "GET",
        getByCode:'GET', 
        shortList:'GET', 
        list:'GET']
        
    def index() { redirect(action: "shortList", params: params) }
    
    def RenderService

    def get(Long id) {
        // ../CoreQueries/orgUnit/get?id=10
        if (params.id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/orgUnit/show/$id" + ".json" //internal request to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }
        } 
        
    def shortList() {
        // ../CoreQueries/orgUnit/shortList
        params.URL =  RenderService.URL(request) 
        params.sourceComponent="Commons"
        params.sourceURI="/orgUnit/shortList"
        render RenderService.serviceMethod(params)       
        }     
        
    def list() {
        // ../orgUnit/list
        params.sourceComponent="Commons"
        params.URL =  RenderService.URL(request) 
        params.sourceURI="/orgUnit/index.json"
        render RenderService.serviceMethod(params)        
        }      
}
