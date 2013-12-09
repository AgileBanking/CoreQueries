package services

import grails.converters.JSON

class OrgUnitTypeController {
    static allowedMethods = [
        get: "GET",
        getByCode:'GET', 
        listShort:'GET', 
        list:'GET']
        
    def index() { redirect(action: "shortList", params: params) }
    
    def RenderService
    
    def get(Long id) {
        // ../CoreQueries/orgUnitType/get?id=3
        if (params.id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/orgUnitType/show/$id" + ".json" //internal request to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }
        } 
        
    
    def getByCode(String code) {
        // ../CoreQueries/orgUnitType/get?code=BoD
        if (code==null){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"String code"]]
            render answer as JSON
        }
        else {
            def uri = "/orgUnitType/getByCode?code="+ code.toUpperCase()  //internal requestt to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?code="+ code.toUpperCase()
//            params.caller = "$request.forwardURI" 
            render RenderService.serviceMethod(params) 
            }
        }  
        
    def shortList() {
        // ../CoreQueries/orgUnitType/shortList
        //params.URL =  "$request.scheme://$request.serverName:$request.serverPort/" + RenderService.AppName(request) + "/$controllerName"  
        params.URL =  RenderService.URL(request) 
        params.sourceComponent="Commons"
        params.sourceURI="/orgUnitType/shortList"
//        params.caller = "$request.forwardURI" 
        render RenderService.serviceMethod(params)       
        }     
        
    def list() {
        // ../orgUnitType/list
        params.sourceComponent="Commons"
        params.URL =  RenderService.URL(request) 
        params.sourceURI="/orgUnitType/index.json"
//        params.caller = "$request.forwardURI" 
        render RenderService.serviceMethod(params)        
        }      

}
