package admin
import grails.converters.*
import groovy.json.*
import groovy.xml.MarkupBuilder
import grails.plugins.rest.client.RestBuilder
//import groovyx.net.http.*
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import org.codehaus.groovy.grails.commons.ConfigurationHolder


class AdminController {
    def grailsApplication
    LinkGenerator grailsLinkGenerator
    
    def index() {redirect(action: "menu") }

    def menu() { 
    def html = """
            <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
            <html>
            <head>
              <meta content="text/html; charset=ISO-8859-1"
             http-equiv="content-type">
              <title>admin</title>
            </head>
            <body>
            <div style="text-align: left;"><big
             style="color: rgb(12, 59, 150);"><big><big><span
             style="font-family: Calibri; font-weight: bold;">Admin menu</span></big></big></big><br>
            </div>
            <br>
            <ol>
              <li><a href="XSD" target="_blank">Data
            Model in XML Schema</a></li>
              <li><a href="JSD" target="_blank">Data
            Model in JSON Schema</a></li>
              <li><a href="relationsDiagram" target="_blank">Relations
            Diagram</a></li>
              <li><a href="componentsActionsByController" target="_blank">Actions
            by Controller</a></li>
            </ol>
                You can address specific component by appending:<br>
                ?ComponentName={component}<br>
                For example, after calling one of the above, append:<br>
                ?ComponentName=Commons
            </body>
            </html>
        """
        render (html)
    }
    
    def relationsDiagram(String ComponentName)  {
        if (ComponentName == "Core" || ComponentName==null) {
            def url = "http://yuml.me/diagram/nofunky;dir:TD/class/draw2/"
            url += "[CoreServer]<>1-  0..*>[Accounts], [CoreServer]<>1-  0..*>[Commons], [CoreServer]<>1-  0..*>[Parties], [CoreServer]<>1-  0..*>[Products],"
            url += "[CoreServer]<>1-  0..*>[Repo]"
            redirect (url:"$url")
            return
        }
        else {
            def component = entities.Component.findByName(ComponentName) 
            if (component.supportAdmin){
                def baseURL = component.baseURL
                redirect (url: "$baseURL" + "/admin/relationsDiagram")
                return
            }
            else {
                response.status = 404
                render "<p>'$ComponentName' does not suport Remote Admin.</p><p> Ask the Relations Diagram from the component, directly.</p>"
            }
        }
    }   
    
    def componentsActionsByController(String ComponentName, String ControllerName) {
        // example: /server/ComponentsActionsByController?ComponentName=Commons&ControllerName=Currency
        if (ComponentName == "Core" || ComponentName==null) {
            def x = myActions()       
        }
        else { 
            def url 
            def component = entities.Component.findByName(ComponentName) 
            if (component.supportAdmin){
                def baseURL = component.baseURL
                if (ControllerName == null) {
                    url = "$baseURL" + "/admin/actionsByController"
                } 
                else {
                    url = "$baseURL" + "/admin/actionsByController?ControllerName=$ControllerName"
                }
                def rest = new RestBuilder()
                def resp = rest.get("$url") {
                    accept "application/json"
                    contentType "application/json"                       
                }
                render resp.json
            }
            else {
                response.status = 404
                render "<p>'$ComponentName' does not suport Remote Admin.</p><p> Ask the Relations Diagram from the component, directly.</p>"
            }
        }
        
    }
    
    private myActions()  {
        def str 
        def fullName
        def actionURI
        def actions = []
        def componentName = grailsApplication.metadata['app.name']
        def jactions = new JsonBuilder()
        def controllers = grailsApplication.controllerClasses  //grailsApplication.getArtefacts("Controller")   // 
//        println "controllers: " + controllers.toString()
        jactions."$componentName"{
        controllers.each { c ->    
//            println "c> $c"
//            println c.getClass()
            fullName = c.fullName
            fullName = fullName.substring(fullName.lastIndexOf('.')+1) - "Controller"
//            println "$fullName contains 'grails'? " + fullName.contains("grails")
//            println "c: $fullName c.name=$c.name" 
            if (!c.fullName.contains("grails") ){
                "$fullName" { 
//                    println "set actions of $c.name to: " + c.getURIs().collect({ uri -> c.getMethodActionName(uri)}).unique().sort() - "index"
                    actions=c.getURIs().collect({ uri -> c.getMethodActionName(uri)}).unique().sort() - "index"
//                    println "actions: " + actions
                    actions.each { a ->
//                        println "a: $a"
//                        if ("$a"==defaultAction) {
//                            "$a" "defaultAction"}
//                        else {
                            "$a" "../$c.name/$a"
//                        }
                    }
                }
            }
         }
     }
     render jactions.toPrettyString()
  }

    def DataModelInJSON = {
        redirect (action:"JSD", params:params)
        return
    }
    
    def JSD (String ComponentName, String ClassName)  {
        def componentName = grailsApplication.metadata['app.name']
//        println componentName + " - " + ComponentName
        if (ComponentName==null || ComponentName==componentName) {
            def className = params.class
            def appName = grailsApplication.metadata['app.name']
            def jschema = new JsonBuilder()
            def domainSchema = grailsApplication.getArtefacts("Domain")
            jschema."$appName"{
            domainSchema.each { c -> 
                if (className==null || "$c.name"==className){
                    "$c.name" { 
                         c.properties.each { p ->
                            "$p.name" {          
                                    type  p.getReferencedPropertyType().getName().substring(p.getReferencedPropertyType().getName().lastIndexOf('.')+1)
                                    if(!p.isBidirectional() || p.isOwningSide()) {
                                       if (p.isOneToMany()){           //  '1-0..*>':'1-1..*>'
                                           minOccurs "0" 
                                           maxOccurs "unbounded"
                                       } else if (p.isOneToOne()){     //  '1-0..1>':'1-1>'
                                           minOccurs "1" 
                                           maxOccurs "1"
                                       } else if (p.isManyToMany()){   //  '*-*>':'1..*-1..*>'
                                           minOccurs "0" 
                                           maxOccurs "unbounded"                                       
                                       }

                                    }  
                                }
                            }
                        }
                    }
                }
            }
             render jschema 
        }
        else {
            def url 
            def component = entities.Component.findByName(ComponentName) 
            def baseURL = component.baseURL

            if (ClassName == null) {
                url = "$baseURL" + "/admin/JSD"
            } 
            else {
                url = "$baseURL" + "/admin/JSD?ClassName=$ClassName"
            }

            def rest = new RestBuilder()
            def resp = rest.get("$url") {
                accept "application/json"
                contentType "application/json"                        
            } 
            render resp.body.toString()            
        }
    }
    
    def dmxml = {
        redirect(action: "XSD", params: params)
        return
    }
    
    def DataModelInXML = {
        redirect(action: "XSD", params: params)
        return
    }

    def XSD (String ComponentName, String ClassName) {       
        def xsd
        def componentName = grailsApplication.metadata['app.name']
        if (ComponentName==null || ComponentName=="$componentName") {
            def className = ClassName
            def componentSchema = grailsApplication.getArtefacts("Domain")
            def writer = new StringWriter()
            def schema = new MarkupBuilder(writer)  
            def str = ""
            def ptype = []
            schema."xs:component" (name: "$componentName") {
                componentSchema.each { c ->
                    if (className==null || "$c.name"==className){
                        schema."xs:complexType" (name: "${c.name}") {
                          schema."xs:sequence" { 
                            c.properties.each { p ->
                               switch (p.name) {
                                   case ['validationSkipMap', 'gormPersistentEntity', 'gormPersistentEntity', 'properties', 'gormDynamicFinders', 'all', 'domainClass', 'attached']:
                                       break
                                   case ['class', 'constraints', 'validationErrorsMap', 'dirtyPropertyNames', 'metaClass', 'errors', 'dirty', 'beforeInsert', 'beforeUpdate'] :
                                       break
                                   default:
                                       str = resolveName(p.getReferencedPropertyType().getName())
                                       if (p.isAssociation()){
                                           // if its association only show the do the owning side
                                           if(!p.isBidirectional() || p.isOwningSide()) {
                                               if (p.isOneToMany()){           //  '1-0..*>':'1-1..*>'
                                                   "xs:element"(name:"${p.name}", type:"${str}", minOccurs:"0", maxOccurs:"unbounded") 
                                               } else if (p.isOneToOne()){     //  '1-0..1>':'1-1>'
                                                   "xs:element"(name:"${p.name}", type:"${str}", minOccurs:"1", maxOccurs:"1")
                                               } else if (p.isManyToMany()){   //  '*-*>':'1..*-1..*>'
                                                   "xs:element"(name:"${p.name}", type:"${str}", minOccurs:"0", maxOccurs:"unbounded")
                                               }
                                               break
                                               }
                                               else { // not bidirectional ...
                                               }
                                       } else {
           //                                    println  p.name + ' ' +resolveName(p.getType().getName()) +  ';'
                                           }    

                                       switch ( str ) {
                                           case "String":
                                               "xs:element"(name: "${p.name}", type:"xs:string")
                                               break
                                           case "Long" :
                                               "xs:element"(name:"${p.name}", type:"xs:long")
                                               break
                                           case "Integer" :
                                               "xs:element"(name:"${p.name}", type:"xs:integer")
                                               break                    
                                           case "Date" :
                                               "xs:element"(name:"${p.name}", type:"xs:date")
                                               break     
                                           case "boolean" :
                                               "xs:element"(name:"${p.name}", type:"xs:boolean")
                                               break
                                           case "Set" :
                                               "xs:element"(name:"${p.name}", type:"xs:boolean")
                                               break
                                           default:
           //                                        println "$p.name $p.type ($str)"
                                               "xs:element"(name:"${p.name}", type:"${str}")   
                                       }
                               }
                        }
                       }
                     }
                    }
                }
            }
            xsd = '<?xml version="1.0" encoding="ISO-8859-1" ?>\n<xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema">\n' + writer.toString() + '\n</xs:schema>'   
            render xsd
        }
        else {
            def url 
            def component = entities.Component.findByName(ComponentName) 
            def baseURL = component.baseURL

            if (ClassName == null) {
                url = "$baseURL" + "/admin/XSD"
            } 
            else {
                url = "$baseURL" + "/admin/XSD?ClassName=$ClassName"
            }

            def rest = new RestBuilder()
            def resp = rest.get("$url") {
                accept "application/json"
                contentType "application/json"                        
            } 
            render resp.body.toString()
        }
    }
        
        
    private resolveName(def name){
        // remove bracket if an array
        if(name.lastIndexOf('[') > -1){
            name = name.replace('[','');
        }
        // remove package name
        if (name.lastIndexOf('.') > -1){
            return name.substring(name.lastIndexOf('.')+1)
        }
        return name
    }        
    
}
