package helpers
import grails.plugins.rest.client.RestBuilder
import grails.converters.*
class SysConfigService {

    
    def getComponent(String component) { 
        def restConfig = new RestBuilder()
        def url = "http://localhost:5984/configdb/$component" 
        print "getComponent url: " + url
        def respV = restConfig.get("$url") { 
            accept "application/json"
            contentType "application/json"
        }   
        return respV.json
    }
    
    def getComponentHost(String component) {
        def restConfig = new RestBuilder() 
        def url = "http://localhost:5984/configdb/$component" 
        def respV = restConfig.get("$url") { 
            accept "application/json"
            contentType "application/json"
        }   
        return respV.json.name
    }    
    
    def isComponentActive(String component) {
        def restConfig = new RestBuilder()
        def url = "http://localhost:5984/configdb/$component" 
        def respV = restConfig.get("$url") { 
            accept "application/json"
            contentType "application/json"
        }   
        return respV.json.isActive
    }      
}
