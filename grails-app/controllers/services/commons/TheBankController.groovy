package services.commons
import grails.converters.deep.JSON
import grails.plugins.rest.client.RestBuilder

class TheBankController extends BaseController {
    
    def XRenderService
    def XBuildLinksService
    
    static allowedMethods = [
        getBank:        'GET',
        iban:           'GET',
        baseCurrency:   'GET',
        hostCountry:    'GET',
        orgTreeRoot:    'GET',
        channels:       'GET',
        hqTimeZone:     'GET',
        officialLanguage:'GET'
    ]
        
    def index() { redirect(action: "getBank", params: params ) }
    
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
        foreignKey()    
    }   
    
    def baseCurrency() {
        foreignKey()       
    }
    
    def hostCountry() {
        foreignKey()        
    }
    
    def orgTreeRoot() {
        foreignKey()          
    }
    
    def hQTimezone() {
        foreignKey()
    }
    
    def channels() {
        foreignKey()
    }

    def officialLanguage() {
        foreignKey() 
    }   
    
    private foreignKey() {   
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
        params.sourceURI = "/$params.controller/$params.action"   //internal request to domains
        renderNow()
    }
    
    def extraLinks(){ 
        def controllerURL = "$params.host/$params.controller"
        def links = [:]
        links += ["get": ["href": "$controllerURL/getBank"]]
        links += ["iban":["href":  "$controllerURL/iban", "notes":"Returns the IBAN of the Bank"]]
        links += ["baseCurrency":["href":  "$controllerURL/baseCurrency", "notes":"Returns the official currency of the Bank"]]
        links += ["hostCountry":["href":  "$controllerURL/hostCountry", "notes":"Returns the Country of the Bank"]]
        links += ["orgUnit":["href":  "$controllerURL/orgUnit", "notes":"Returns the root of the Organization Chart of the Bank"]]
        links += ["hQTimezone":["href":  "$controllerURL/hQTimezone", "notes":"Returns the timezone of the Headquarters of the Bank"]]
        links += ["channels":["href":  "$controllerURL/channels", "notes":"Returns a list of the channels of the Bank"]]
        links += ["timeZones":["href":  "$controllerURL/timeZones", "notes":"Returns the timezones of the Bank"]]
        links += ["officialLanguage":["href":  "$controllerURL/officialLanguage", "notes":"Returns the official Language of the Bank"]]
        return links 
    }     
}
