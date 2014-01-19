package services.parties
import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder

class LegalGroupTypeController extends BaseController{
    static allowedMethods = [
        index:              "GET",
        get:                "GET", 
        schema:             "GET",
        create:             "GET",
        myLegalGroups:  "GET",
        list:               "GET"]
        
    def casheControl() {"public, max-age=72000"}
    
    def index()     { redirect(action: "list", params: params) }
    def shortList() { redirect(action: "list", params: params) }
          
    def myLegalGroups(Long id) {
        if (params.id==null ) {
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "id":"$id"]]
            render answer as JSON
        }
        else {
            def uri = "/legalGroupType/$params.controller/$id" + ".json" 
            params.sourceComponent="Parties"
            params.collection = true
            params.sourceURI="$uri" 
            params.hideclass = true
            params.hostApp = XRenderService.hostApp(request)
            extraLinks()
            params.URL =  XRenderService.URL(request) 
            params.URL += "?id=$id"
            renderNow()
            }       
    }
    
    def extraLinks(){ 
        def controllerURL = "$params.host/$params.controller"
        def links = [:]
        links += ["myLegalGroups":["template":true, "fields": ["id":"Long (Legal Group Type id)"], \
            "href":  "$controllerURL/myLegalGroups?id={id}" ]]
        return links 
    } }