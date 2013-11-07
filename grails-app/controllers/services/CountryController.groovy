package services
import grails.plugins.rest.client.RestBuilder
import grails.converters.*

class CountryController {
    static allowedMethods = [list: "GET"]
    
    def index() {redirect(action: "list", params: params) }
    
    def list() {
        // ../country/getFullList
        params.sourceComponent="Commons"
        params.sourceURI="/country/index.json"
        params.caller = "$request.forwardURI" 
        render RenderService.serviceMethod(params)    
    }          
}
