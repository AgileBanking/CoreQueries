package services.commons
import grails.converters.deep.JSON
import grails.plugins.rest.client.RestBuilder

class TheBankController extends BaseController {
    def XRenderService        
    def XBuildLinksService
    
    static allowedMethods = [
        getBank:    'GET',
        iban:       'GET',
        currency:   'GET',
        hostCountry:'GET',
        orgUnit:    'GET',
        channels:   'GET',
        hqTimeZone: 'GET',
        timeZones:  'GET',
        officialLanguage:'GET'
    ]
        
    def index() { redirect(action: "getBank", params: params) }
    
    def shortList() { 
        params.code = 303 // See Other
        redirect(action:"getBank", params: params) 
    }
        
    def getBank() {
        params.id=1
        redirect (action:"get", params:params)
        return
    }     
    
    def iban() {
        params.id = thisBank().iBAN.id
        redirect (controller:"Iban", action:"get", params:params )
        return
    }   
    
    def currency() {
        params.id = thisBank().iBAN.id
        redirect (controller:"currency", action:"get", params:params )
        return        
        
    }
    
    def hostCountry() {
        params.id = thisBank().iBAN.id
        redirect (controller:"country", action:"get", params:params )
        return        
    }
    
    def orgUnit() {
        params.id = thisBank().iBAN.id
        redirect (controller:"orgUnit", action:"get", params:params )
        return        
    }
    
    def channels() {
        
    }
    
    def hqTimeZone() {
        params.id = thisBank().iBAN.id
        redirect (controller:"timeZone", action:"get", params:params )
        return
    }
    
    def timeZones() {
        
    }
    
    def officialLanguage() {
        params.id = thisBank().iBAN.id
        redirect (controller:"language", action:"get", params:params )
        return        
    }
    
    def extraLinks(){ 
        def controllerURL = "$params.host/$params.controller"
        def links = [:]
        links += ["get": ["href": "$controllerURL/getBank"]]
        links += ["iban":["href":  "$controllerURL/iban", "notes":"Returns the IBAN of the Bank"]]
        links += ["currency":["href":  "$controllerURL/currency", "notes":"Returns the official currency of the Bank"]]
        links += ["hostCountry":["href":  "$controllerURL/hostCountry", "notes":"Returns the Country of the Bank"]]
        links += ["orgUnit":["href":  "$controllerURL/orgUnit", "notes":"Returns the root of the Organization Chart of the Bank"]]
        links += ["hqTimeZone":["href":  "$controllerURL/hqTimeZone", "notes":"Returns the timezone of the Headquarters of the Bank"]]
        links += ["channels":["href":  "$controllerURL/channels", "notes":"Returns a list of the channels of the Bank"]]
        links += ["timeZones":["href":  "$controllerURL/timeZones", "notes":"Returns the timezones of the Bank"]]
        links += ["officialLanguage":["href":  "$controllerURL/officialLanguage", "notes":"Returns the official Language of the Bank"]]
        return links 
    }    
    
    private thisBank() {
        params.sourceComponent=sourceComponent()
        params.sourceURI="/theBank/" 
        params.host = XRenderService.host(request)
        params.URL =  XRenderService.URL(request)  
        def answer =""
        def resp 
        def rest = new RestBuilder()
        try {        
            resp = rest.get("$params.host/theBank/getBank") { 
                accept "application/json"
                contentType "application/json"
                } 
            return resp.json.body 
        } 
        catch(Exception e2) {
            //answer = ["status":"$resp.status" , "message": "$e2.message", "sourceURI":"$baseURL$params.sourceURI"]  
            def xe2 = e2.toString() //.message.split(':')
            answer = ["status":"500", "possibleCause": "Unavailable Domain Server $params.sourceComponent", "message":[xe2]] 
            return answer 
        }       
    }
}
