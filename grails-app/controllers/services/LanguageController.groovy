package services

import grails.plugins.rest.client.RestBuilder
import grails.converters.*

class LanguageController {
    static allowedMethods = [get:'GET', listShort:'GET', list:'GET']
        
    def index() { redirect(action: "shortList", params: params) }

    def get(String iso2) {
        // ../CoreQueries/currency/get?iso3=EUR
        if (iso2==null || iso2.size()!=3){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"String iso3"]]
            render answer as JSON
        }
        else {
            def uri = "/language/getByIso3?iso3="+ iso3.toUpperCase()  //internal requestt to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?iso3="+ iso3.toUpperCase()
//            params.caller = "$request.forwardURI" 
            render RenderService.serviceMethod(params) 
            }
        } 
}
