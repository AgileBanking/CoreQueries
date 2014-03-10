package services.parties
import grails.plugins.rest.client.RestBuilder
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.*
import grails.converters.JSON

class PersonController extends BaseController{
    def XRenderService
    def XBuildLinksService
    
    def getByUsername(String username) {
        if (params.username==null) {
            response.status = 400 // 400 Bad Request`
            def answer = ["error":["status":"400", "id":"$params.id"]]
            render answer as JSON
        }
        else {
            params.sourceComponent=sourceComponent()
            params.collection = false
            params.host = XRenderService.hostApp(request) 
            params.withlinks = params.withlinks ? params.withlinks.toLowerCase() : "true" 
            if (params.withlinks == "true") {
                params.links = XBuildLinksService.controllerLinks(params, request) 
                params.links += extraLinks()
            }
            params.hideclass = true
            params.URL =  XRenderService.URL(request) 
            params.recStatus = (params.recStatus ? params.recStatus.toLowerCase() : "Active").capitalize()  
            params.sourceURI = "/$params.controller/getByUsername.json?username=$params.username"   //internal request to domains
            params.URL += "?username=$username&recStatus=$params.recStatus" 
            renderNow()    
        }
    }         
}
