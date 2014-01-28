package services.commons

import grails.converters.JSON

class CurrencyController extends BaseController {
//    def RenderService        
//    def BuildLinksService
    
    def getByIso3(String iso3) {
        // ../CoreQueries/currency/get?iso3=EUR
        if (iso3==null || iso3.size()!=3){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$iso3"]]
            render answer as JSON
        }
        else {
            def uri = "/currency/getByIso3?iso3="+ iso3.toUpperCase()  //internal requestt to domains
            params.sourceComponent=sourceComponent()
            params.sourceURI="$uri" 
            params.host = RenderService.hostApp(request)
            params.URL =  RenderService.URL(request) 
            params.URL += "?iso3="+ iso3.toUpperCase()
            params.links = BuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()
            renderNow()  //render RenderService.serviceMethod(params, request) 
            }
        }       
        
    def extraLinks() { 
        def controllerURL = "$params.host/$params.controller"
        def links = [:]
        links += ["getByIso3":["template":true, "fields": ["iso3":"String"], "href":  "$controllerURL/getByIso3?iso3={iso3}"]]
        return links 
    }     
}