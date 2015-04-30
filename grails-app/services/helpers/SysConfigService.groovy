package helpers
import grails.plugins.rest.client.RestBuilder
import grails.converters.*

class SysConfigService {

        // It seems that I do not need it
    def getComponent(String component) { 
        def restConfig = new RestBuilder()
        def url = "http://agilebanking.net:6789/$component"
//        def url = "http://auditdb:5984/configdb/$component" 
//        println "getComponent url: " + url
        def respV = restConfig.get("$url") { 
            accept "application/json"
            contentType "application/json"
        }   
//        println "audit: " + respV.json
        return respV.json.appServer
    }
    
//    def getComponentHost(String component) {
//        def restConfig = new RestBuilder() 
//        def url = "http://agilebanking.net:6789/$component"
////        def url = "http://auditdb:5984/configdb/$component" 
//        println "getComponentHost url: " + url
//        def respV = restConfig.get("$url") { 
//            accept "application/json"
//            contentType "application/json"
//        }   
//        return respV.json.name
//    }    
    
    def isComponentActive(String component) {
        def restConfig = new RestBuilder()
        def url = "http://agilebanking.net:6789/$component"
//        def url = "http://auditdb:5984/configdb/$component" 
//        println "isComponentActive url: " + url
        def respV = restConfig.get("$url") { 
            accept "application/json"
            contentType "application/json"
        }   
//        println respV.json
        return respV.json.isActive
    }      
}
