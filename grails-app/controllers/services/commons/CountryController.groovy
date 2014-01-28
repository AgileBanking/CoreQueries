package services.commons

import grails.converters.JSON

class CountryController extends BaseController {
    
    static allowedMethods = [
        get: "GET",
        list: "GET",
        getByIso2: "GET",
        shortList: "GET"
    ]
    
    def getByIso2(String iso2) {
        // ../CoreQueries/country/get?iso2=EUR
        if (iso2==null || iso2.size()!=2){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$iso2"]]
            render answer as JSON
        }
        else {
            def uri = "/country/getByIso2?iso2="+ iso2.toUpperCase()  //internal requestt to domains
            params.sourceComponent=sourceComponent()
            params.sourceURI="$uri" 
            params.host = RenderService.hostApp(request)
            params.URL =  RenderService.URL(request) 
            params.URL += "?iso2="+ iso2.toUpperCase()
            params.links = BuildLinksService.controllerLinks(params, request)
            params.links += extraLinks()
            render RenderService.serviceMethod(params, request) 
            }
        }         
        
    def extraLinks() { 
        def links = [:]
        def controllerURL = "$params.host/$params.controller"
        links += ["getByIso2":["template":true, "fields": ["iso2":"String (Country: ISO 3166 alpha-2 code)"], \
            "href":  "$controllerURL/getByIso2?iso2={iso2}" ]]
        links += ["references":["ISO 3166 alpha-2 code": "http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2",
            "ISO 3166 alpha-3 code": "http://en.wikipedia.org/wiki/ISO_3166-1_alpha-3",
            "ISO 3166 numeric code": "http://en.wikipedia.org/wiki/ISO_3166-1_numeric",
            "ISO.org Country codes": "http://www.iso.org/iso/country_codes",
            "ISO.org Country   XML": "http://www.iso.org/iso/home/standards/country_codes/country_names_and_code_elements_xml",
            "Dropdown CountryCodes": "http://www.textfixer.com/resources/country-dropdowns.php"]]
        return links 
    }       
}
