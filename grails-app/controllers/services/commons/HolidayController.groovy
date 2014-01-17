package services.commons
import grails.converters.JSON

class HolidayController extends BaseController {
    def XRenderService        
    def XBuildLinksService

    def index() { redirect(action: "list", params: params) }
    
    static allowedMethods = [
        get: "GET",
        getByIso2:'GET', 
        shortList:'GET', 
        list:'GET']
                
    def shortList() { redirect(action:"list", params: params) }
    
    def getByIso2(String iso2) {
        // ../CoreQueries/holiday/get?iso2=EUR
        if (iso2==null || iso2.size()!=2){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$iso2"]]
            render answer as JSON
        }
        else {
            def uri = "/holiday/getByIso2?iso2="+ iso2.toUpperCase()  //internal requestt to domains
            params.sourceComponent=sourceComponent()
            params.sourceURI="$uri" 
            params.host = XRenderService.hostApp(request)
            params.URL =  XRenderService.URL(request) 
            params.URL += "?iso2="+ iso2.toUpperCase()
            params.links = XBuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()
            render XRenderService.serviceMethod(params, request) 
            }
        }       

    def getYearHolidays(String iso2, String year) {
        // ../CoreQueries/holiday/getYearHolidays?iso2=EUR&year=2014
        if (iso2==null || iso2.size()!=2){
            def currentYear = new Date().year
            params.year = Math.min(params.year ? params.int('year') : currentYear, currentYear )  
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$iso2"]]
            render answer as JSON
        }
        else {
            def uri = "/holiday/getYearHolidays?iso2="+ iso2.toUpperCase() + "&year=$currentYear"  //internal requestt to domains
            params.sourceComponent=sourceComponent()
            params.sourceURI="$uri" 
            params.host = XRenderService.hostApp(request)
            params.URL =  XRenderService.URL(request) 
            params.URL += "?iso2="+ iso2.toUpperCase() + "&year=$currentYear"
            params.links = XBuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()
            render XRenderService.serviceMethod(params, request) 
            }        
    }
    
    def extraLinks() { 
        def controllerURL = "$params.host/$params.controller"
        def links = [:]
        links += ["getByIso2":["template":true, "fields": ["iso2":"String (Country: ISO 3166 alpha-2 code)"], \
            "href":  "$controllerURL/getByIso2?iso2={iso2}", "documentation": "http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2"]]
        links += ["getYearHolidays":["template":true, "fields": ["iso2":"String (Country: ISO 3166 alpha-2 code)", "year":"Integer (the year - default the current" ],
            "href":  "$controllerURL/getYearHolidays?iso2={iso2}&year={year}", "documentation": "http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2"]]
        return links 
    }        
}
