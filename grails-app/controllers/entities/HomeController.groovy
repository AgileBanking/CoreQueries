package entities
import grails.converters.*
import groovy.json.*
import groovy.xml.MarkupBuilder
import grails.plugins.rest.client.RestBuilder
import grails.web.Action
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class HomeController {
    def RenderService
    def BuildLinksService
    
    def index() {
        def componentName = grailsApplication.metadata['app.name']
        def jactions = new JsonBuilder()
        def component = [:]
        def controllerLinks = [:]
        params.hostApp = RenderService.hostApp(request)
        def x = request.getRequestURL() 
        x = x.substring(0,x.indexOf('.dispatch')) - '/grails' - "/admin/componentsActionsByController" - "index"
        component =["$componentName" : ["links":["host" : ["hostApp":"$x" - "/home"],"repo":["href":"$x" ]]]]
        grailsApplication.controllerClasses.each {cc -> 
            String controller = cc.logicalPropertyName
            if ("$controller" !="dbdoc") {
                params.controller = "$controller"
                controllerLinks += ["$controller" : BuildLinksService.controllerLinks(params, request) ]
            }
        }
        def result = ["component": component, "controllers": controllerLinks] as JSON
        render result
    }
    
}
