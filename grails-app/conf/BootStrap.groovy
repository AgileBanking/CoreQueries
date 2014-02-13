import grails.plugins.rest.client.RestBuilder
class BootStrap {    

    def init = { servletContext ->
        // create auditdb, if not exists
       try {
        def restAudit = new RestBuilder()
        def respAudit = restAudit.put("http://localhost:5984/auditdb"){}            
       }
       catch(Exception e){
           println "Auditdb is not accessible"
           grailsApplication.config.custome.auditdb.isOn = true
       }
       
       if (entities.Component.count()==0) {
           def audit =  new entities.Component(name:"Auditor",      appVersion:"1.0", notes:"CouchDB", baseURL:"http://localhost:5984/auditdb").save()
           def coreQ =  new entities.Component(name:"CoreQueries",  appVersion:"1.0", baseURL:"http://localhost:9981/CoreQueries").save()
           def coreU =  new entities.Component(name:"CoreCommands",  appVersion:"1.0", baseURL:"http://localhost:9982/CoreCommands").save()
           def co =     new entities.Component(name:"Commons",      appVersion:"1.0", baseURL:"http://localhost:9901/Commons").save()
           def pa =     new entities.Component(name:"Parties",      appVersion:"1.0", baseURL:"http://localhost:9902/Parties").save()
           def pr =     new entities.Component(name:"Products",     appVersion:"1.0", baseURL:"http://localhost:8903/Products").save()
           def ac =     new entities.Component(name:"Accounts",     appVersion:"1.0", baseURL:"http://localhost:9094/Accounts").save()
       }
        
//        servletContext.globalVariable  = anyValue 
    }
    def destroy = {
    }
}
