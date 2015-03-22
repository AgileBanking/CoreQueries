package services.commons
import grails.converters.JSON
class TimezoneController extends BaseController {

    def XRenderService
    def XBuildLinksService
    
    static allowedMethods = [getByIso3:'GET']
        
    def shortList() { redirect(action:"list", params: params) }   
    
    def getByLocation(String location) {
        // ../CoreQueries/timezone/getByLocation?location=Europe/Athens
        if (location==null){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$location"]]
            render answer as JSON
        }
        else {
            def uri = "/timezone/getByLocation?location="+ location.toUpperCase()  //internal requestt to domains
            params.sourceComponent=sourceComponent()
            params.sourceURI="$uri" 
            params.host = XRenderService.hostApp(request)
            params.URL =  XRenderService.URL(request) 
            params.URL += "?location="+ location.toUpperCase()
            params.links = XBuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()
            renderNow()
            }
        }  
   
    def extraLinks() { 
        def links = [:]
        def controllerURL = "http://$params.host/$params.controller"
        links += ["getByLocation":["template":true, "fields": ["location":"String (City name)"], \
            "href":  "$controllerURL/getByLocation?location={location}" ]]
        return links 
    } 
    
}