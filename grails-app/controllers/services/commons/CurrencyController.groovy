package services.commons

import grails.converters.JSON

class CurrencyController extends BaseController {
    def XRenderService        
    def XBuildLinksService
    
    def getByIso3(String iso3) {
        // ../CoreQueries/currency/get?iso3=EUR
        if (iso3==null || iso3.size()!=3){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$iso3"]]
            render answer as JSON
        }
        else {
            def uri = "/currency/getByIso3?iso3="+ iso3.toUpperCase()  //internal requestt to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.hostApp = XRenderService.hostApp(request)
            params.URL =  XRenderService.URL(request) 
            params.URL += "?iso3="+ iso3.toUpperCase()
            params.links = XBuildLinksService.controllerLinks(params, request)
            params.links += extraLinks("$params.hostApp/$params.controller")
            render XRenderService.serviceMethod(params, request) 
            }
        }     
        
    def shortList() {
        // ../CoreQueries/currency/shortList
        //params.URL =  "$request.scheme://$request.serverName:$request.serverPort/" + RenderService.AppName(request) + "/$controllerName"  
        params.URL =  XRenderService.URL(request) 
        params.sourceComponent="Commons"
        params.hostApp = XRenderService.hostApp(request)
        params.sourceURI="/currency/shortList"
        params.links = XBuildLinksService.controllerLinks(params, request)
        params.links += extraLinks("$params.hostApp/$params.controller")
        render XRenderService.serviceMethod(params, request)       
        }     
        
    def extraLinks(String controllerURL) { 
        def links = [:]
        links += ["shortList": ["href": "$controllerURL/shortList"]]
        links += ["getByIso3":["template":true, "fields": ["iso3":"String"], "href":  "$controllerURL/getByIso3?iso3={iso3}"]]
        return links 
    }     
}