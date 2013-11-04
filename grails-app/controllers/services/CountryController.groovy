package services
import grails.plugins.rest.client.RestBuilder
import grails.converters.*

class CountryController {
    static allowedMethods = [getFullList: "GET"]
    
    def index() {redirect(action: "getFullList", params: params) }
    
    def getFullList() {
        // ../country/getFullList
        redirect (
            controller: "CoreServer", 
            action: "renderResponce", 
            params:[sourceComponent:"Commons", sourceURI:"/country/list.json",
            "caller":"$request.forwardURI" ,"params":params]
            )         
    }          
}
