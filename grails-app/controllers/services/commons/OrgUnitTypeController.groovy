package services.commons
import grails.converters.JSON

class OrgUnitTypeController extends BaseController {

    static allowedMethods = [
        get: "GET",
        getByCode:'GET', 
        listShort:'GET', 
        list:'GET']
        
    def getByCode(String code) {
        // ../CoreQueries/orgUnit/get?code=EUR
        if (code==null){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$code"]]
            render answer as JSON
        }
        else {
            def uri = "/orgUnit/getByCode?code="+ code.toUpperCase()  //internal requestt to domains
            params.sourceComponent=sourceComponent()
            params.sourceURI="$uri" 
            params.host = RenderService.hostApp(request)
            params.URL =  RenderService.URL(request) 
            params.URL += "?code="+ code.toUpperCase()
            params.links = BuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()
            render RenderService.serviceMethod(params, request) 
            }
        }      
        
    def extraLinks(){ 
        def controllerURL = "$params.host/$params.controller"
        def links = [:]
        links += ["getByCode":["template":true, "fields": ["code":"String (Organization Unit code)"], \
            "href":  "$controllerURL/getByCode?code={code}" ]]
        return links 
    }    

}
