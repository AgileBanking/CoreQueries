package services.commons

import grails.converters.JSON

class IbanController extends BaseController {
    def XRenderService        
    def XBuildLinksService

    static allowedMethods = [
        getByCountryIso2:'GET', 
        shortList:'GET', 
        list:'GET']

    def getByCountryIso2(String iso2) {
        // ../CoreQueries/iban/get?iso2=EUR
        if (iso2==null || iso2.size()!=2){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"$iso2"]]
            render answer as JSON
        }
        else {
            def uri = "/$params.controller/$params.action?iso2="+ iso2.toUpperCase()  //internal requestt to domains
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
   
    def extraLinks() {
        def controllerURL = XRenderService.hostApp(request) + "/$params.controller"
        def links = [:]
        links += ["getByCountryIso2":["template":true, "fields": ["iso2":"String (Country: ISO 3166 alpha-2 code)"], \
            "href":  "$controllerURL/getByCountryIso2?iso2={iso2}" ]]
        links += ["references":[\
            "Country":["ISO 3166 alpha-2 code": "http://en.wikipedia.org/wiki/ISO_3166-1_alpha-2",
                "ISO 3166 alpha-3 code": "http://en.wikipedia.org/wiki/ISO_3166-1_alpha-3",
                "ISO 3166 numeric code": "http://en.wikipedia.org/wiki/ISO_3166-1_numeric",
                "ISO.org Country codes": "http://www.iso.org/iso/iban_codes",
                "ISO.org Country   XML": "http://www.iso.org/iso/home/standards/iban_codes/iban_names_and_code_elements_xml",
                "Dropdown CountryCodes": "http://www.textfixer.com/resources/iban-dropdowns.php"],
            "IBAN":["Wikipedia": "http://en.wikipedia.org/wiki/International_Bank_Account_Number",
                "Examples":"http://bit.ly/1dpgfiU",
                "SWIFT":"http://goo.gl/fHg1z",
                "SWIFT IBAN Registry": "http://www.swift.com/dsp/resources/documents/IBAN_Registry.pdf"
                ]]]
        return links 
    }
}