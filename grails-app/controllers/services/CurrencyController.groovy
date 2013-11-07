package services

import grails.plugins.rest.client.RestBuilder
import grails.converters.*
//import org.codehaus.groovy.grails.commons.ConfigurationHolder

class CurrencyController {
//    LinkGenerator grailsLinkGenerator
//    def components = ConfigurationHolder.config.app.components

    static allowedMethods = [get:'GET', listShort:'GET', list:'GET']
        
    def index() { redirect(action: "shortList", params: params) }
    
    def RenderService
    
    def get(String iso3) {
        // ../CoreQueries/currency/getByIso3?iso3=EUR
        if (iso3==null || iso3.size()!=3){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"String iso3"]]
            render answer as JSON
        }
        else {
            def uri = "/currency/getByIso3?iso3="+ iso3.toUpperCase()
//            println "redirect to $uri"
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.caller = "$request.forwardURI" 
//            render RenderService.serviceMethod(params) 
            render RenderService.serviceMethod(params) 
            }
        }  
        
    def shortList() {
        // ../CoreQueries/currency/getListOfIso3
//        println "redirect to /currency/getListOfIso3"
        params.sourceComponent="Commons"
        params.sourceURI="/currency/shortList"
        params.caller = "$request.forwardURI" 
        render RenderService.serviceMethod(params)       
        }     
        
    def list() {
        // ../currency/getFullList
        params.sourceComponent="Commons"
        params.sourceURI="/currency/index.json"
        params.caller = "$request.forwardURI" 
        render RenderService.serviceMethod(params)        
        }      
}
    


