package entities

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.json.*
import org.springframework.dao.DataIntegrityViolationException

import grails.converters.*

class ComponentController {
        
    static allowedMethods = [
        save: "POST", 
        update: "POST", 
        delete: "POST",
        index: "GET",
        list: "GET",
        listMethods: "GET",
        get: "GET",
        customeQuery: "GET"
    ]

    def list() {redirect(action: "index", params: params)}
    
    def index()  {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)  
        respond Component.list(params), model:[componentInstanceTotal: Component.count()]
    }   

    def listMethods = {
        def myMap =[:]
        def object = new Component()
        object.count()
        def myMethods =  object.metaClass.methods*.name.sort().unique()
        myMap["Total Dynamic Methods for 'Component'"]=myMethods.size
        myMap["Methods"]=myMethods
        withFormat{
            json {render myMap as JSON}
            xml  {render myMap as XML}
        }
    }    



    def get(Long id) {
        def componentInstance =  Component.get(id)
        if (!componentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'componentInstance.label', default: 'Component'), id])
            withFormat{
                json {render [:] as JSON}
                xml  {render [:] as XML}
            }
            return
        }
        JSON.use('deep')
        XML.use('deep')
        withFormat{
            json {render componentInstance as JSON}
            xml  {render componentInstance as XML}
        }

    }
    
    
    def customQuery(String query) {
        // Example: // customeQuery?query=select iso2, name, nameInt from Language
        def componentInstance =  Component.executeQuery(query)
        if (!componentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'componentInstance.label', default: 'Component'), query])
            withFormat{
                json {render [:] as JSON}
                xml  {render [:] as XML}
            }
            return
        }
        JSON.use('deep')
        XML.use('deep')        
        withFormat{
            json {render componentInstance as JSON}
            xml  {render componentInstance as XML}
        }
    }
    
    def create() {
        [componentInstance: new Component(params)]
    }

    def save() {
        def componentInstance = new Component(params)
        if (!componentInstance.save(flush: true)) {
            render(view: "create", model: [componentInstance: componentInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'component.label', default: 'Component'), componentInstance.id])
        redirect(action: "show", id: componentInstance.id)
    }

    def show(Long id) {
        def componentInstance = Component.get(id)
        if (!componentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'component.label', default: 'Component'), id])
            redirect(action: "list")
            return
        }
        [componentInstance: componentInstance]
    }
    

    def edit = {       
        def componentInstance = Component.get(params.id)
        if (!componentInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'component.label', default: 'Component'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [componentInstance: componentInstance]
        }
    }
    
    def update(Long id, Long version) {
        def componentInstance = Component.get(id)
        if (!componentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'component.label', default: 'Component'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (componentInstance.version > version) {
                componentInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'component.label', default: 'Component')] as Object[],
                          "Another user has updated this Component while you were editing")
                render(view: "edit", model: [componentInstance: componentInstance])
                return
            }
        }

        componentInstance.properties = params

        if (!componentInstance.save(flush: true)) {
            render(view: "edit", model: [componentInstance: componentInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'component.label', default: 'Component'), componentInstance.id])
        redirect(action: "show", id: componentInstance.id)
    }

    def delete(Long id) {       // Instand of DELETE execute UPDATE with status=DELETED
        def componentInstance = Component.get(id)
        if (!componentInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'component.label', default: 'Component'), id])
            redirect(action: "list")
            return
        }

        try {
            componentInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'component.label', default: 'Component'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'component.label', default: 'Component'), id])
            redirect(action: "show", id: id)
        }
    }
}