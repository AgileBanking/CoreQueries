package services.commons

import grails.converters.JSON

class LanguageController extends BaseController {

    def XRenderService
    def XBuildLinksService
    
    static allowedMethods = [getByIso2: "GET"]
             
    def getByIso2(String iso2) {
        // ../CoreQueries/language/get?iso2=EUR
        if (iso2==null || iso2.size()!=2){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$iso2"]]
            render answer as JSON
        }
        else {
            def uri = "/language/getByIso2?iso2="+ iso2.toUpperCase()  //internal requestt to domains
            params.sourceComponent=sourceComponent()
            params.sourceURI="$uri" 
            params.host = XRenderService.hostApp(request)
            params.URL =  XRenderService.URL(request) 
            params.URL += "?iso2="+ iso2.toUpperCase()
            params.links = XBuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()
            renderNow()
            }
        }      
        
    def extraLinks() { 
        def controllerURL = "$params.host/$params.controller"
        def links = [:]
        links += ["getByIso2":["template":true, "fields": ["iso2":"String (Language: ISO 639-2 code)"], \
            "href":  "$controllerURL/getByIso2?iso2={iso2}" ]]
        links += ["references":["ISO 639-2 code": "http://www.loc.gov/standards/iso639-2/php/code_list.php"]]
        return links 
    }   
}
