package services.commons

import grails.converters.JSON

class CurrencyController {
//    LinkGenerator grailsLinkGenerator
//    def components = ConfigurationHolder.config.app.components

    static allowedMethods = [
        get: "GET",
        getByIso3:'GET', 
        shortList:'GET', 
        list:'GET']
        
    def index() { redirect(action: "shortList", params: params) }
    
    def RenderService
    
    def get(Long id) {
        // ../CoreQueries/currency/get?id=1
        if (id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/currency/show/$id" + ".json" //internal request to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }
        }  
        
    def getByIso3(String iso3) {
        // ../CoreQueries/currency/get?iso3=EUR
        if (iso3==null || iso3.size()!=3){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$iso3"]]
            render answer as JSON
        }
        else {
            def uri = "/currency/get?iso3="+ iso3.toUpperCase()  //internal requestt to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?iso3="+ iso3.toUpperCase()
            render RenderService.serviceMethod(params) 
            }
        }     
        
    def shortList() {
        // ../CoreQueries/currency/shortList
        //params.URL =  "$request.scheme://$request.serverName:$request.serverPort/" + RenderService.AppName(request) + "/$controllerName"  
        params.URL =  RenderService.URL(request) 
        params.sourceComponent="Commons"
        params.sourceURI="/currency/shortList"
//        params.caller = "$request.forwardURI" 
        render RenderService.serviceMethod(params)       
        }     
        
    def list() {
        // ../currency/list
        params.sourceComponent="Commons"
        params.URL =  RenderService.URL(request) 
        params.sourceURI="/currency/index.json"
//        params.caller = "$request.forwardURI" 
        render RenderService.serviceMethod(params)        
        }      
}