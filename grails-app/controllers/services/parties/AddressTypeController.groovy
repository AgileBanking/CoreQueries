package services.parties

import grails.converters.JSON
class AddressTypeController {
    static allowedMethods = [
        get: "GET",
        list:'GET']
        
    def index() { redirect(action: "list", params: params) }
    
    def RenderService
    
    def get(Long id) {
        // ../Parties/addressType/get?id=1
        if (id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/addressType/show/$id" + ".json" //internal request to domains
            params.sourceComponent="Parties"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }
        }  
        
        
//    def shortList() {
//        // ../Parties/addressType/shortList
//        params.URL =  RenderService.URL(request) 
//        params.sourceComponent="Parties"
//        params.sourceURI="/addressType/shortList"
//        render RenderService.serviceMethod(params)       
//        }     
        
    def list() {
        // ../addressType/list
        params.sourceComponent="Parties"
        params.URL =  RenderService.URL(request) 
        params.sourceURI="/addressType/index.json"
        params.hideclass=true
        render RenderService.serviceMethod(params)        
        }      
}

