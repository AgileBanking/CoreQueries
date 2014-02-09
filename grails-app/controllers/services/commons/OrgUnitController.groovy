package services.commons
import grails.converters.JSON

class OrgUnitController extends BaseController {
    def XRenderService
    def XBuildLinksService
    
    static allowedMethods = [getByCode:'GET']

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
            params.host = XRenderService.hostApp(request)
            params.URL =  XRenderService.URL(request) 
            params.URL += "?code="+ code.toUpperCase()
            params.links = XBuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()
            renderNow()
            }
        }     

    def getOrgUnitType(Long id) {
        // ../CoreQueries/orgUnit/getOrgUnitType?code=EUR
        if (id==null){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$code"]]
            render answer as JSON
        }
        else {
            def uri = "/orgUnit/getOrgUnitType?id=$id"  //internal request to domains
            params.sourceComponent=sourceComponent()
            params.sourceURI="$uri" 
            params.host = XRenderService.hostApp(request)
            params.URL =  XRenderService.URL(request) 
            params.URL += "?id=$id"
            params.links = XBuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()
            render XRenderService.serviceMethod(params, request) 
            }
        }         
        
    def extraLinks(){ 
        def controllerURL = "$params.host/$params.controller"
        def links = [:]
        links += ["getByCode":["template":true, "fields": ["code":"String (Organization Unit code)"], \
            "href":  "$controllerURL/getByCode?code={code}" ]]
        links += ["OrgUnitType": ["template":true, "fields": ["id": "Long"],
            "href": "$controllerURL/getOrgUnitType?id={id}" ]]
        return links 
    }   
}
