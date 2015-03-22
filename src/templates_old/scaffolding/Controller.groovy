<%=packageName ? "package ${packageName}\n\n" : ''%>

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.*

class ${className}Controller {
        
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def RenderService
    
    def index() {redirect(action: "list", params: params)}
    
    def schema = {
        withFormat{
            json {redirect(uri:"/schema/json?class=${className}")}
            xml  {redirect(uri:"/schema/XSD?class=${className}")}
        }
    }    

    def listMethods = {
        def myMap =[:]
        def object = new ${className}()
        object.count()
        def myMethods =  object.metaClass.methods*.name.sort().unique()
        myMap["Total Dynamic Methods for '${className}'"]=myMethods.size
        myMap["Methods"]=myMethods
        withFormat{
            json {render myMap as JSON}
            xml {render myMap as XML}
        }
    }    


    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)  
//        def result = [${propertyName.capitalize() - "Instance"}List: ${className}.list(params)]
        def result = ${className}.list(params)
        withFormat{
            html { return [${propertyName}List: ${className}.list(params), ${propertyName}Total: ${className}.count()]}
            xml  { render result as XML }
            json { render result as JSON}
        }
    }

    def get(Long id) {
        def ${propertyName} =  ${className}.get(id)
        if (!${propertyName}) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${propertyName}.label', default: '${className}'), id])
            withFormat{
                json {render [:] as JSON}
                xml  {render [:] as XML}
            }
            return
        }
        JSON.use('deep')
        XML.use('deep')
        withFormat{
            json {render ${propertyName} as JSON}
            xml  {render ${propertyName} as XML}
        }

    }
    
    
    def customeQuery(String query) {
        // Example: // customeQuery?query=select iso2, name, nameInt from Language
        def ${propertyName} =  ${className}.executeQuery(query)
        if (!${propertyName}) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${propertyName}.label', default: '${className}'), query])
            withFormat{
                json {render [:] as JSON}
                xml  {render [:] as XML}
            }
            return
        }
        JSON.use('deep')
        XML.use('deep')        
        withFormat{
            json {render ${propertyName} as JSON}
            xml  {render ${propertyName} as XML}
        }
    }
    
    def create() {
        [${propertyName}: new ${className}(params)]
    }

    def save() {
        def ${propertyName} = new ${className}(params)
        if (!${propertyName}.save(flush: true)) {
            render(view: "create", model: [${propertyName}: ${propertyName}])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])
        redirect(action: "show", id: ${propertyName}.id)
    }

    def show(Long id) {
        def ${propertyName} = ${className}.get(id)
        if (!${propertyName}) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: "list")
            return
        }
        [${propertyName}: ${propertyName}]
    }
    
//    def get(Long id) {
//        def ${propertyName} = ${className}.get(id)
//        if (!${propertyName}) {
//            flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
//            redirect(action: "list")
//            return
//        }
//        withFormat{
//            json {render ${propertyName} as JSON}
//            xml  {render ${propertyName} as XML}
//            rss {render ${propertyName}}
//        }
//    }

    def edit = {
        def ${propertyName} = ${className}.get(params.id)
        if (!${propertyName}) {
            flash.message = "\${message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [${propertyName}: ${propertyName}]
        }
    }
    
    def update(Long id, Long version) {
        def ${propertyName} = ${className}.get(id)
        if (!${propertyName}) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (${propertyName}.version > version) {<% def lowerCaseName = grails.util.GrailsNameUtils.getPropertyName(className) %>
                ${propertyName}.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: '${domainClass.propertyName}.label', default: '${className}')] as Object[],
                          "Another user has updated this ${className} while you were editing")
                render(view: "edit", model: [${propertyName}: ${propertyName}])
                return
            }
        }

        ${propertyName}.properties = params

        if (!${propertyName}.save(flush: true)) {
            render(view: "edit", model: [${propertyName}: ${propertyName}])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), ${propertyName}.id])
        redirect(action: "show", id: ${propertyName}.id)
    }

    def delete(Long id) {       // Instand of DELETE execute UPDATE with status=DELETED
        def ${propertyName} = ${className}.get(id)
        if (!${propertyName}) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: "list")
            return
        }

        try {
            ${propertyName}.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: '${domainClass.propertyName}.label', default: '${className}'), id])
            redirect(action: "show", id: id)
        }
    }
    
    def relatedLinks() {
        params.hostApp = RenderService.hostApp(request)
        includeLinks(params)
        def result = [:]
        result.controller = params.controller 
        params.links += ["self": ["href": "$params.hostApp/$result.controller/relatedLinks"]]
        result.links= params.links
        render result as JSON
    }

    def schema() {
        def uri = "/admin/JSD?ComponentName=Parties&ClassName=" + params.controller.capitalize() //internal request to domains
//        println uri
        params.sourceComponent="Parties"
        params.collection = false
        params.hostApp = RenderService.hostApp(request) 
        params.sourceURI="$uri"  
        params.hideclass = true
        params.URL =  RenderService.URL(request) 
        includeLinks(params)      
        render RenderService.serviceMethod(params)
    }
    
    def create() {
        def uri = "/$params.controller/create.json" //internal request to domains
//        println uri
        params.sourceComponent="Parties"
        params.collection = false
        params.hostApp = RenderService.hostApp(request) 
        params.URL =  RenderService.URL(request) 
        includeLinks(params)
        params.hide = ["id", "version"]
        params.sourceURI = "$uri"  
        params.hideclass = true
        params.URL =  RenderService.URL(request) 
        render RenderService.serviceMethod(params)         
    }
    
    private includeLinks(params) {
        if (params.withlinks==null) { params.withlinks = request.getHeader('withlinks')}
        if (params.withlinks=="true" || params.withlinks==null ) { 
            params.links  = ["list":["template": true, "fields": ["max"], "href":"$params.hostApp/$params.controller/list", "description":"List " ]]
            params.links += ["get":["template": true, "fields": ["id"], "href": "$params.hostApp/$params.controller/get?id={id}"]]
            params.links += ["schema": ["href": "$params.hostApp/$params.controller/schema"]]
            params.links += ["create": ["href": "$params.hostApp/$params.controller/create", "notes":"Returns an empty instance of editable fields."]]
            params.links += ["save":["template":true, "method":"PUT", "href": entities.Component.findByName('CoreUpdates').baseURL + "/$params.controller/save", \
                "body":"@create", "notes":"If you have not in cache, get the body from the 'create'."]]
            if (params.id==null) {
                params.links += ["myLegalGroups":["template": true, "fields": "id","href": "$params.hostApp/$params.controller/listMyLegalGroups?id={id}"]]           
            }
            else {
                params.links += ["myLegalGroups":["href": "$params.hostApp/$params.controller/listMyLegalGroups?id=$params.id"]]            
            }
        }        
    }    
    
}
