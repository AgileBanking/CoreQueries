package services

import grails.converters.JSON

class IbanController {

    static allowedMethods = [
        get: "GET",
        getByCountryIso2:'GET', 
        shortList:'GET', 
        list:'GET']
        
    def index() { redirect(action: "shortList", params: params) }
    
    def RenderService
    
    def get(Long id) {
        // ../CoreQueries/Iban/get?id=1
        if (id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/Iban/show/$id" + ".json" //internal request to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }
        }
        
    def getByCountryIso2(String iso2) {
        // ../CoreQueries/Iban/getByIso2?iso3=EUR
        if (iso2==null || iso2.size()!=2){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"String iso2"]]
            render answer as JSON
        }
        else {
            def uri = "/Iban/getByIso2?iso2="+ iso2.toUpperCase()  //internal requestt to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?iso3="+ iso3.toUpperCase()
//            params.caller = "$request.forwardURI" 
            render RenderService.serviceMethod(params) 
            }
        }  
        
    def shortList() {
        // ../CoreQueries/Iban/shortList
        //params.URL =  "$request.scheme://$request.serverName:$request.serverPort/" + RenderService.AppName(request) + "/$controllerName"  
        params.URL =  RenderService.URL(request) 
        params.sourceComponent="Commons"
        params.sourceURI="/Iban/shortList"
//        params.caller = "$request.forwardURI" 
        render RenderService.serviceMethod(params)       
        }     
        
    def list() {
        // ../Iban/list
        params.sourceComponent="Commons"
        params.URL =  RenderService.URL(request) 
        params.sourceURI="/Iban/index.json"
//        params.caller = "$request.forwardURI" 
        render RenderService.serviceMethod(params)        
        }      

}
