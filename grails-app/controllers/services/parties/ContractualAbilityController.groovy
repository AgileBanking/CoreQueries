package services.parties
import grails.converters.JSON
class ContractualAbilityController {
    static allowedMethods = [
        get: "GET",
        list:'GET']
        
    def index() { redirect(action: "list", params: params) }
    
    def RenderService
    
    def get(Long id) {
        // ../Parties/contractualAbility/get?id=1
        if (id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/contractualAbility/show/$id" + ".json" //internal request to domains
            params.sourceComponent="Parties"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }
        }      
        
    def list() {
        // ../addressType/list
        params.sourceComponent="Parties"
        params.URL =  RenderService.URL(request) 
        params.sourceURI="/contractualAbility/index.json"
        render RenderService.serviceMethod(params)        
        }
}
