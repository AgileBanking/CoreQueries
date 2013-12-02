package services

import grails.plugins.rest.client.RestBuilder
import grails.converters.*

class CurrencyController {
//    LinkGenerator grailsLinkGenerator
//    def components = ConfigurationHolder.config.app.components

    static allowedMethods = [get:'GET', listShort:'GET', list:'GET']
        
    def index() { redirect(action: "shortList", params: params) }
    
    def RenderService
    
    def get(String iso3) {
        // ../CoreQueries/currency/get?iso3=EUR
        if (iso3==null || iso3.size()!=3){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"String iso3"]]
            render answer as JSON
        }
        else {
            def uri = "/currency/getByIso3?iso3="+ iso3.toUpperCase()  //internal requestt to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?iso3="+ iso3.toUpperCase()
//            params.caller = "$request.forwardURI" 
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
    


