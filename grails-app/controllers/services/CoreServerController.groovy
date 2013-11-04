package services

import grails.plugins.rest.client.RestBuilder
import grails.converters.*

class CoreServerController {
//    LinkGenerator grailsLinkGenerator
    
//    def components = ConfigurationHolder.config.app.components
    
    static allowedMethods = [renderResponce: "GET"]

    def index() {
        redirect(action: "demo", params: params)
    }

    def renderResponce() {
        def comp1 = entities.Component.findByName(params.sourceComponent)
        def baseURL = comp1.baseURL
        def rest = new RestBuilder()
        println baseURL + "$params.sourceURI"
        def resp = rest.get(baseURL + "$params.sourceURI") {
            accept "application/json"
            contentType "application/json"
            } 
        params.reqID = UUID.randomUUID().toString()
        params.timestamp = new Date().toString()
        def answer = ["header":params, "body":resp.json.sort()]
        
        def comp2 = entities.Component.findByName("Auditor")
        if (comp2 && entities.Component.findByName("Auditor").isActive) {
            // store in the auditdb (CouchDB)
            answer.header.audited="true"
            def restAudit = new RestBuilder()
            def url = entities.Component.findByName("Auditor").baseURL + "/" + params.reqID
//            println url
            def respAudit = restAudit.put(url){
                contentType "application/json"
                json {answer}
            }
        }
        else { 
            answer.header.audited="false"
        }
        render answer as JSON        
    }
    
    def demo = {
        JSON.use('deep')
        def component = entities.Component.findByName("Commons")
        def baseURL = component.baseURL
        def rest = new RestBuilder()
        println "$baseURL" + "/currency/get/1"
        def resp1 = rest.get("$baseURL" + "/currency/get/1") {
            accept "application/json"
            contentType "application/json"
        }
        def resp2 = rest.get("$baseURL" + "/currency/get/2") {
            accept "application/json"
            contentType "application/json"
        }
        // add new        
        // add new property
        resp1.json.author = "Nikos Karamolegos"
        // change property's value
        resp1.json.name = "EURO-ΕΥΡΩ"
        resp1.json.notes = "This is a new note"
        // remove/hide property
        resp1.json.remove("area")
        //add another json
        resp1.json.other = resp2.json
        render resp1.json
    }
}
