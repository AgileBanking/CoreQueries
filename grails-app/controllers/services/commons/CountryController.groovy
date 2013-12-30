package services.commons

import grails.converters.JSON

class CountryController {
    static allowedMethods = [
        get: "GET",
        list: "GET",
        getByIso2: "GET",
        shortList: "GET"
    ]
    
    def RenderService
    
    def index() {redirect(action: "shortList", params: params) }
    
    def get(Long id) {
        // ../CoreQueries/country/get?id=1
        if (id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/country/show/$id" + ".json" //internal requestt to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            def x = request.getRequestURL() 
            params.serviceURL = x.substring(0,x.indexOf('/grails')) 
            params.ref = [currency:"1", languages:"*", timeZones:"*", iban:"1"]
            render RenderService.serviceMethod(params) 
        }
    }         
    
    def getByIso2(String iso2) {
        // ../getByIso2/iso2
        if (iso2==null || !iso2.size()==2) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$iso2"]]
            render answer as JSON 
        }
        else {            
            params.sourceComponent="Commons"
            params.sourceURI="/country/getByIso2/" + iso2.toUpperCase()
            params.URL =  RenderService.URL(request) 
            params.URL += "?iso2="+ iso2.toUpperCase()
            render RenderService.serviceMethod(params) 
        }
    }

    def list() {
        // ../country/getFullList
        params.sourceComponent="Commons"
        params.sourceURI="/country/index.json"
        params.URL =  RenderService.URL(request) 
        render RenderService.serviceMethod(params)    
    }   
    
    def shortList() {
        // ../CoreQueries/country/shortList
        //params.URL =  "$request.scheme://$request.serverName:$request.serverPort/" + RenderService.AppName(request) + "/$controllerName"  
        params.URL =  RenderService.URL(request) 
        params.sourceComponent="Commons"
        params.sourceURI="/country/shortList"
        render RenderService.serviceMethod(params)       
        }     
        
        
}
