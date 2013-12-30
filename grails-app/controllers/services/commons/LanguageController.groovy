package services.commons

import grails.converters.JSON

class LanguageController {
    static allowedMethods = [
        get: "GET",
        getByIso2:'GET', 
        shortList:'GET', 
        list:'GET']
    
    def RenderService
    
    def index() { redirect(action: "shortList", params: params) }

    def get(Long id) {
        // ../CoreQueries/language/get?id=12
        if (params.id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/language/show/$id" + ".json" //internal request to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }
        } 
        
    def getByIso2(String iso2) {
        // ../CoreQueries/currency/get?iso3=EUR
        if (iso2==null || iso2.size()!=2){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"String iso3"]]
            render answer as JSON
        }
        else {
            params.sourceComponent="Commons"
            params.sourceURI="/language/getByIso2?iso2="+ iso2.toUpperCase()
            params.URL =  RenderService.URL(request) 
            params.URL += "?iso2="+ iso2.toUpperCase()
            render RenderService.serviceMethod(params) 
            }
        } 
        
    def shortList() {
        // ../CoreQueries/language/shortList
        params.URL =  RenderService.URL(request) 
        params.sourceComponent="Commons"
        params.sourceURI="/language/shortList"
        render RenderService.serviceMethod(params)       
        }     
        
    def list() {
        // ../CoreQueries/language/list
        params.URL =  RenderService.URL(request) 
        params.sourceComponent="Commons"
        params.sourceURI="/language/index.json"
        render RenderService.serviceMethod(params)       
        } 
}
