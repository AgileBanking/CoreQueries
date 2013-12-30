package services.commons
import grails.converters.deep.JSON

class TheBankController {
    static allowedMethods = [
        get: "GET",
        getBank:'GET']
        
    def index() { redirect(action: "getBank", params: params) }
    
    def RenderService

    def get(Long id) {
        // ../CoreQueries/theBank/get?id=1  // ignore id. the bank is only one
        redirect (action: "getBank")
        } 
        
    def getBank() {
        params.sourceComponent="Commons"
        params.sourceURI="/theBank/"
        params.URL =  RenderService.URL(request) 
        params.URL += "getBank" 
        render RenderService.serviceMethod(params) 
    }        
}
