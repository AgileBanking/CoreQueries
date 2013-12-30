package services.commons
import grails.converters.JSON
class TimezoneController {

static allowedMethods = [
    get: "GET",
    getByIso3:'GET', 
    shortList:'GET', 
    list:'GET']
        
    def index() { redirect(action: "shortList", params: params) }
    
    def RenderService
    
    def get(Long id) {
        // ../CoreQueries/timezone/get?id=357
        if (id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/timezone/show/$id" + ".json" //internal request to domains
            params.sourceComponent="Commons"
            params.sourceURI="$uri" 
            params.URL =  RenderService.URL(request) 
            params.URL += "?id=$id"
            render RenderService.serviceMethod(params) 
            }
        } 
        
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
            params.URL =  RenderService.URL(request) 
            params.URL += "?location="+ location
//            params.caller = "$request.forwardURI" 
            render RenderService.serviceMethod(params) 
            }
        }  
        
    def shortList() {
        // ../CoreQueries/timezone/shortList
        //params.URL =  "$request.scheme://$request.serverName:$request.serverPort/" + RenderService.AppName(request) + "/$controllerName"  
        params.URL =  RenderService.URL(request) 
        params.sourceComponent="Commons"
        params.sourceURI="/timezone/shortList"
//        params.caller = "$request.forwardURI" 
        render RenderService.serviceMethod(params)       
        }     
        
    def list() {
        // ../timezone/list
        params.sourceComponent="Commons"
        params.URL =  RenderService.URL(request) 
        params.sourceURI="/timezone/index.json"
//        params.caller = "$request.forwardURI" 
        render RenderService.serviceMethod(params)        
        }      
}