package services.commons
import grails.converters.JSON

class ChannelController extends BaseController {
    def XRenderService
    def XBuildLinksService
    
    static allowedMethods = [
        getByCode: "GET"
    ]    
    def getByCode(String code) {
        // ../CoreQueries/channel/get?code=EUR
        if (code==null ){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$code"]]
            render answer as JSON
        }
        else {
            def uri = "/channel/getByCode?code="+ code.toUpperCase()  //internal requestt to domains
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

    def extraLinks() { 
        def controllerURL = "http://$params.host/$params.controller"
        def links = [:]
        links += ["getByCode":["template":true, "fields": ["code":"String"], "href":  "$controllerURL/getByCode?code={code}"]]
        return links 
    }    
}
