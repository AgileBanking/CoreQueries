package services.commons
import grails.converters.JSON
class TimezoneController extends BaseController {
    def XRenderService        
    def XBuildLinksService

static allowedMethods = [
    get: "GET",
    getByIso3:'GET', 
    shortList:'GET', 
    list:'GET']
        
    def shortList() { redirect(action:"list", params: params) }   
    
    def getByLocation(String location) {
        // ../CoreQueries/timezone/getByLocation?location=Europe/Athens
        println "location: $location"
        if (location==null){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$location"]]
            render answer as JSON
        }
        else {
            def uri = "/timezone/getByLocation?location="+ location.toUpperCase()  //internal requestt to domains
            println uri
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  XRenderService.URL(request) 
            params.URL += "?location="+ location
//            params.caller = "$request.forwardURI" 
            render XRenderService.serviceMethod(params, request) 
            }
        }  
   
    def extraLinks() { 
        def links = [:]
        def controllerURL = "$params.host/$params.controller"
        links += ["getByLocation":["template":true, "fields": ["location":"String (City name)"], \
            "href":  "$controllerURL/getByLocation?location={location}" ]]
        return links 
    } 
    
}