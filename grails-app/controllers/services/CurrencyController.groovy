package services

import grails.plugins.rest.client.RestBuilder
import grails.converters.*
//import org.codehaus.groovy.grails.commons.ConfigurationHolder

class CurrencyController {
//    LinkGenerator grailsLinkGenerator
//    def components = ConfigurationHolder.config.app.components

    static allowedMethods = [getByIso3:'GET', getListOfIso3:'GET', getFullList:'GET']
    
    def index() { redirect(action: "getListOfIso3", params: params) }
    
    def getByIso3(String iso3) {
        // ../CoreQueries/currency/getByIso3?iso3=EUR
        if (iso3==null || iso3.size()!=3){
            response.status = 400 // 400 Bad Request
            def answer = ["error":["status":"400", "expectedParams":"String iso3"]]
            render answer as JSON
        }
        else {
            def uri = "/currency/getByIso3?iso3="+ iso3.toUpperCase()
//            println "redirect to $uri" 
            redirect (
                controller: "CoreServer", 
                action: "renderResponce", 
                params:[sourceComponent:"Commons", sourceURI:"$uri",
                "caller":"$request.forwardURI" ,"params":params]
                )
            }
        }
//    def getShortListOfIso3() {
//        // ../currency/getShortListOfIso3
//        redirect (
//            controller: "CoreServer", 
//            action: "renderResponce", 
//            params: [sourceComponent:"Commons", sourceURI:"/currency/getShortListOfIso3",
//            "caller":"$request.forwardURI" ,"params":params]
//            )
//        }   
        
    def getListOfIso3() {
        // ../CoreQueries/currency/getListOfIso3
//        println "redirect to /currency/getListOfIso3"
        redirect (
            controller: "CoreServer", 
            action: "renderResponce", 
            params:[sourceComponent:"Commons", sourceURI:"/currency/getListOfIso3",
            "caller":"$request.forwardURI" ,"params":params]
            )        
        }     
        
    def getFullList() {
        // ../currency/getFullListOfIso3
        redirect (
            controller: "CoreServer", 
            action: "renderResponce", 
            params:[sourceComponent:"Commons", sourceURI:"/currency/list.json",
            "caller":"$request.forwardURI" ,"params":params]
            )         
        }      
}
    


