package services.parties

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.*
import grails.converters.JSON

class PersonController extends BaseController{
    def XRenderService
    def XBuildLinksService
    
    def shortList() {
        render status:405 // Not supported
    }
    
    def isIdValid(String id ) { 
        if (params.id==null) {
            response.status = 400 // 400 Bad Request`
            def answer = ["error":["status":"400", "id":"$params.id"]]
            render answer as JSON
        }
        else {
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
            params.recStatus = (params.recStatus ? params.recStatus.toLowerCase() : "Active").capitalize()  
            params.sourceURI = "/$params.controller/isIdValid.json?id=$params.id"   //internal request to domains
            params.URL += "?id=$id&recStatus=$params.recStatus" 
            renderNow()    
        }
    }  
    
    def getByTaxId(String taxid) { 
        if (params.taxid==null) {
            response.status = 400 // 400 Bad Request`
            def answer = ["error":["status":"400", "taxid":"$params.taxid"]]
            render answer as JSON
        }
        else {
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
            params.recStatus = (params.recStatus ? params.recStatus.toLowerCase() : "Active").capitalize()  
            params.sourceURI = "/$params.controller/getByTaxId.json?taxid=$params.taxid"   //internal request to domains
            params.URL += "?taxid=$taxid&recStatus=$params.recStatus" 
            renderNow()    
        }
    }         
    
    def getByEmail(String email ) { 
        if (params.email==null) {
            response.status = 400 // 400 Bad Request`
            def answer = ["error":["status":"400", "email":"$params.email"]]
            render answer as JSON
        }
        else {
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
            params.recStatus = (params.recStatus ? params.recStatus.toLowerCase() : "Active").capitalize()  
            params.sourceURI = "/$params.controller/getByEmail.json?email=$params.email"   //internal request to domains
            params.URL += "?email=$email&recStatus=$params.recStatus" 
            renderNow()    
        }
    }    
    
    def getBySSN(String ssn ) {
        if (params.ssn==null) {
            response.status = 400 // 400 Bad Request`
            def answer = ["error":["status":"400", "ssn":"$params.ssn"]]
            render answer as JSON
        }
        else {
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
            params.recStatus = (params.recStatus ? params.recStatus.toLowerCase() : "Active").capitalize()  
            params.sourceURI = "/$params.controller/getBySSN.json?ssn=$params.ssn"   //internal request to domains
            params.URL += "?ssn=$ssn&recStatus=$params.recStatus" 
            renderNow()    
        }
    }     
}
